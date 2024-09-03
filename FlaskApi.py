from flask import Flask,request,jsonify
from Services.ScrapingService import ScrapingService
from Services.ResumeParserService import Service
from Services.MySqlService import MySqlService
from Services.RecommendationService import RecommendationService
from Services.StatisticService import StatisticService
from flair.models import SequenceTagger
from Services.CandidateDBService import CandidateDBService
from Services.CandidateRecommendationService import CandidateRecommendationService
from werkzeug.utils import secure_filename
import nltk
from nltk.corpus import stopwords
from app import createApp,createMysqlInstanceScrapping,startScrapingTask,getTagger
import threading
from io import BytesIO
import gridfs
from bson import ObjectId
from ultralytics import YOLO
from pdf2image import convert_from_path
from paddleocr import PaddleOCR, draw_ocr
import os
from flask_cors import CORS
os.environ["KMP_DUPLICATE_LIB_OK"]="TRUE"

app=createApp()
CORS(app)
mysql=createMysqlInstanceScrapping(app)
nltk.download('stopwords')
nltk.download('punkt')
tagger=SequenceTagger.load("flair/ner-french")
base_dir = os.path.dirname(os.path.abspath(__file__))

# Construct the absolute path to best5.pt
model = YOLO('best5.pt')

# Initialize the PaddleOCR reader
ocr = PaddleOCR(use_angle_cls=True, lang='en')
@app.route("/")
def home():
    return "Home"

# Flag to track whether the endpoint has been invoked
endpoint_invoked = False

@app.route('/invokeOnce', methods=['GET'])
def invoke_once():
    global endpoint_invoked
    if not endpoint_invoked:
        # Your logic for the endpoint goes here
        # For example, you can return a message indicating success
        startScrapingTask(mysql,app)
        num_threads = threading.active_count()

        print("Number of active threads:", num_threads)
        endpoint_invoked = True
        return 'Scraper invoked successfully', 200
    else:
        # If the endpoint has already been invoked, return an error message
        return 'Endpoint already invoked', 400




@app.route('/upload', methods=['POST'])
def upload_file():
    # Check if the POST request has a file part
    if 'file' not in request.files:
        return jsonify({'error': 'No file part'})

    file = request.files['file']

    # Check if the file is empty
    if file.filename == '':
        return jsonify({'error': 'No selected file'})

    # Check if the file is a PDF
    if file and file.filename.endswith('.pdf'):
        # Perform actions with the PDF file, e.g., save it or process it
        # In this example, we'll just return a success message
        
        file_path=Service.saveFile(file,app.config['UPLOAD_FOLDER'])
        
        text=Service.extract_content(file_path)
        consultant=Service.ResumeToConsultant(text,tagger,model,ocr,file_path)
        return jsonify(consultant.__dict__)

    return jsonify({'error': 'Invalid file format. Please upload a PDF file'})

@app.route('/scraper', methods=['POST'])
def scraper():
    scrapingService=ScrapingService()
    return scrapingService.get_All_opportunities(mysql,app)

@app.route('/test', methods=['GET'])
def test():
    mysqlService=MySqlService()
    #data= mysqlService.getAllOpportunity(mysql)
    #data=mysqlService.saveOpportunity(mysql,"test","test","test","test","test","test")
    #data=mysqlService.deleteAllOpportunity(mysql)
    recommendationService=RecommendationService()
    data=recommendationService.getOpportunityList(mysql)
    return str(data)

@app.route('/statistic', methods=['GET'])
def getStatistic():
    
    statisticService=StatisticService()
    data=statisticService.getStatistic(mysql)
    return data

@app.route('/recommendation', methods=['POST'])
def getRecommendations():
    if request.method == 'POST':
        # Assuming the object is sent as JSON data
        consultant = request.json
        recommendationService=RecommendationService()
        #data=recommendationService.processConsultant(consultant)
        data=recommendationService.getRecommendationOpportunity(mysql,consultant)
        # Process the received object as needed
        # For example, you can access its fields like data['field_name']
        
        # Return a response (optional)
        return data, 200
    else:
        return jsonify({'error': 'Method not allowed'}), 405

@app.route('/getCV', methods=['POST'])
def getCV():
    token = request.headers.get('Authorization')
    cv_file_id = request.form.get('cv_id')
    
    if not cv_file_id:
        return jsonify({"error": "file_id is required"}), 400

    try:
        # Convert the file_id string to ObjectId
        print(cv_file_id)
        file_id = ObjectId(cv_file_id)
    except:
        return jsonify({"error": "Invalid file_id format"}), 400

    try:
        # Attempt to retrieve the file from GridFS
        candidateDBService=CandidateDBService()
        cv_file=candidateDBService.get_cv(cv_file_id)
        return jsonify({'cv_file': cv_file}), 200, {'Content-Type': 'application/octet-stream'}
    except gridfs.errors.NoFile:
        return jsonify({"error": "File not found"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500
    return cv_file

@app.route('/saveCandidate', methods=['POST'])
def save_candidate():
    # Get the JSON data from the request
    name = request.form.get('name')
    email = request.form.get('email')
    phone = request.form.get('phone')
    title=request.form.get('title')
    offreId=int(request.form.get('offreId'))
    cover_letter = request.form.get('coverLetter')
    cv_file = request.files['cvFile']
    if not all([name, email, phone, cover_letter, cv_file]):
        return jsonify({"error": "Missing required fields"}), 400
    
    if cv_file and cv_file.filename.endswith('.pdf'):    
        file_path=Service.saveFile(cv_file,app.config['UPLOAD_FOLDER'])
        text=Service.extract_content(file_path)
        candidate=Service.ResumeToConsultant(text,tagger,model,ocr,file_path)
        candidate.setTitle(title)
        candidate.setName(name)
        candidate.setEmail(email)
        candidate.setPhoneNumber(phone)
        candidateDbService=CandidateDBService()
        candidateDbService.store_candidate_profile(candidate,cover_letter,offreId,cv_file)
        return jsonify(candidate.__dict__)
    

    
    
    '''return jsonify({"message": "Candidate profile saved", "id": str(result.inserted_id)}), 201'''
    return jsonify({"message": "Candidate profile saved"}), 201


@app.route('/getCandidates', methods=['POST'])
def get_candidates():
    #Get the optional offre_id parameter
    
    candidateDBService=CandidateDBService()
    candidateRecommendationService=CandidateRecommendationService()
    offre=request.get_json()
    # If offre_id is provided, filter by it
    offre_id=offre.get('id')
    if offre_id:
        
        df=candidateRecommendationService.getRecommendationOpportunity(offre)
        
        return jsonify(df),200
        
    
    else:
        return jsonify({"error": "No candidates found"}), 404

    


    

@app.route('/test', methods=['POST'])
def Test():
    if request.method == 'POST':
        # Assuming the object is sent as JSON data
        statisticService=StatisticService()
        statisticService.Test(mysql)
        return "Ok",200
    else:
        return jsonify({'error': 'Method not allowed'}), 405




if __name__ == "__main__":

    app.run(debug=True)






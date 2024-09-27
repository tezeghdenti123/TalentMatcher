from flask import Blueprint,redirect,request,jsonify
from paddleocr import PaddleOCR, draw_ocr
import os
from ultralytics import YOLO
from flair.models import SequenceTagger
import nltk
from nltk.corpus import stopwords
from ResumeParsing.ResumeParserService import Service
resume_parsing_bp=Blueprint("resumeparsing",__name__)

os.environ["KMP_DUPLICATE_LIB_OK"] = "TRUE"

# Download necessary NLTK data
nltk.download('stopwords')
nltk.download('punkt')

# Load French NER tagger model
tagger = SequenceTagger.load("flair/ner-french")

# Define the upload folder path
base_dir = os.path.dirname(os.path.abspath(__file__))
UPLOAD_FOLDER = os.path.join(base_dir, 'static', 'files')

# Ensure the upload folder exists
os.makedirs(UPLOAD_FOLDER, exist_ok=True)
# Construct the absolute path to best5.pt
model = YOLO('best5.pt')

# Initialize the PaddleOCR reader
ocr = PaddleOCR(use_angle_cls=True, lang='en')

@resume_parsing_bp.route("/")
def home():
    return "Hello world!"



@resume_parsing_bp.route('/upload', methods=['POST'])
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
        
        file_path=Service.saveFile(file,UPLOAD_FOLDER)
        
        text=Service.extract_content(file_path)
        consultant=Service.ResumeToConsultant(text,tagger,model,ocr,file_path)
        return jsonify(consultant.__dict__)

    return jsonify({'error': 'Invalid file format. Please upload a PDF file'})
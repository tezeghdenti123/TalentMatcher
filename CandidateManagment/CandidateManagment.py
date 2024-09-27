from flask import Blueprint,redirect,request,jsonify, current_app
from CandidateManagment.CandidateDBService import CandidateDBService
from CandidateManagment.CandidateRecommendationService import CandidateRecommendationService
from Models.Consultant import Consultant
from io import BytesIO
import gridfs
from bson import ObjectId

candidate_managment_bp=Blueprint("candidatemanagement",__name__)


@candidate_managment_bp.route('/saveCandidate', methods=['POST'])
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
        # Create a test client and use it to make a POST request to the upload endpoint
        cv_file_buffer = BytesIO(cv_file.read())
        with current_app.test_client() as client:
            data = {'file': (cv_file_buffer, cv_file.filename)}
            response = client.post('/upload', data=data, content_type='multipart/form-data')
            # Assert the response status code

            # Retrieve the JSON response
            consultant_data = response.get_json()  # This returns the JSON response as a dictionary

            # Reconstruct the Consultant object
            candidate = Consultant.from_dict(consultant_data)
            candidate.setTitle(title)
            candidate.setName(name)
            candidate.setEmail(email)
            candidate.setPhoneNumber(phone)
            candidateDbService=CandidateDBService()
            cv_file.seek(0) 
            candidateDbService.store_candidate_profile(candidate,cover_letter,offreId,cv_file)
            return jsonify(candidate.__dict__)
    
    return jsonify({"message": "Candidate profile saved"}), 201


@candidate_managment_bp.route('/getCandidates', methods=['POST'])
def get_candidates():
    #Get the optional offre_id parameter
    
    candidateRecommendationService=CandidateRecommendationService()
    offre=request.get_json()
    # If offre_id is provided, filter by it
    offre_id=offre.get('id')
    if offre_id:
        
        df=candidateRecommendationService.getCandidateRecommendation(offre)
        
        return jsonify(df),200
        
    
    else:
        return jsonify({"error": "No candidates found"}), 404
    
    
    
@candidate_managment_bp.route('/getCV', methods=['POST'])
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
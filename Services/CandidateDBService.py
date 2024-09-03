from pymongo import MongoClient
import gridfs
from bson import ObjectId
import base64
import os
# Connect to MongoDB
client = MongoClient('mongodb://localhost:27017/')
db = client['candidate_profiles']

# Create a GridFS instance
fs = gridfs.GridFS(db)

class CandidateDBService:

    def store_candidate_profile(self,candidate, cover_letter,offreId, cv_file):
        cv_file.seek(0) 
        file_content = cv_file.read()
        print("******************************** cvFile content ***********************************")
        print(file_content)
        # Read the CV file
        cv_file_id = fs.put(file_content, filename=cv_file.filename)
        print("******************************** cvFile content ***********************************")
        # Create the candidate profile document
        candidate_profile = {
            "name": candidate.getName(),
            "email": candidate.getEmail(),
            "phone": candidate.getPhoneNumber(),
            "title":candidate.getTitle(),
            "linkedIn":candidate.getLinkedIn(),
            "listSkills":candidate.getListSkill(),
            "experienceList":candidate.getExperienceList(),
            "educationList":candidate.getEducationList(),
            "languages":candidate.getlanguages(),
            "cover_letter": cover_letter,
            "offre_id":offreId,
            "cv_file_id": cv_file_id
        }
        # Insert the candidate profile into the collection
        result = db.profiles.insert_one(candidate_profile)
        print(f"Inserted candidate profile with ID: {result.inserted_id}")



    def get_candidates_profile(self, offre_id):
        candidates = db.profiles.find({"offre_id": offre_id})
        candidates_list = []
        i=0
        for candidate in candidates:
            candidate['_id'] = str(candidate['_id'])  # Convert ObjectId to string
            cv_file_id = candidate['cv_file_id']
            candidate['cv_file_id'] = str(cv_file_id)  # Convert ObjectId to string
            # Read the CV file from GridFS
            #cv_file = fs.get(ObjectId(cv_file_id))
            current_directory = os.getcwd()
            #file_path = os.path.join(current_directory, cv_file.filename)

            # Save the file to the current directory
            
            # Convert the file to base64
            ##cv_file_content = base64.b64encode(cv_file.read()).decode('utf-8')
            #cv_file_content=cv_file.read()
            print("*************************************************")
            #print(cv_file_content)
            print("************************************************")
            #candidate['cv_file'] = cv_file_content
            aux=candidate
            candidates_list.append(aux)
            i+=1
        print(len(candidates_list))
        return candidates_list
    
    def get_cv(self, cv_id):
        cv_file = fs.get(ObjectId(cv_id))
        current_directory = os.getcwd()
        
            
            # Convert the file to base64
        cv_file_content = base64.b64encode(cv_file.read()).decode('utf-8')
        return cv_file_content
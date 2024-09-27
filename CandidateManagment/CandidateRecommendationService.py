from Services.MySqlService import MySqlService
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel
from CandidateManagment.CandidateDBService import CandidateDBService
import os

class CandidateRecommendationService:

    def processCandidate(self,candidate):
        description=candidate.get('title')+' '
        description+=candidate.get('title')+' '
        description+=candidate.get('title')+' '
        description+=candidate.get('title')+' '
        description+=candidate.get('title')+' '
        experienceList=candidate.get('experienceList')
        educationList=candidate.get('educationList')
        listSkills=candidate.get('listSkills')
        if(educationList==None):
            educationList=[]
        if(experienceList==None):
            experienceList=[]
        if(listSkills==None):
            experienceList=[]
        for i in range(len(experienceList)):
            description+=experienceList[i]+' '
        for i in range(len(listSkills)):
            description+=listSkills[i].get('name')+' '
        for i in range(len(educationList)):
            description+=educationList[i]+' '
        return description


    def getCandidateList(self,offreId):
        candidateDBService=CandidateDBService()
        data=candidateDBService.get_candidates_profile(offreId)
        return data
    

    def candidateListToDataFrame(self,data):
        candidates=[]
        for candidate in data:
            candidate['_id'] = str(candidate['_id'])  # Convert ObjectId to string
            description=self.processCandidate(candidate)
            candidates.append({'id':candidate.get('_id'),'name':candidate.get('name'),'title':candidate.get('title'),'email':candidate.get('email'),'phone':candidate.get('phone'),'description':description,'cv_id':candidate.get('cv_file_id')})
        candidateDataFrame = pd.DataFrame(candidates)
        return candidateDataFrame
    
    def addOffre(self,offreDescription,df1):
        df1.loc[len(df1)]=['','','Notre','','',offreDescription,'']
        return df1
    
    def vectorization(self,df1):
        tdif=TfidfVectorizer(stop_words='english')
        tdif_matrix=tdif.fit_transform(df1['description'])
        return tdif_matrix
    
    def get_recommendation(self,title,cosine_sim,indices,df1):
        idx=indices[title]
        sim_scores=list(enumerate(cosine_sim[idx]))
        sim_scores=sorted(sim_scores,key=lambda X: X[1], reverse=True)
        #sim_scores=sim_scores[1:17]
        tech_indices=[i[0] for i in sim_scores]
        return df1.iloc[tech_indices]
    
    def dataframeToObjectList(self,df):
        row_objects = []
        for index, row in df.iterrows():
            row_dict = row.to_dict()
            row_objects.append(row_dict)
        return row_objects

    def processOffre(self,offre):
        description=offre.get('title')+' '
        description+=offre.get('title')+' '
        description+=offre.get('title')+' '
        description+=offre.get('title')+' '
        description+=offre.get('title')+' '
        description+=offre.get('description')+' '
        return description

    def getCandidateRecommendation(self,offre):
        candidateList=self.getCandidateList(offre.get('id'))
        candidatesDataFrame=self.candidateListToDataFrame(candidateList)
        print(len(candidateList))
        offreDescription=self.processOffre(offre)
        # Append the new row to the DataFrame
        if(len(candidatesDataFrame)==0):
            return []
        candidatesDataFrame=self.addOffre(offreDescription,candidatesDataFrame)
        tdif_matrix=self.vectorization(candidatesDataFrame)
        cosine_sim=linear_kernel(tdif_matrix,tdif_matrix)
        indices=pd.Series(candidatesDataFrame.index, index=candidatesDataFrame['title']).drop_duplicates()
        recommendation=self.get_recommendation('Notre',cosine_sim,indices,candidatesDataFrame).copy()
        recommendation = recommendation.drop(index=len(recommendation)-1)
        return self.dataframeToObjectList(recommendation)
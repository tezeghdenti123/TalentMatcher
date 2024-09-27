from flask import Flask,Blueprint,redirect,request,jsonify,current_app
from OpportunityRecommendation.RecommendationService import RecommendationService
from app import createMysqlInstanceScrapping,createApp
opportunity_recommendation_bp=Blueprint("opportunityrecommendation",__name__)
app=createApp()
mysql=createMysqlInstanceScrapping(app)

@opportunity_recommendation_bp.route('/recommendation', methods=['POST'])
def getRecommendations():
    if request.method == 'POST':
        # Assuming the object is sent as JSON data
        consultant = request.json
        recommendationService=RecommendationService()
        #data=recommendationService.processConsultant(consultant)
        data=recommendationService.getRecommendationOpportunity(mysql,consultant)
        # Return a response (optional)
        return data, 200
    else:
        return jsonify({'error': 'Method not allowed'}), 405
from app import createApp,createMysqlInstanceScrapping
from flask_cors import CORS
from ResumeParsing.ResumeParsing import resume_parsing_bp
from CandidateManagment.CandidateManagment import candidate_managment_bp
from OpportunityRecommendation.OpportunityManagment import opportunity_recommendation_bp
from ScrapingModule.ScrapingManagment import scraping_bp
app=createApp()
CORS(app)
app.register_blueprint(resume_parsing_bp)
app.register_blueprint(candidate_managment_bp)
app.register_blueprint(opportunity_recommendation_bp)
app.register_blueprint(scraping_bp)
mysql=createMysqlInstanceScrapping(app)

if __name__ == "__main__":

    app.run(debug=True)






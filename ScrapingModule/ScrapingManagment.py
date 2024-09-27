from flask import Blueprint
import threading
from ScrapingModule.ScrapingService import ScrapingService
from app import createApp,createMysqlInstanceScrapping,startScrapingTask
scraping_bp=Blueprint("Scraping",__name__)
app=createApp()
mysql=createMysqlInstanceScrapping(app)


# Flag to track whether the endpoint has been invoked
endpoint_invoked = False

@scraping_bp.route('/invokeOnce', methods=['GET'])
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
    
    
    
    
@app.route('/scraper', methods=['POST'])
def scraper():
    scrapingService=ScrapingService()
    return scrapingService.get_All_opportunities(mysql,app)

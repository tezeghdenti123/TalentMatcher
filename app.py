from flask import Flask,request,jsonify
import os
from flask_cors import CORS
from flask_mysqldb import MySQL
import threading
from ScrapingModule.ScrapingService import ScrapingService
from flair.models import SequenceTagger
from nltk.corpus import stopwords
from apscheduler.schedulers.background import BackgroundScheduler

def createApp():
    app=app=Flask(__name__)
    CORS(app)  # This will enable CORS for all route
    
    # Ensure the upload folder exists
    app.config['SQLALCHEMY_DATABASE_URI'] = ':///User.mysql'
    return app

def createMysqlInstanceScrapping(app):
    app.config['MYSQL_HOST'] = 'localhost'
    app.config['MYSQL_USER'] = 'root'
    app.config['MYSQL_PASSWORD'] = 'Mohamed2019$'
    app.config['MYSQL_DB'] = 'scraping'

    return MySQL(app)

def startScrapingTask(mysql,app):
    try:
        service=ScrapingService()
        
        scheduler = BackgroundScheduler()
        if not scheduler.running:
        # Schedule the job to run every day at 8:00 AM
            scheduler.add_job(service.get_All_opportunities, 'interval', minutes=1,args=[mysql,app])

        # Start the scheduler
            scheduler.start()
        
    except Exception as e:
        print("SchedulerException: Job interupted!",e)


def getTagger():
    return SequenceTagger.load("flair/ner-french")

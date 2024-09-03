from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
import chromedriver_autoinstaller
from selenium import webdriver
from Models.Opportunity import Opportunity
from flask import jsonify
import time
from Services.MySqlService import MySqlService
from Services.HitechProScraping import HitechProScraping
import re
import csv
import threading
import schedule

class ScrapingService:
    def getDriver(self):
        opt = webdriver.ChromeOptions()
        opt.add_argument("--start-maximized")
        opt.add_argument("--log-level=1")
        chromedriver_autoinstaller.install()
        driver = webdriver.Chrome(options=opt)
        return driver
    def get_Pages_number(self,driver):
        try:
            # Locate the pagination ul element
            pagination_ul = driver.find_element(By.CLASS_NAME, 'pagination')
            
            # Find all li elements within the pagination ul
            li_elements = pagination_ul.find_elements(By.TAG_NAME, 'li')
            
            # Get the li element before the last one
            if len(li_elements) > 1:
                li_before_last = li_elements[-2]
                li_before_last_text = li_before_last.text
                print(f"Text of the li element before the last one: {li_before_last_text}")
                return li_before_last_text
            else:
                print("Not enough li elements found.")
                return '50'
        except Exception as e:
            print("The pagination element was not found.")
            return '50'
        
    def getTitles(self,driver):
        elements = WebDriverWait(driver, 20).until(
            EC.presence_of_all_elements_located((By.CSS_SELECTOR, 'h2.job-title > a.stretched-link'))
        )

        # Iterate through the list of elements and get the text of each <a> element
        title_list=[]
        for element in elements:
            # Scroll the element into view
            driver.execute_script("arguments[0].scrollIntoView(true);", element)
            
            # Get the text of the <a> element
            link = element.get_attribute('href')
            #print("Link inside the element:", link)
            element_text = element.text
            #print("Text inside the element:", element_text)
            title_list.append(link)
            title_list.append(element_text)
        return title_list
    
    def loadPage(self,driver,page_index):
        target_element = WebDriverWait(driver, 20).until(
            EC.presence_of_element_located((By.XPATH, f'//a[@href="/offres-freelance?page={page_index}" and @class="page-link"]'))
        )

        # Scroll the element into view
        driver.execute_script("arguments[0].scrollIntoView(true);", target_element)

        # Wait until the element is clickable
        WebDriverWait(driver, 20).until(EC.element_to_be_clickable((By.XPATH, f'//a[@href="/offres-freelance?page={page_index}" and @class="page-link"]')))

        # Use JavaScript to click the element
        driver.execute_script("arguments[0].click();", target_element)

        # Optionally, wait for some time to let the next page load
        time.sleep(5)

    def get_element_text(self,driver,xpath):
        try:
            element = driver.find_element(By.XPATH, xpath)
            return element.text
        except Exception:
            print("ok")
            return None
            
    
    def get_opportunity(self,driver,title,opp_link):
        driver.get(opp_link)
        time.sleep(10)
        # Extract the date
        # Extract the date
        try:
            date_xpath = '//li[@title="Date de début"]/div/div[2]'
            date_value = self.get_element_text(driver,date_xpath)
            #print(f"Date de début: {date_value}")
        except Exception as e:
            #print(f"Date de début: None (Exception: {e})")
            date_value=None

        # Extract the location
        try:
            location_xpath = '//li[@title="Localisation"]/div/h2/a'
            location_value = self.get_element_text(driver,location_xpath)
            #print(f"Localisation: {location_value}")
        except Exception as e:
            #print(f"Localisation: None (Exception: {e})")
            location_value=None

        # Extract the duration
        try:
            duration_xpath = '//li[@title="Durée"]/div/div[2]'
            duration_value = self.get_element_text(driver,duration_xpath)
            #print(f"Durée: {duration_value}")
        except Exception as e:
            #print(f"Durée: None (Exception: {e})")
            duration_value=None

        # Extract the sector of activity
        try:
            sector_xpath = '//li[@title="Secteur d\'activité"]/div/h2'
            sector_value = self.get_element_text(driver,sector_xpath)
            #print(f"Secteur d\'activité: {sector_value}")
        except Exception as e:
            #print(f"Secteur d\'activité: None (Exception: {e})")
            sector_value=None
            
        try:
            # Locate the mission description div
            mission_description = driver.find_element(By.CLASS_NAME, 'mission-description')
            # Get the text content of the div
            mission_text = mission_description.text
        except Exception as e:
            #print("The mission description element was not found.")
            mission_text=None

        try:
            # Locate the anchor element with the class `btn btn-orange-transparent btn-orange-hover only-desk`
            contact_element = driver.find_element(By.CSS_SELECTOR, 'a.btn.btn-orange-transparent.btn-orange-hover.only-desk')
            # Get the href attribute value
            contact_href = contact_element.get_attribute('href')
            # Get the text content of the element
            contact_text = contact_element.text
        except Exception as e:
            #print("The contact element was not found.")
            contact_text=None
        
        try:
            opportunity=Opportunity(title,date_value,location_value,duration_value,sector_value,mission_text,opp_link)
            #opportunity=A(contact_text)
            print(opportunity.__dict__)
            return opportunity
        except Exception as e:
            print("Failed")
            return None
    
    def get_All_opportunities(self,mysql,app):
        with app.app_context():
            hitechProScraping=HitechProScraping()
            hitechProScraping.scrape_hi_tech_pro(mysql)
            '''driver=self.getDriver()
            driver.get("https://www.freelance-informatique.fr/offres-freelance")
            time.sleep(10)
            nb_pages=int(self.get_Pages_number(driver))
            print(nb_pages)

            i=1
            while (i<=nb_pages)and (i<100):
                try:
                    # Wait until the element is present and visible
                    if(i!=1):
                        self.loadPage(driver,i)
                    i+=1
                    print(i)
                    title_list=self.getTitles(driver)
                    j=0
                    while(j<len(title_list)):
                        print("*********************************************************************************")
                        opportunity=self.get_opportunity(driver,title_list[j+1],title_list[j])
                        mysqlServer=MySqlService()
                        #mysqlServer.getAllOpportunity(mysql)
                        mysqlServer.saveOpportunity(mysql,opportunity.title,opportunity.location,opportunity.durée,opportunity.sector,opportunity.description,opportunity.contactName,opportunity.phone,opportunity.link)
                        driver.back()
                        j+=2
                    time.sleep(3)
                    
                except Exception as e:
                    i+=1
                    print(f"Element not found or not clickable: Page {i}")
                    continue'''
        
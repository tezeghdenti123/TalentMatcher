from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.action_chains import ActionChains
from selenium.common.exceptions import TimeoutException
import chromedriver_autoinstaller
import undetected_chromedriver as uc
from selenium import webdriver
import time
import csv
import threading
import schedule
import queue
import random
import requests
import xml.etree.ElementTree as ET
from Services.MySqlService import MySqlService
class HitechProScraping:
    def getDriver(self):
        try:
            opt = webdriver.ChromeOptions()
            opt.add_argument("--start-maximized")

            chromedriver_autoinstaller.install()
            driver = webdriver.Chrome(options=opt)
            return driver
        except Exception as e:
            print(e)
            return None
    
    
    def get_links_of_opportunities(self):
        try:
            sitemap_url = 'https://esn.hitechpros.com/sitemap-missions.xml'

            # Custom User-Agent header
            headers = {
                'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3'
            }

            response = requests.get(sitemap_url, headers=headers)

            root = ET.fromstring(response.content)

            # Namespace for the sitemap XML
            namespace = {'ns': 'http://www.sitemaps.org/schemas/sitemap/0.9'}

            # Extract URLs
            urls = [elem.text for elem in root.findall('.//ns:loc', namespace)]
            return urls
        except Exception as e:
            return []
        
        
    
    def get_element_by_XPATH(self,driver,xpath):
        try:
            wait = WebDriverWait(driver, 30)
            element = wait.until(EC.presence_of_element_located((By.XPATH, xpath)))
            return element.text
        except Exception as e:
            return None

    def get_title(self,driver):
        title=self.get_element_by_XPATH(driver,'//header[@class="row mainhead mainhead__mission position-sticky pt-4 pb-3 mb-3"]//div[@class="col-12 col-xl-8 pt-2"]//div[@class="col-12 col-sm-10 col-md-9 d-flex justify-content-start align-items-center breakword"]//h1[@class="mainhead__title font-weight-bold m-0"]')
        return title
        
    def get_poste_date(self,driver):
        poste_date=self.get_element_by_XPATH(driver,'//header[@class="row mainhead mainhead__mission position-sticky pt-4 pb-3 mb-3"]//div[@class="col-12 col-xl-8 pt-2"]//div[@class="col-12 col-sm-10 col-md-9 d-flex justify-content-start align-items-center breakword"]//p[@class="mb-0 mainhead__subtitle"]')
        return poste_date



    def get_details(self,driver):
        try:
            div_element = driver.find_element(By.XPATH, '//div[@class="row d-flex justify-content-center"]')
            tjm,durée,location,sector="","","",""
            # Extract and print the text content of the child elements
            items = div_element.find_elements(By.CSS_SELECTOR, 'div.maincontent__block')
            for item in items:
                title = item.find_element(By.CSS_SELECTOR, 'p.maincontent__block__title').text
                subtitle = item.find_element(By.CSS_SELECTOR, 'p.maincontent__block__subtitle').text
                if(title=="BUDGET"):
                    tjm=subtitle
                elif(title=="DURÉE"):
                    durée=subtitle
                elif(title=="LIEU"):
                    location=subtitle
                elif(title=="CATÉGORIE TECHNIQUE"):
                    sector=subtitle
            return tjm,durée,location,sector
        except Exception as e:
            print(e)
            return None,None,None,None
        


    def load_page(self,driver,url,timeout):
        driver.set_page_load_timeout(timeout)  # Set page load timeout
        try:
            driver.get(url)
        except TimeoutException:
            timeout+=5
            if(timeout==50):
                print("Internet connection!")
            else:    
                print(f"Page load timed out after {timeout} seconds.")
                self.load_page(driver,url,timeout)
        except Exception as e:
            print("Internet Connection!")

    def get_description(self,driver):
        try:
            description_section=driver.find_element(By.XPATH,'//main[@class="main container-fluid"]//section[@class="maincontent maincontent__mission py-5"]//div[@class="row"]//div[@class="col-12 col-xl-10 offset-xl-2"]')
            driver.execute_script("arguments[0].scrollIntoView();", description_section)
            print(description_section.text)
            return description_section.text   
        except Exception as e:
            print(e)
            return None
        
    def get_skills(self,driver):
        try:
            
            skill_list=[]
            skills_div = driver.find_element(By.XPATH, '//div[@class="row"]//div[@class="maincontent__block"]//div[@class="col-12"]')
            # Extract skill information
            skill_blocks = skills_div.find_elements(By.CSS_SELECTOR, 'div.maincontent__block')
            #print(skill_blocks)
            for skill_block in skill_blocks:
                title = skill_block.find_element(By.CSS_SELECTOR, 'p.maincontent__block__title').text
                skill_list.append(title)
                print(f"Skill: {title}")
            return skill_list
        except Exception as e:
            print(e)
            return skill_list
    
    def scrape_hi_tech_pro(self,mysql):
        urls=self.get_links_of_opportunities()
        driver=self.getDriver()
        for i in range(len(urls)):
            self.load_page(driver,urls[i],10)
            time.sleep(random.randrange(5,10))
            title=self.get_title(driver)
            print(title)
            poste_date=self.get_poste_date(driver)
            print(poste_date)
            tjm,durée,location,sector=self.get_details(driver)
            print(tjm,durée,location,sector)
            skills=self.get_skills(driver)
                
            description=self.get_description(driver)
            print(description)
            
            if(skills!=None):
                result = " ".join(skills)
                description=result+description
            print("**********************",i,"************************************************")
            mysqlService= MySqlService()
            mysqlService.saveHitechProOpportunity(mysql,title,"",poste_date,durée,tjm,"",description,location,sector,urls[i])
        driver.close()





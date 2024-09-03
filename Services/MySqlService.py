import time
class MySqlService:
    def getAllOpportunity(self,mysql):
        cur = mysql.connection.cursor()
        cur.execute("SELECT * FROM opportunity")
        data = cur.fetchall()
        print("Selected successfully! ****************************************")
        cur.close()
        return data
        
    def saveOpportunity(self, mysql, title, location, durée, sector, description, link):
        query = """
        INSERT INTO opportunity (title, location, duration, sector, description, link)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
        """
        # Replace None with 'NULL' for SQL or handle it accordingly
        values = [
            title if title is not None else '',
            location if location is not None else '',
            durée if durée is not None else '',
            sector if sector is not None else '',
            description if description is not None else '',
            link if link is not None else ''
        ]

        try:
            # Execute the query
            cur = mysql.connection.cursor()
            cur.execute(query, values)
            mysql.connection.commit()
            cur.close()
            print('Data inserted successfully')
            return 'Data inserted successfully'
        except Exception as e:
            print(e)
            return str(e)

        
    def deleteAllOpportunity(self,mysql):
        try:
            # Create SQL DELETE query
            query = "DELETE FROM opportunite"

            # Execute the query
            cur = mysql.connection.cursor()
            cur.execute(query)
            mysql.connection.commit()
            cur.close()

            return 'All data deleted successfully'
        except Exception as e:
            return str(e)
        

    def updateDataBase(self,mysql,titre,desc,date,tjm,dure,location):
        self.deleteAllOpportunity(mysql)
        time.sleep(5)
        self.saveOpportunity(mysql,titre,desc,date,tjm,dure,location)
    
    def getLocationOccurence(self,mysql):
        cur = mysql.connection.cursor()
        nb=6
        cur.execute("select location,count(*) as occurrence from opportunite group by location order by occurrence desc limit %s",(nb,))
        data = cur.fetchall()
        cur.close()
        return data
    
    def getContractDurationOccurence(self,mysql):
        cur = mysql.connection.cursor()
        nb=4
        cur.execute("select duree,count(*) as occurrence from opportunite group by duree order by occurrence desc limit %s",(nb,))
        data = cur.fetchall()
        cur.close()
        return data
    
    def getTjmStatistics(self,mysql):
        cur = mysql.connection.cursor()
        cur.execute("select min(tjm) as min,avg(tjm) as avg,max(tjm) as max from opportunite")
        data = cur.fetchall()
        cur.close()
        return data
    
    def getNumberOfNewOpportunite(self,mysql):
        cur = mysql.connection.cursor()
        cur.execute("select count(*) from opportunite where date not like '%jours%'")
        data = cur.fetchall()
        cur.close()
        return data
    
    def getTjmMediane(self,mysql):
        cur = mysql.connection.cursor()
        cur.execute("with rankedValues as( select tjm, row_number() over(order by tjm) as row_num, count(*) over() as total_count from opportunite) select avg(tjm) as median from rankedValues where row_num in (floor((total_count+1)/2),ceil((total_count+1)/2))")
        data = cur.fetchall()
        cur.close()
        return data
    
    def saveTjmMediane(self,mysql,medtjm):
        query = "INSERT INTO statistics (medtjm) VALUES (%s)"

        try:
            # Execute the query
            cur = mysql.connection.cursor()
            cur.execute(query, (medtjm,))
            mysql.connection.commit()
            cur.close()
            print('Data inserted successfully')
            return 'Data inserted successfully'
        except Exception as e:
            print(e)
            return str(e)
    

    def getTendanceTitle(self,mysql):
        cur = mysql.connection.cursor()
        nb=1
        cur.execute("select titre,count(*) as occurrence from opportunite group by titre order by occurrence desc limit %s",(nb,))
        data = cur.fetchall()
        cur.close()
        return data
    
    
    def saveHitechProOpportunity(self, mysql, title,company,poste_date,duration,tjm,contract_type,description,location,sector,link):
        query = """
        INSERT INTO opportunity (title, company, poste_date, duration, tjm, contract_type,description,location,sector,link,platforme_id)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s,%s,%s,%s)
        """
        # Replace None with 'NULL' for SQL or handle it accordingly
        values = [
            title if title is not None else '',
            company if company is not None else '',
            poste_date if poste_date is not None else '',
            duration if duration is not None else '',
            tjm if tjm is not None else '',
            contract_type if contract_type is not None else '',
            description if description is not None else '',
            location if location is not None else '',
            
            sector if sector is not None else '',
            
            link if link is not None else '',
            2
        ]

        try:
            # Execute the query
            cur = mysql.connection.cursor()
            cur.execute(query, values)
            mysql.connection.commit()
            cur.close()
            print('Data inserted successfully')
            return 'Data inserted successfully'
        except Exception as e:
            print(e)
            return str(e)
class EducationExtractor:
    def getListEduOrg(self,text):    
        listOfsentences=text.split("\n")
        #print(text)
        typlesOfExperienceAndEducation=self.getTypleOfExperienceAndEducation(listOfsentences)
        listEducation=typlesOfExperienceAndEducation[0]
        listExperience=typlesOfExperienceAndEducation[1]
        #print(listEducation)
        #print(listExperience)
        listEduDate=[] #registre all the date for the Education
        listEduOrg=[] #list of the educationnal organization
        for sentence in listEducation:
            if(len(self.isdate(sentence))):
                listEduDate.append(sentence)

            if(len(self.isDiplomat(sentence))!=0):
                eduction=Education(sentence,"","")
                listEduOrg.append(eduction.__dict__)
        return listEduOrg
    
    
    def getTypleOfExperienceAndEducation(self,listOfsentences):
        expBloc=False #it is active if the bloc is Experience bloc
        eduBloc=False #it is active if it is an Education bloc
        certBloc=False
        listExperience=[]
        listEducation=[]
        for sentence in listOfsentences:

            pattern = re.compile(r'E[\s]?[xX][\s]?[pP][\s]?[eEéÉ][\s]?[rR][\s]?[iI][\s]?[eE][\s]?[nN][\s]?[cC][\s]?[eE]|E[mM][pP][lL][oO][yY][Mm][eE][nN][tT]')
            matches = re.findall(pattern, sentence)
            if(len(matches)!=0):
                expBloc=True
                eduBloc=False
                certBloc=False
            pattern1 = re.compile(r'P[aA][rR][cC][oO][uU][rR][sS]?|D[iI][pP][lL][oO][mM][aA][sS]|[dD][eE][gG][rR][eE][eE]|F[\s]?[oO][\s]?[rR][\s]?[mM][\s]?[aA][\s]?[tT][\s]?[iI][\s]?[Oo][\s]?[Nn]|[EÉ][\s]?[dD][\s]?[uU][\s]?[cC][\s]?[aA][\s]?[tT][\s]?[iI][\s]?[oO][\s]?[nN]|[P][aA][rR][cC][oO][uU][rR][sS]?[\s]*[A][Cc][aA][dD][eE][mM][iI][qQ][uU][eE]|[D][iI][pP][lL][oOôÔ][Mm][eE][sS]|E[tT][uU][dD][eE][sS]|A[Cc][aA][dD][eE][mM][iI][Cc][sS]')
            matches1 = re.findall(pattern1, sentence)
            if(len(matches1)!=0):
                expBloc=False
                eduBloc=True
                certBloc=False


            

            if(expBloc==True)and(self.is_empty_or_spaces(sentence)!=True):
                listExperience.append(sentence)
            if(eduBloc==True)and(self.is_empty_or_spaces(sentence)!=True):
                listEducation.append(sentence)
        listEducation=listEducation[1:]
        listExperience=listExperience[1:]
        typleOfEperiencesAndEducation=(listEducation,listExperience)
        return typleOfEperiencesAndEducation
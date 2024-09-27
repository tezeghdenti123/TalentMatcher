class Consultant:
    def __init__(self,name,title, email,linkedIn,phoneNumber,languages, listSkills=None,educationList=None,experienceList=None):
        self.name = name
        self.email = email
        self.linkedIn=linkedIn
        self.phoneNumber=phoneNumber
        self.languages = languages if languages else []
        self.listSkills = listSkills if listSkills else []
        self.educationList=educationList
        self.experienceList=experienceList
        self.title=title

    
    
    @classmethod
    def from_dict(cls, data):
        """Create a Consultant instance from a dictionary."""
        return cls(
            name=data['name'],
            title=data['title'],
            email=data['email'],
            linkedIn=data['linkedIn'],
            phoneNumber=data['phoneNumber'],
            languages=data.get('languages', []),
            listSkills=data.get('listSkills', []),
            educationList=data.get('educationList', []),
            experienceList=data.get('experienceList', [])
        )
    
    def add_skill(self, skill):
        self.listSkills.append(skill)
    
    def setName(self,name):
        self.name=name
    
    
    def getlanguages(self):
        return self.languages

    
    def add_language(self, language):
        self.languages.append(language)

    def remove_language(self, language):
        if language in self.languages:
            self.languages.remove(language)
        else:
            print(f"{self._name} does not have the language {language}.")
    
    def getName(self):
        return self.name
    

    def setEmail(self,email):
        self.email=email

    def getEmail(self):
        return self.email
    
    def setLinkedIn(self,linkedIN):
        self.linkedIn=linkedIN

    def getLinkedIn(self):
        return self.linkedIn
    
    def setPhoneNumber(self,phoneNumber):
        self.phoneNumber=phoneNumber

    def getPhoneNumber(self):
        return self.phoneNumber
    
    def getEducationList(self):
        return self.educationList
    
    def setEducationList(self,educationList):
        self.educationList=educationList
        
    def getExperienceList(self):
        return self.experienceList
    
    def setExperienceList(self,experienceList):
        self.experienceList=experienceList
    
    def getListSkill(self):
        return self.listSkills
    
    def setListSkills(self,listSkills):
        self.listSkills=listSkills

    def remove_skill(self, skill):
        if skill in self.listSkills:
            self.listSkills.remove(skill)
        else:
            print(f"{self.name} does not have the skill {skill}.")

    def display_info(self):
        print(f"Name: {self.name}")
        print(f"Email: {self.email}")
        print("Skills:", ', '.join(self.listSkills))
    
    def getTitle(self):
        return self.title
    
    def setTitle(self,title):
        self.title=title
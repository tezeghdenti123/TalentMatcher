import io
import unittest
from app import createApp
from Services.ExperienceExtractService import ExperienceExtractor
class ExperienceExtractorTestCase(unittest.TestCase):
    def setUp(self):
        app=createApp()
        app.config['TESTING']+True
        print("Test is about to begin =========================>")
        
        
    def test_is_contain(self):
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=0,0,0,0,0,0,0,0
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(True,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,0,0,4,4
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(False,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,4,0,8,4
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(False,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,8,0,12,4
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(False,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,0,4,4,8
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(False,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,4,4,8,8
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(True,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,8,4,12,8
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(False,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,0,8,4,12
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(False,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,4,8,8,12
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(False,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,8,12,12,16
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(False,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,8,12,12,12
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(True,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=0,0,0,0,8,8,12,12
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(False,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,4,6,8,10
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(True,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,4,6,8,18
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(False,result)
        xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2=4,4,8,8,4,6,8,9
        experienceExtractorService=ExperienceExtractor()
        result=experienceExtractorService.is_contain(xmin1,ymin1,xmax1,ymax1,xmin2,ymin2,xmax2,ymax2)
        print("Is_contain Test running")
        self.assertEqual(True,result)
        
        
        
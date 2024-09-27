from ultralytics import YOLO
from pdf2image import convert_from_path,convert_from_bytes
import numpy as np
import cv2
import re
from PIL import Image
import io
import os
       

import cv2
import torch
from paddleocr import PaddleOCR, draw_ocr


class ExperienceExtractor:
    def get_title_and_experience(self,pdf_path,model,ocr):
        images=self.pdf_to_images(pdf_path)    
        subtitle_boxs=[]
        subtitle_texts=[]
        education_texts=[]
        title_texts=[]
        for i in range(len(images)):
            # Example usage
            img_cv = cv2.cvtColor(np.array(self.resize_image(images[i])), cv2.COLOR_RGB2BGR)
            cv2.imwrite('resulttttt.png', img_cv)
            subtitle_box, subtitle_text,title_text,education_text = self.apply_yolo(img_cv,model,ocr)
            subtitle_boxs.extend(subtitle_box)
            subtitle_texts.extend(subtitle_text)
            title_texts.extend(title_text)
            education_texts.extend(education_text)

        return subtitle_texts,title_texts,education_texts
    
    def pdf_to_images(self,pdf_path,dpi=300):
        # Convert PDF to images in memory
        images = convert_from_path(pdf_path,dpi=dpi,poppler_path=r'C:\Users\GMC -  LENOVO\Downloads\Release-24.02.0-0\poppler-24.02.0\Library\bin')
        return images
    
    def resize_image(self,image, size=(640, 640)):
            return image.resize(size)
    
    def apply_yolo(self,image,model,ocr):
        results = model(image)
        subtitle_boxs,title_boxs=self.get_subtitlesBoxs_and_titlesBoxes(results,model)
        processed_subtitle_boxs=self.process_combined_subtitle(subtitle_boxs)        
        subtitle_texts,education_texts = self.extract_subtitles_text(model,processed_subtitle_boxs,image,ocr)
        title_texts=self.extract_titles_text(model,title_boxs,image,ocr) 
        return  subtitle_boxs, subtitle_texts,title_texts,education_texts
    
    
    def get_subtitlesBoxs_and_titlesBoxes(self,results,model):
        subtitle_boxs = []
        title_boxs = []
        # Extract results
        for idx, result in enumerate(results[0].boxes):
            class_id = int(result.cls[0])
            label = model.names[class_id]
            if label == "Experience":
                subtitle_boxs.append(result)
            elif label=="Title":
                title_boxs.append(result)
        return subtitle_boxs,title_boxs 
    
    
    def process_combined_subtitle(self,subtitles_boxs):
        overlapped_boxes_indexs=self.get_overlapped_Boxes_indexs(subtitles_boxs)
        newsubtitlesboxs=self.eleminate_overlapped_boxes(subtitles_boxs,overlapped_boxes_indexs)
        return newsubtitlesboxs
    
    def get_overlapped_Boxes_indexs(self,subtitles_boxs):
        listindex=[]
        for i in range(len(subtitles_boxs)):
            for j in range(len(subtitles_boxs)):
                if(i==j):
                    continue
                x1min, y1min, x1max, y1max = map(int, subtitles_boxs[i].xyxy[0]) #coordinate for the subtitle i
                x2min, y2min, x2max, y2max = map(int, subtitles_boxs[j].xyxy[0]) # coordinate for the subtitle j
                if(self.is_contain(x1min, y1min, x1max, y1max,x2min, y2min, x2max, y2max)):
                    listindex.append(j)
        return listindex
    
    
    #Tested
    def is_contain(self,x1min,y1min,x1max,y1max,x2min,y2min,x2max,y2max):
        s1=(x1max-x1min)*(y1max-y1min)
        s2=(x2max-x2min)*(y2max-y2min)
        c1=max(0,(min(x2max,x1max)-max(x2min,x1min)))*max(0,(min(y1max,y2max)-max(y1min,y2min)))
        if(s2==0):
            return True
        elif(s2>s1):
            return False
        elif(c1>=s2/2):
            return True
        else:
            return False
        
    def eleminate_overlapped_boxes(self,subtitles_boxs,listindex):
        newsubtitlesboxs=[]
        for i in range(len(subtitles_boxs)):
            if(i not in listindex):
                newsubtitlesboxs.append(subtitles_boxs[i])
        return newsubtitlesboxs
    
    
    def extract_subtitles_text(self,model,subtitle_boxs,image,ocr):
        subtitle_texts = []
        education_texts=[]
        for idx, result in enumerate(subtitle_boxs):
            cropped_image = self.croppe_image(image,result)
            ocr_result = self.extract_text_from_image(ocr,cropped_image,result)
            if ocr_result and ocr_result[0]:
                subtitle_text = " ".join([line[1][0] for line in ocr_result[0]])
                if( not(self.isDiplomat(subtitle_text))and(not(self.isProject(subtitle_text)))and self.ContainDate(subtitle_text)):
                    subtitle_texts.append(subtitle_text)
                elif(not(self.isProject(subtitle_text))and self.isDiplomat(subtitle_text)):
                    education_texts.append(subtitle_text)     
        return subtitle_texts,education_texts 
    
    
    def croppe_image(self,image,result):
        xmin, ymin, xmax, ymax = map(int, result.xyxy[0])
            # Draw rectangle
        cv2.rectangle(image, (xmin, ymin), (xmax, ymax), (255, 0, 0), 2)
            # Draw label and confidence
        label_text = f""
        cv2.putText(image, label_text, (xmin, ymin - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 0, 0), 2)
        cropped_image = image[ymin:ymax, xmin:xmax]
        
        return cropped_image
    
    
    def extract_text_from_image(self,ocr,cropped_image,result):
        xmin, ymin, xmax, ymax = map(int, result.xyxy[0])
        new_width = (xmax-xmin)* 4
        new_height = (ymax-ymin) * 8
        print(new_width,new_height)
        resized_image = cv2.resize(cropped_image, (new_width, new_height), interpolation=cv2.INTER_CUBIC)    
        alpha = 1.0  # Contrast control (1.0-3.0)
        beta = 0     # Brightness control (0-100)
        contrasted_image = cv2.convertScaleAbs(resized_image, alpha=alpha, beta=beta)
        gray_image = cv2.cvtColor(contrasted_image, cv2.COLOR_BGR2GRAY)
        cv2.imwrite("C:\\Users\\GMC -  LENOVO\\Desktop\\Result\\"+str(xmin)+".png", gray_image)
        ocr_result = ocr.ocr(gray_image, cls=True)
        return ocr_result
    
    
    def isDiplomat(self,sentence):
        pattern1 = re.compile(r'[dD][eE][gG][rR][eE][eE]|[pP][rR][eE][pP][aA][rR][Aa][tT][oO][rR][yY]|[cC][yY][cC][lL][eE]|[fF][aA][cC][aA][lL][tT][yY]|[sS][Cc][hH][oO][Oo][lL]|[Hh][iI][gG][hH][eE][rR]|[Uu][nN][iI][vV][eE][rR][sS][iI][tT]|[Ii][nN][sS][tT][iI][tT][uU][tT][\s][pP][rR][eE][pP][aA]|[cC][eE][rR][tT][iI]|[A][tT][tT][eE][sS][tT][aA][tT][iI][Oo][Nn]|[^A-Za-z][Ff][oO][rR][mM][aA][Tt][iI][oO][nN]|BUT[^a-zA-Z]|L2|L1|IUP|DESS|LPIC|Certiﬁcat:|C[eE][rR][tT][iI][fF][iI][cC][aA][tT][^a-zA-Z]|(?:^|[^A-Za-z])Formation[\s](?!et|d[eu]|[cC][lL][iI][eE][nN][tT]|\W*$)|System and Information Technology|PMP|[gG][rR][aA][dD][uU][tT][eE]|1 ère année|Ingénieurie|[Ss]oftware [eE]ngineer|[Cc][Yy][cC][lL][eE][\s][dDiI]|[cC][eE][rR][tT][iI][fF][iI][cC][Aa][Tt][eE]?[^a-zA-Z]|[Pp][rR][Ee][pP][aA][rR][aA][tT][oO][rR][Yy]|[^tT][\s][Pp][rR][eEéÉ][pP][aA][rR][aA][tT][oO][iI][rR]|B[\.\s]?[sS][\.\s]?[Cc][^a-zA-Z]|[pP][\.\s]?[hH][\.\s]?[dD]|[Dd][eE][gG][Rr][Ii][eE]|[Cc][Pp][gG][Ee]|[mM][aA][iIî][tT][rR][iI][sS][eE][\s][^dD]|[Bb][tT][sS]|[lL][iI][cC][eE][Nn][cCsS][eE]|MS[cC]|(?:^|[^A-Za-z])[mM][aA][sS][tT][eEèÈ][rR]|[dD][iI][pP][lL][oOô][mM][eE][\s]|[dD][iI][pP][lL][oOô][mM][aA]|[Bb][aA][cC][^a-zA-Z]|[Bb][aA][cC][^kKhH][^Oo]|[bB][aA][Cc][Hh][Ee][Ll][oO]|[iI][nN][gG][eÉEé][nN][iI][eE][rR][iI][eE][\s][eE][nN]|[Dd][uU][tT][\s]|[^a-zA-Z]?B\.?E[^a-zA-Z]|[Aa][sS][sS][oO][cC][iI][aA][tT][eE]|[Dd][oO][cC][tT][oO][rR]|M[\s\.]2|[Eée][cC][oO][lL][eE]') # DUT BTS master formation licence cycl cert bac diplome genie BE associat doctor 
        matches1 = re.findall(pattern1, sentence)
        if(len(matches1)>0):
            return True
        else:
            return False
    
    
    def extract_titles_text(self,model,title_boxs,image,ocr):
        title_texts=[]
        if(len(title_boxs)==0):
            return title_texts
        else:
            result=title_boxs[0]
            cropped_image = self.croppe_image(image,result)
            ocr_result = self.extract_text_from_image(ocr,cropped_image,result)
            if ocr_result and ocr_result[0]:
                title_text = " ".join([line[1][0] for line in ocr_result[0]])
                title_texts.append(title_text)
            return title_texts



    



    


    def isProject(self,sentence):
            pattern = re.compile(r'[Pp][rR][oO][jJ][eE][tT]')
            matches1 = re.findall(pattern, sentence)
            if(len(matches1)>0):
                return True
            else:
                return False


    
    def isExperience(self,sentence):
            pattern = re.compile(r'E[\s]?[xX][\s]?[pP][\s]?[eEéÉ][\s]?[rR][\s]?[iI][\s]?[eE][\s]?[nN][\s]?[cC][\s]?[eE]|E[mM][pP][lL][oO][yY][Mm][eE][nN][tT]')
            matches1 = re.findall(pattern, sentence)
            if(len(matches1)>0):
                return True
            else:
                return False
            

    def ContainDate(self,sentence):
            pattern1 = re.compile(r'[2zZ][0o9O][0-9zZoO][0-9zZoO]|[1][9][98765][0-9oOzZ]') # DUT BTS master formation licence cycl cert bac diplome genie BE associat doctor 
            matches1 = re.findall(pattern1, sentence)
            if(len(matches1)>0):
                return True
            else:
                return False

    

    def convert_pdf_binary_to_images(self,pdf_binary):
        # Convert PDF binary data to a list of images (one per page)
        images = convert_from_bytes(pdf_binary)
        image_buffers = []
        for image in images:
            buffer = io.BytesIO()
            image.save(buffer, format='PNG')
            buffer.seek(0)  # Move the pointer to the beginning of the BytesIO object
            image_buffers.append(buffer)
        return image_buffers

    def get_image_path(self,image):
        img_byte_arr = io.BytesIO()
        image.save(img_byte_arr, format='png')
        img_byte_arr = img_byte_arr.getvalue()
        return img_byte_arr
    
    
    
    
        


    
    


     

    
    
        
                    
                    
                    
    



    


    

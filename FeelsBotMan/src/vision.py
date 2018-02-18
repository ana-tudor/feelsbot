# from google.cloud import vision
# client = vision.ImageAnnotatorClient()

import argparse
import io
import time
import glob
import cv2
import sys

# [START import_client_library]
from google.cloud import vision
# [END import_client_library]
from google.cloud.vision import types
from PIL import Image, ImageDraw

"""Trying to take pictures"""

import numpy as np
import cv2
import subprocess


cap = cv2.VideoCapture(0)

ret, picFrame = cap.read()
cv2.imwrite("/Users/rebeccazeng/Desktop/Projects/feelsbot/FeelsBotMan/src/pics/img0.png", picFrame)
# cap.close()
ret = cap.set(3,640)
ret = cap.set(4,360)
ret = cap.set(11,1)
ret = cap.set(12,1)
ret = cap.set(16,1)

def detect_faces(path):
    """Detects faces in an image."""
    client = vision.ImageAnnotatorClient()

    with io.open(path, 'rb') as image_file:
        content = image_file.read()

    image = types.Image(content=content)

    response = client.face_detection(image=image)
    faces = response.face_annotations

    # Names of likelihood from google.cloud.vision.enums
    likelihood_name = ('UNKNOWN', 'VERY_UNLIKELY', 'UNLIKELY', 'POSSIBLE',
                       'LIKELY', 'VERY_LIKELY')
    # print('Faces:')
    data = []

    for face in faces:
        data.extend([face.anger_likelihood, face.joy_likelihood, face.surprise_likelihood])

    print(data)
    printer(data)
    return data
def printer(data):
    # really shouldn't need this but idk
    print(data)
# print(detect_faces("angry-face.jpeg"))
# video_pics = glob.glob('pics/*.jpg')
# for pic in video_pics:
#      detect_faces(pic)

data = detect_faces("pics/img0.png")
file = open("face_analysis.txt", "w")
file.write(str(data))
file.close()




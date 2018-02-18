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

ret, picFrame = cap.capture()
cv2.imwrite("/Users/rebeccazeng/Desktop/Projects/feelsbot/FeelsBotMan/src/pics/img"+str(time.time())+".png", picFrame)
cap.close()
ret = cap.set(3,640)
ret = cap.set(4,360)

""""""

# # [START def_detect_face]
# def detect_face(face_file, max_results=4):
#     """Uses the Vision API to detect faces in the given file.

#     Args:
#         face_file: A file-like object containing an image with faces.

#     Returns:
#         An array of Face objects with information about the picture.
#     """
#     # [START get_vision_service]
#     client = vision.ImageAnnotatorClient()
#     # [END get_vision_service]

#     content = face_file.read()
#     image = types.Image(content=content)

#     return client.face_detection(image=image).face_annotations
# # [END def_detect_face]


# # [START def_highlight_faces]
# def highlight_faces(image, faces, output_filename):
#     """Draws a polygon around the faces, then saves to output_filename.

#     Args:
#       image: a file containing the image with the faces.
#       faces: a list of faces found in the file. This should be in the format
#           returned by the Vision API.
#       output_filename: the name of the image file to be created, where the
#           faces have polygons drawn around them.
#     """
#     im = Image.open(image)
#     draw = ImageDraw.Draw(im)

#     for face in faces:
#         box = [(vertex.x, vertex.y)
#                for vertex in face.bounding_poly.vertices]
#         draw.line(box + [box[0]], width=5, fill='#00ff00')

#     im.save(output_filename)
# # [END def_highlight_faces]


# # [START def_main]
# def main(input_filename, output_filename, max_results):
#     with open(input_filename, 'rb') as image:
#         faces = detect_face(image, max_results)
#         print('Found {} face{}'.format(
#             len(faces), '' if len(faces) == 1 else 's'))

#         print('Writing to file {}'.format(output_filename))
#         # Reset the file pointer, so we can read the file again
#         image.seek(0)
#         highlight_faces(image, faces, output_filename)
# # [END def_main]


# if __name__ == '__main__':
#     parser = argparse.ArgumentParser(
#         description='Detects faces in the given image.')
#     parser.add_argument(
#         'input_image', help='the image you\'d like to detect faces in.')
#     parser.add_argument(
#         '--out', dest='output', default='out.jpg',
#         help='the name of the output file.')
#     parser.add_argument(
#         '--max-results', dest='max_results', default=4,
#         help='the max results of face detection.')
#     args = parser.parse_args()

#     main(args.input_image, args.output, args.max_results)

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
        data.extend([face.anger_likelihood, face.joy_likelihood, face.surprise_likelihood, time.time()])
        # print('anger number: {}'.format(face.anger_likelihood))
        # print('anger: {}'.format(likelihood_name[face.anger_likelihood]))
        # print('joy: {}'.format(likelihood_name[face.joy_likelihood]))
        # print('surprise: {}'.format(likelihood_name[face.surprise_likelihood]))
    print(data)
    printer(data)
    return data
def printer(data):
    # really shouldn't need this but idk
    print(data)
# print(detect_faces("angry-face.jpeg"))
video_pics = glob.glob('pics/*.jpg')
for pic in video_pics:
     detect_faces(pic)



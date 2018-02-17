import cv2
import os

print(cv2.__version__)
vidcap = cv2.VideoCapture('meanwhile.mp4')
success,image = vidcap.read()
count = 0
success = True
while success:
  pics = []
  success,image = vidcap.read()
  path = '/Users/rebeccazeng/Desktop/Projects/feelsbot/pics'
  print('Read a new frame: ', success)
  cv2.imwrite(os.path.join(path, ("frame%d.jpg" % count)), image)     # save frame as JPEG file
  count += 1

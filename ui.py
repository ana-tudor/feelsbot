import pygame
import sys
import time
from PIL import Image 
import glob


pygame.init() # you have to call this at the start, 
                   # if you want to use this module.
screen = pygame.display.set_mode((500,500))
clock = pygame.time.Clock()

# bot_surface = pygame.surface.Surface(500, 500)

# [text]
black, white = (0, 0, 0), (255, 255, 255)

# initialize font
font1 = pygame.font.SysFont("monospace", 25)
# render font
def captions(label):
    caption_returned = font1.render(label, True, black)
    # screen.blit(caption, (100,100))
    return caption_returned


# [sprite]
class TestSprite(pygame.sprite.Sprite):
    def __init__(self):
        super(TestSprite, self).__init__()

        self.images = []
        for filename in glob.glob('bears2/*.PNG'): #assuming png
            img = Image.open(filename)
            surface = pygame.image.load(filename)
            self.images.append(surface)
        # self.images.append(pygame.image.load(img))
        # self.images.append(pygame.image.load('bears/bear1.png'))
        # self.images.append(pygame.image.load('bears/bear2.png'))
        # self.images.append(pygame.image.load('bears/bear3.png'))
        # self.images.append(pygame.image.load('bears/bear4.png'))
        # self.images.append(pygame.image.load('bears/bear5.png'))

        self.index = 0
        self.image = self.images[self.index]
        self.rect = pygame.Rect(5, 5, 64, 64)
        self.direction = 1

    def update(self):
        '''This method iterates through the elements inside self.images and 
        displays the next one each tick. For a slower animation, you may want to 
        consider using a timer of some sort so it updates slower.'''
        # if self.index >= len(self.images):
        #     self.index = 0
        self.image = self.images[self.index]
        self.index = self.direction + self.index
        if self.index >= len(self.images) or self.index < 0:
            self.direction = self.direction * -1
            self.index += self.direction

command_list = []
def write(command):
    """ Writes the command (either start or end) onto a txt file """
    print("Creating a new txt file.")
    start_or_end = open("start_or_end.txt","w")
    command_list.append(command)
    for each_command in command_list:
        start_or_end.write("%s\n" % each_command)


def main():
    pygame.init()
    my_bear = TestSprite()
    bears_group = pygame.sprite.Group(my_bear)
    vid_on = False
    # initial caption
    caption = captions("Hello! Double click to begin recording.")

    def update_screen(caption_blit, coords):
        bears_group.update()
        bears_group.draw(screen)
        # pygame.display.flip()
        screen.blit(caption_blit, coords)
        pygame.display.flip()

    # start with a picture of the bear
    update_screen(caption, (100,100))
    started = 0;

    # checking the information passed in through a .txt file
    def check_data(data_file):
        file = open(data_file)
        read_files = file.readlines()
        file.close()
        return read_files
    print(type(check_data("start_or_end.txt")))
    # display whatever is currently in the file
    

    while True:
        if not started:
            event = pygame.event.wait()
            if pygame.mouse.get_pressed()[0] and vid_on == False:
            # this is when we start taking in video
            # update_screen()
            # print("this is when the video feed starts")
                write("start") # this writes to a txt file for the Java
                caption = captions("started recording")
                update_screen(caption, (100,100))
                started = 1;
                time_start = clock.get_time()
                print(time_start)

        # vid_on = False  
        
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
        pygame.display.flip()
        input = str(check_data("start_or_end.txt"))
        if input == [] or input 
        feeling = captions("started recording")
        screen.blit(feeling, (300,100))
        update_screen(feeling, (100,100))

    



        clock.tick(1600)
        pygame.time.wait(100)
        # Calling the 'bears_group.update' function calls the 'update' function of all 
        # its member sprites. Calling the 'bears_group.draw' function uses the 'image'
        # and 'rect' attributes of its member sprites to draw the sprite.



# main
if __name__ == '__main__':
    main()
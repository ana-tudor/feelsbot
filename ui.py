import pygame
import sys
import time

pygame.init() # you have to call this at the start, 
                   # if you want to use this module.
screen = pygame.display.set_mode((500,500))
# bot_surface = pygame.surface.Surface(500, 500)


# [sprite]
class TestSprite(pygame.sprite.Sprite):
    def __init__(self):
        super(TestSprite, self).__init__()

        self.counter = 0;
        self.images = []
        self.images.append(pygame.image.load('bears/bear1.png'))
        self.images.append(pygame.image.load('bears/bear2.png'))
        # assuming both images are 64x64 pixels

        self.index = 0
        self.image = self.images[self.index]
        # self.image = self.images[round(self.index)]
        self.rect = pygame.Rect(5, 5, 64, 64)

    def update(self):
        '''This method iterates through the elements inside self.images and 
        displays the next one each tick. For a slower animation, you may want to 
        consider using a timer of some sort so it updates slower.'''
        if self.index >= len(self.images):
            self.index = 0
        self.image = self.images[self.index]
        self.counter += 1
        if self.counter == 5:
            self.index += 1
            self.counter = 0

command_list = []
def write(command):
    """ Writes the command (either start or end) onto a txt file """
    print("Creating a new txt file.")
    start_or_end = open("start_or_end.txt","w")
    command_list.append(command)
    for each_command in command_list:
        start_or_end.write("%s\n" % each_command)

# [text]
black = (0,0,0)
white = (255, 255, 255)
# initialize
font1 = pygame.font.SysFont("monospace", 14)
# render
def captions(label):
    caption = font1.render(label, True, white)
    screen.blit(caption, (0,100))
    return caption

def main():
    pygame.init()

    my_sprite = TestSprite()
    my_group = pygame.sprite.Group(my_sprite)
    vid_on = False
    caption_list = []

    while True:
        # my_group.update()
        # my_group.draw(screen)
        # pygame.display.flip()
        event = pygame.event.wait()
        if event.type == pygame.QUIT:
            print("a")
            pygame.quit()
            sys.exit(0)
        if pygame.mouse.get_pressed()[0] and vid_on == False:
            print("b")
            my_group.update()
            my_group.draw(screen)
            pygame.display.flip()
            print("this is when the video feed starts")
            write("start")
            caption_list.append(captions("hello world"))
            vid_on = True

            # this is when we start taking in video
        if pygame.mouse.get_pressed()[2] and vid_on == True:
            print("this is when the video feed ends")
            write("end")
            caption_list.append(captions("hi world"))
            vid_on = False
            # this is when we need to stop taking in video
        for caption in caption_list:
            pygame.display.flip()
            # screen.blit(caption, (100,100))
        # Calling the 'my_group.update' function calls the 'update' function of all 
        # its member sprites. Calling the 'my_group.draw' function uses the 'image'
        # and 'rect' attributes of its member sprites to draw the sprite.



# main
if __name__ == '__main__':
    main()
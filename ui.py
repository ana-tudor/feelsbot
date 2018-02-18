import pygame
import sys
import time

pygame.init() # you have to call this at the start, 
                   # if you want to use this module.
screen = pygame.display.set_mode((500,500))
clock = pygame.time.Clock()

# bot_surface = pygame.surface.Surface(500, 500)


# [sprite]
class TestSprite(pygame.sprite.Sprite):
    def __init__(self):
        super(TestSprite, self).__init__()

        self.images = []
        self.images.append(pygame.image.load('bears/bear0.png'))
        self.images.append(pygame.image.load('bears/bear1.png'))
        self.images.append(pygame.image.load('bears/bear2.png'))
        self.images.append(pygame.image.load('bears/bear3.png'))
        self.images.append(pygame.image.load('bears/bear4.png'))
        self.images.append(pygame.image.load('bears/bear5.png'))

        self.index = 0
        self.image = self.images[self.index]
        self.rect = pygame.Rect(5, 5, 64, 64)

    def update(self):
        '''This method iterates through the elements inside self.images and 
        displays the next one each tick. For a slower animation, you may want to 
        consider using a timer of some sort so it updates slower.'''
        # if self.index >= len(self.images):
        #     self.index = 0
        self.image = self.images[self.index]
        self.index = (self.index + 1) % 6

command_list = []
def write(command):
    """ Writes the command (either start or end) onto a txt file """
    print("Creating a new txt file.")
    start_or_end = open("start_or_end.txt","w")
    command_list.append(command)
    for each_command in command_list:
        start_or_end.write("%s\n" % each_command)

# [text]
black, white = (0, 0, 0), (255, 255, 255)

# initialize font
font1 = pygame.font.SysFont("monospace", 25)
# render font
def captions(label):
    caption = font1.render(label, True, white)
    # screen.blit(caption, (100,100))
    return caption

def main():
    pygame.init()

    my_sprite = TestSprite()
    my_group = pygame.sprite.Group(my_sprite)
    vid_on = False
    caption_list = []

    def update_screen():
        my_group.update()
        my_group.draw(screen)
        pygame.display.flip()

    # start with a picture of the bear
    update_screen()

    while True:
        event = pygame.event.wait()
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit(0)
        if pygame.mouse.get_pressed()[0] and vid_on == False:
            # this is when we start taking in video
            update_screen()
            print("this is when the video feed starts")
            write("start")
            caption_list.append(captions("hello world"))
            vid_on = True
        if pygame.mouse.get_pressed()[2] and vid_on == True:
            # this is when we need to stop taking in video  
            update_screen()          
            print("this is when the video feed ends")
            write("end")
            caption_list.append(captions("hi world"))
            vid_on = False  
        for caption in caption_list:
            pygame.display.flip()
            screen.blit(caption, (100,100))
        clock.tick(160)
        # Calling the 'my_group.update' function calls the 'update' function of all 
        # its member sprites. Calling the 'my_group.draw' function uses the 'image'
        # and 'rect' attributes of its member sprites to draw the sprite.



# main
if __name__ == '__main__':
    main()
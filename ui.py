import pygame
import sys

def load_image(name):
    image = pygame.image.load(name)
    return image

class TestSprite(pygame.sprite.Sprite):
    def __init__(self):
        super(TestSprite, self).__init__()
        self.counter = 0;
        self.images = []
        self.images.append(load_image('bears/bear1.png'))
        self.images.append(load_image('bears/bear2.png'))
        # self.images.append(load_image('bear2.png'))
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
        if self.counter == 40:
            self.index += 1
            self.counter = 0

def main():
    pygame.init()
    screen = pygame.display.set_mode((500, 500))

    my_sprite = TestSprite()
    my_group = pygame.sprite.Group(my_sprite)

    while True:
        event = pygame.event.poll()
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit(0)

        # Calling the 'my_group.update' function calls the 'update' function of all 
        # its member sprites. Calling the 'my_group.draw' function uses the 'image'
        # and 'rect' attributes of its member sprites to draw the sprite.
        my_group.update()
        my_group.draw(screen)
        pygame.display.flip()

if __name__ == '__main__':
    main()
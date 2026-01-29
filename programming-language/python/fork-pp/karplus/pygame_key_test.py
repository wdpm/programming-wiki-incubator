import pygame

pygame.init()

while True:
    for event in pygame.event.get():
        if event.type == pygame.KEYDOWN:
            print("key pressed")

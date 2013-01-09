DroidSnake
==========
An educational home Android game project. 
Created a platform-agnostic framework of interfaces, so that the game can be 
easily ported to PC, for example. Uses Model-View-Controller architecture 
so that I can easily change stuff up. Initial commit is a fully functioning game, 
for which some care was taken so that it can run on as low as API level 3 (Cupcake). 
The "true" minimum API level I'm aiming for is 8 (Froyo).

TODO: 
1) Implement a sort of spotlight shader that limits rendering to a radius 
around the snake's head, has a small chance of triggering when eating a target 
and lasting for 3-5 seconds 
2) Create a moving target that has a small chance of spawning, 
that tries to run away from the snake 
3) Add gesture control for turning 
4) Change graphics and sound assets, as the currently used ones 
are from Beginning Android Games, 2nd edition

Stronghold Defense
==================

Build your own stronghold and defend against hordes of monsters.  
It's yet another tower defense clone if you didn't guessed already :)

What makes it slightly different is the focus on building complete  
fortified stronghold rather than single independent towers.

Target platforms for now: desktop (Windows/Linux/Mac OS X) + Android (which is main focus).


Building
------

At the beginning you need to download some libGDX and scala libraries (this need to be done just once):
```bash
sbt update-libs
```
Then you can compile and run desktop version to check if it works:
```bash
sbt "project desktop" run
```
You can also generate project for IntelliJ
```bash
sbt gen-idea
```
Unlucky you will need to manually adjust your Proguard configuration path.
Edit .idea_modules/android.imf file and change PROGUARD_CFG_PATH value to "/../android/src/main/proguard.cfg".


Gameplay
------
![alt text](http://oi44.tinypic.com/adzq55.jpg "Screenshoot")
Stronghold Defense
==================

![Screenshot](http://oi39.tinypic.com/x54ncy.jpg)

Experiment to build realtime android game fully in scala.
I would say I'm quite happy with the result even if there were some difficulties.
Two biggest ones:
- Garbage collector (this is problematic also in java, but it limit idomatic scala usage and thus hurt me even more).
- Big scala runtime and amount of generated interfaces. You need to use proguard to strip output jars. Thankfully to libgdx this is partially mitigated.

Since recently I feel a bit bored and want to do something else than gamedev I decided to open source what I did so far. I think all assets execept loading screen are fully open sourced and you can use them even commercially (but I cannot guarantee that and I don't hold any rights for them).
All monster models comes from Flare (great source of assets!). Other assets are taken from http://opengameart.org/ and http://mrbubblewand.wordpress.com/ (spell and effects).

Code is distributed under Apache 2 License.

If you will build something usefull based on this - let me know, I'm curious! ;)

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
Right now 2 wepons are available: lighting and whirlwind. You can see both of them here:
http://i.minus.com/ic0JMJeABN5km.gif

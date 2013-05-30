package com.pkukielka.stronghold-defense

import com.badlogic.gdx.backends.lwjgl._

object Main extends App {
    val cfg = new LwjglApplicationConfiguration()
    cfg.title = "Stronghold Defense"
    cfg.height = 480
    cfg.width = 320
    cfg.useGL20 = true
    new LwjglApplication(new StrongholdDefense(), cfg)
}

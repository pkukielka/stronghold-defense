package com.pkukielka.stronghold

import com.badlogic.gdx.backends.lwjgl._

object Main extends App {
    val cfg = new LwjglApplicationConfiguration()
    cfg.title = "Stronghold Defense"
    cfg.height = 800
    cfg.width = 600
    cfg.useGL20 = true
    new LwjglApplication(new StrongholdDefense(), cfg)
}

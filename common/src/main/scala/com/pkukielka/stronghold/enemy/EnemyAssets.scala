package com.pkukielka.stronghold.enemy

class EnemyAssets(name: String, frameDuration: Float) {
  val baseType = name.split("_").head
  val gfx = new EnemyGfx(name, 0.10f)
  val sound = new EnemySound(baseType)
}

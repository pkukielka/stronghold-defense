package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.Gdx

class EnemySound(name: String) {
  val hit = loadSound(name + "_hit")
  val die = loadSound(name + "_die")
  val ment = loadSound(name + "_ment")
  val phys = loadSound(name + "_phys")
  val criticDie = loadSound(name + "_critdie")

  def loadSound(name: String) = Gdx.audio.newSound(Gdx.files.internal("data/sound/enemies/" + name + ".ogg"))
}

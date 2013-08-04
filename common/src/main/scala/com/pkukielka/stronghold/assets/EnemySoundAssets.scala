package com.pkukielka.stronghold.assets

import com.badlogic.gdx.Gdx

class EnemySoundAssets(name: String) {
  val hit = loadSound(name + "_hit")
  val die = loadSound(name + "_die")
  val ment = loadSound(name + "_ment")
  val phys = loadSound(name + "_phys")
  val criticDie = loadSound(name + "_critdie")

  def loadSound(name: String) = Gdx.audio.newSound(Gdx.files.internal("data/sound/enemies/" + name + ".ogg"))
}

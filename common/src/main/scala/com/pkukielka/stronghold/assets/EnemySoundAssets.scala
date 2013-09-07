package com.pkukielka.stronghold.assets

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.assets.AssetManager

class EnemySoundAssets(name: String) {
  val enemiesSoundsDir = "data/sound/enemies/"

  var hit: Sound = _
  var die: Sound = _
  var ment: Sound = _
  var phys: Sound = _
  var criticDie: Sound = _

  def load(assetManager: AssetManager) {
    Array("hit", "die", "ment", "phys", "critdie").foreach{
      sound =>
        assetManager.load(enemiesSoundsDir + name + "_" + sound + ".ogg", classOf[Sound])
    }
  }

  def cache(assetManager: AssetManager) {
    def getSound(soundType: String) =
      assetManager.get(enemiesSoundsDir + name + "_" + soundType + ".ogg", classOf[Sound])

    hit = getSound("hit")
    die = getSound("die")
    ment = getSound("ment")
    phys = getSound("phys")
    criticDie = getSound("critdie")
  }
}

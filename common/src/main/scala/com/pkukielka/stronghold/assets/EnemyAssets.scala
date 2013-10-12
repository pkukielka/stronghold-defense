package com.pkukielka.stronghold.assets

import com.badlogic.gdx.assets.AssetManager

class EnemyAssets(name: String, frameDuration: Float) {
  val baseType = name.split("_").head
  val gfx = new EnemyGfxAssets(name, frameDuration)
  val sound = new EnemySoundAssets(baseType)

  def load(assetManager: AssetManager) {
    gfx.load(assetManager)
    sound.load(assetManager)
  }

  def cache(assetManager: AssetManager) {
    gfx.cache(assetManager)
    sound.cache(assetManager)
  }
}
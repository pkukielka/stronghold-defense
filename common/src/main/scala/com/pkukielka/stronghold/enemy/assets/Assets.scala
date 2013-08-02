package com.pkukielka.stronghold.enemy.assets

class Assets(name: String, frameDuration: Float) {
  val baseType = name.split("_").head
  val gfx = new GfxAssets(name, frameDuration)
  val sound = new SoundAssets(baseType)
}
package com.pkukielka.stronghold.assets

import com.badlogic.gdx.graphics.g2d.{Animation, TextureAtlas}
import com.badlogic.gdx.assets.AssetManager

class EffectGfxAssets(name: String, frameDuration: Float = 0.05f) {
  val effectsTextures = "data/textures/effects/"
  val spellsEffectsAtlas = effectsTextures + "spells.atlas"
  val miscEffectsAtlas = effectsTextures + "misc.atlas"

  var effectAnimation: Animation = _

  def load(assetManager: AssetManager) {
    assetManager.load(spellsEffectsAtlas, classOf[TextureAtlas])
    assetManager.load(miscEffectsAtlas, classOf[TextureAtlas])
  }

  def cache(assetManager: AssetManager) {
    effectAnimation = {
      val region = {
        val region = assetManager.get(spellsEffectsAtlas, classOf[TextureAtlas]).findRegions(name)
        if (region.size > 0) {
          region
        }
        else {
          assetManager.get(miscEffectsAtlas, classOf[TextureAtlas]).findRegions(name)
        }
      }
      new Animation(frameDuration, new com.badlogic.gdx.utils.Array(region))
    }
  }

}

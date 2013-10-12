package com.pkukielka.stronghold.assets

import com.badlogic.gdx.graphics.g2d.{Animation, TextureAtlas}
import scala.collection.convert.WrapAsScala.asScalaIterator
import com.badlogic.gdx.assets.AssetManager

class EnemyGfxAssets(name: String, frameDuration: Float) {
  val animationFrames = 8
  val enemiesTextures = "data/textures/enemies/"
  val enemiesMoveAtlas = enemiesTextures + "enemies_move.atlas"
  val enemiesDieAtlas = enemiesTextures + "enemies_die.atlas"

  var dieAnimations: Array[Animation] = _
  var moveAnimations: Array[Animation] = _

  def load(assetManager: AssetManager) {
    assetManager.load(enemiesMoveAtlas, classOf[TextureAtlas])
    assetManager.load(enemiesDieAtlas, classOf[TextureAtlas])
  }

  def cache(assetManager: AssetManager) {
    def createAnimations(atlasName: String, regionName: String): Array[Animation] = {
      val regions = assetManager.get(atlasName, classOf[TextureAtlas]).findRegions(regionName)
      regions.iterator().toArray.grouped(animationFrames).map {
        frames =>
          new Animation(frameDuration, new com.badlogic.gdx.utils.Array(frames))
      }.toArray
    }

    dieAnimations = createAnimations(enemiesDieAtlas, name + "_die")
    moveAnimations = createAnimations(enemiesMoveAtlas, name + "_move")
  }


}

package com.pkukielka.stronghold.assets

import com.badlogic.gdx.graphics.g2d.{Animation, TextureAtlas}
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import scala.collection.convert.WrapAsScala.asScalaIterator

class EnemyGfxAssets(name: String, frameDuration: Float) {
  val dieAnimations = createAnimations(Assets.dieAtlas, name + "_die", frameDuration)
  val moveAnimations = createAnimations(Assets.moveAtlas, name + "_move", frameDuration)

  def createAnimations(atlas: TextureAtlas, regionName: String, frameDuration: Float): Array[Animation] = {
    val regions: com.badlogic.gdx.utils.Array[AtlasRegion] = atlas.findRegions(regionName)
    regions.iterator().toArray.grouped(8).map {
      frames =>
        new Animation(frameDuration, new com.badlogic.gdx.utils.Array(frames))
    }.toArray
  }
}

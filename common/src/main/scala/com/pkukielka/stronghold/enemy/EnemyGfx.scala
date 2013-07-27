package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.graphics.g2d.{Animation, TextureAtlas}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import scala.collection.convert.WrapAsScala.asScalaIterator

object EnemyGfx {
  val moveAtlas = new TextureAtlas(Gdx.files.internal("data/textures/enemies/enemies_move.atlas"))
  val dieAtlas = new TextureAtlas(Gdx.files.internal("data/textures/enemies/enemies_die.atlas"))

  def createDieAnimations(regionName: String, frameDuration: Float) =
    createAnimations(dieAtlas, regionName + "_die", frameDuration)

  def createMoveAnimations(regionName: String, frameDuration: Float) =
    createAnimations(moveAtlas, regionName + "_move", frameDuration)

  def createAnimations(atlas: TextureAtlas, regionName: String, frameDuration: Float): Array[Animation] = {
    val regions: com.badlogic.gdx.utils.Array[AtlasRegion] = atlas.findRegions(regionName)
    regions.iterator().toArray.grouped(8).map {
      frames =>
        new Animation(frameDuration, new com.badlogic.gdx.utils.Array(frames))
    }.toArray
  }
}

class EnemyGfx(name: String, frameDuration: Float) {
  val dieAnimations = EnemyGfx.createDieAnimations(name, frameDuration)
  val moveAnimations = EnemyGfx.createMoveAnimations(name, frameDuration)
}

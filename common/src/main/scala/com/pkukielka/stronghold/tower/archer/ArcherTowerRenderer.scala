package com.pkukielka.stronghold.tower.archer

import com.badlogic.gdx.graphics.g2d.{Sprite, SpriteBatch}
import com.pkukielka.stronghold.{Renderer, IsometricMapUtils}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

object ArcherTowerRenderer {
  val towerSprite = new Sprite(new Texture(Gdx.files.internal("data/textures/towers/archerTower.png")))
}

trait ArcherTowerRenderer extends Renderer {
  self: ArcherTower =>
  val pos  = new Vector2

  import ArcherTowerRenderer._

  private def width: Float = towerSprite.getRegionWidth * IsometricMapUtils.unitScale

  private def height: Float = towerSprite.getRegionHeight * IsometricMapUtils.unitScale

  override def depth = {
    pos.set(position).add(-0.5f, -0.5f)
    IsometricMapUtils.cameraToMapY(pos)
  }

  override def draw(batch: SpriteBatch, deltaTime: Float): Unit = {
    batch.draw(towerSprite,
      IsometricMapUtils.mapToCameraX(position) - 0.5f,
      IsometricMapUtils.mapToCameraY(position) - 0.5f,
      width, height)
  }
}

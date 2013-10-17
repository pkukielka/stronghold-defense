package com.pkukielka.stronghold.tower.archer

import com.pkukielka.stronghold.tower.Renderer
import com.badlogic.gdx.graphics.g2d.{Sprite, SpriteBatch}
import com.pkukielka.stronghold.IsometricMapUtils
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx

object ArcherTowerRenderer {
  val towerSprite = new Sprite(new Texture(Gdx.files.internal("data/textures/towers/archerTower.png")))
}

trait ArcherTowerRenderer extends Renderer {
  self: ArcherTower =>

  import ArcherTowerRenderer._

  private def width: Float = towerSprite.getRegionWidth * IsometricMapUtils.unitScale

  private def height: Float = towerSprite.getRegionHeight * IsometricMapUtils.unitScale

  override def draw(batch: SpriteBatch): Unit = {
    batch.draw(towerSprite,
      IsometricMapUtils.mapToCameraX(position) - 0.5f,
      IsometricMapUtils.mapToCameraY(position) - 0.5f,
      width, height)
  }
}

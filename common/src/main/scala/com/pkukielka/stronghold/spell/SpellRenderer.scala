package com.pkukielka.stronghold.spell

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pkukielka.stronghold.{IsometricMapUtils, Renderer}
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion

trait SpellRenderer extends Renderer {
  self: Spell =>

  protected def currentFrame = assets.effectAnimation.getKeyFrame(time, false)

  private def width: Float = currentFrame.getRegionWidth * IsometricMapUtils.unitScale

  private def height: Float = currentFrame.getRegionHeight * IsometricMapUtils.unitScale

  override def depth = IsometricMapUtils.cameraToMapY(position)

  protected def getXAdjustment = currentFrame.asInstanceOf[AtlasRegion].offsetX * IsometricMapUtils.unitScale - 0.5f

  protected def getYAdjustment = currentFrame.asInstanceOf[AtlasRegion].offsetY * IsometricMapUtils.unitScale - 0.5f

  override def draw(batch: SpriteBatch, deltaTime: Float): Unit = {
    if (!assets.effectAnimation.isAnimationFinished(time)) {
      batch.draw(currentFrame,
        IsometricMapUtils.mapToCameraX(position) + getXAdjustment,
        IsometricMapUtils.mapToCameraY(position) + getYAdjustment,
        width, height)
    }
  }
}

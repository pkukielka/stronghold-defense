package com.pkukielka.stronghold.spell.wind

import com.pkukielka.stronghold.{IsometricMapUtils, Renderer}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pkukielka.stronghold.assets.Assets

trait WhirlwindRenderer extends Renderer {
  self: Whirlwind =>

  def draw(batch: SpriteBatch, deltaTime: Float): Unit = {
    if (!isCompleted) {
      val frame = Assets.spellWind3.effectAnimation.getKeyFrame(time, false)
      batch.draw(frame, IsometricMapUtils.mapToCameraX(position) - 0.5f, IsometricMapUtils.mapToCameraY(position) - 0.5f,
        frame.getRegionWidth * IsometricMapUtils.unitScale, frame.getRegionHeight * IsometricMapUtils.unitScale)
    }
  }

  def depth: Float = IsometricMapUtils.cameraToMapY(position)
}

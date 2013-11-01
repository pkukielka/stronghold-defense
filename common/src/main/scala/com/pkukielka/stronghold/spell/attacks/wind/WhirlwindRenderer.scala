package com.pkukielka.stronghold.spell.attacks.wind

import com.pkukielka.stronghold.{IsometricMapUtils, Renderer}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pkukielka.stronghold.assets.Assets
import com.pkukielka.stronghold.spell.Attack
import com.badlogic.gdx.Gdx

object WhirlwindRenderer {
  val sound = Gdx.audio.newSound(Gdx.files.internal("data/sound/powers/whirlwind.ogg"))
}

trait WhirlwindRenderer extends Renderer with Attack {
  self: Whirlwind =>

  def draw(batch: SpriteBatch, deltaTime: Float): Unit = {
    if (!isCompleted) {
      val frame = Assets.spellWind3.effectAnimation.getKeyFrame(time, false)
      batch.draw(frame, IsometricMapUtils.mapToCameraX(position) - 0.5f, IsometricMapUtils.mapToCameraY(position) - 0.5f,
        frame.getRegionWidth * IsometricMapUtils.unitScale, frame.getRegionHeight * IsometricMapUtils.unitScale)
    }
  }

  abstract override def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float) {
    super.init(xStart, yStart, xEnd, yEnd, heightsDifference)
    WhirlwindRenderer.sound.play()
  }

  def depth: Float = IsometricMapUtils.cameraToMapY(position)
}

package com.pkukielka.stronghold.spell.attacks.thunder

import com.pkukielka.stronghold.{IsometricMapUtils, Renderer}
import com.badlogic.gdx.graphics.g2d.{Animation, SpriteBatch}
import com.pkukielka.stronghold.assets.Assets
import com.pkukielka.stronghold.spell.Attack
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion

object ThunderRenderer {
  val thunders = Array(
    Gdx.audio.newSound(Gdx.files.internal("data/sound/powers/thunder/thunder1.ogg")),
    Gdx.audio.newSound(Gdx.files.internal("data/sound/powers/thunder/thunder2.ogg")),
    Gdx.audio.newSound(Gdx.files.internal("data/sound/powers/thunder/thunder3.ogg"))
  )

  def sound = thunders(scala.util.Random.nextInt(thunders.size))
}

trait ThunderRenderer extends Renderer with Attack {
  self: Thunder =>

  val flipped = scala.util.Random.nextBoolean()

  var animation: Animation = _

  protected def currentFrame = animation.getKeyFrame(time, false)

  protected def width = currentFrame.getRegionWidth * IsometricMapUtils.unitScale

  protected def height = currentFrame.getRegionHeight * IsometricMapUtils.unitScale

  protected def xBaseAdjustment = currentFrame.asInstanceOf[AtlasRegion].offsetX * IsometricMapUtils.unitScale

  protected def xAdjustment = if (flipped) 1f - xBaseAdjustment else xBaseAdjustment

  protected def yAdjustment = currentFrame.asInstanceOf[AtlasRegion].offsetY * IsometricMapUtils.unitScale

  protected def scaleX = if (flipped) -1f else 1f

  protected def xPosBase = IsometricMapUtils.mapToCameraX(position) + xAdjustment

  protected def xPos = if (flipped) xPosBase else xPosBase - 1f

  protected def yPos = IsometricMapUtils.mapToCameraY(position) + yAdjustment

  override def draw(batch: SpriteBatch, deltaTime: Float): Unit = {
    if (isActive) {
      batch.draw(currentFrame, xPos, yPos, 0, 0, width, height, scaleX, 1f, 0)
    }
  }

  override abstract def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float) {
    super.init(xStart, yStart, xEnd, yEnd, heightsDifference)
    animation = if (scala.util.Random.nextBoolean()) Assets.spellThunder1.effectAnimation else Assets.spellThunder2.effectAnimation
    ThunderRenderer.sound.play()
  }

  override def depth: Float = IsometricMapUtils.cameraToMapY(position)
}

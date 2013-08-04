package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.graphics.g2d.{ParticleEffectPool, SpriteBatch, TextureRegion}
import com.pkukielka.stronghold.IsometricMapUtils
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color
import com.pkukielka.stronghold.effect.FireEffect

object EnemyRenderer {

  object lifeBar {
    val width = 0.4f
    val height = 0.06f
    val margin = 0.02f
    val distance = 0.1f
  }

}

trait EnemyRenderer {
  self: Enemy =>

  val unitScale = 1 / 96f
  var fireEffect: ParticleEffectPool#PooledEffect = null

  def currentFrame: TextureRegion = {
    if (isDead) {
      assets.gfx.dieAnimations(angle).getKeyFrame(animationTime, false)
    }
    else {
      assets.gfx.moveAnimations(angle).getKeyFrame(animationTime, true)
    }
  }

  def width: Float = currentFrame.getRegionWidth * unitScale

  def height: Float = currentFrame.getRegionHeight * unitScale

  def isHit(xScreen: Float, yScreen: Float, utils: IsometricMapUtils): Boolean = {
    val pos = utils.mapToCameraCoordinates(position.x, position.y)
    pos.x <= xScreen && pos.x + width >= xScreen && pos.y <= yScreen && pos.y + height >= yScreen
  }

  def setOnFire(time: Float) {
    if (fireEffect == null) {
      fireEffect = FireEffect.obtain
    }

    fireEffect.setDuration((time * 1000).toInt)
    fireEffect.reset()
  }

  def drawLifeBar(shapeRenderer: ShapeRenderer, utils: IsometricMapUtils) {
    import EnemyRenderer.lifeBar

    if (!isDead && life != maxLife) {
      val pos = utils.mapToCameraCoordinates(position.x, position.y)
      val x = pos.x + (width - 0.5f) / 2
      val y = pos.y + height + lifeBar.distance
      val lifeRatio = life.toFloat / maxLife

      shapeRenderer.setColor(Color.BLACK)
      shapeRenderer.rect(x, y, lifeBar.width, lifeBar.height)
      shapeRenderer.setColor(1.2f - Math.pow(lifeRatio, 2).toFloat, 1f - Math.pow(1f - lifeRatio, 2).toFloat - 0.2f, 0f, 1f)
      shapeRenderer.rect(x + lifeBar.margin, y + lifeBar.margin, lifeRatio * (lifeBar.width - 2 * lifeBar.margin), lifeBar.height - 2 * lifeBar.margin)
    }
  }

  def drawModel(batch: SpriteBatch, deltaTime: Float, utils: IsometricMapUtils) {
    val pos = utils.mapToCameraCoordinates(position.x, position.y)
    batch.draw(currentFrame, pos.x, pos.y, width, height)

    if (fireEffect != null) {
      if (fireEffect.isComplete) {
        fireEffect.free()
        fireEffect = null
      }
      else {
        fireEffect.setPosition(pos.x + width * 0.5f, pos.y + height * 0.5f)
        fireEffect.draw(batch, deltaTime)
      }
    }
  }

}

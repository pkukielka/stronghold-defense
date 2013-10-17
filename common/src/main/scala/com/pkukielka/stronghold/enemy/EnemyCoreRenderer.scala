package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.graphics.g2d.{ParticleEffectPool, SpriteBatch, TextureRegion}
import com.pkukielka.stronghold.IsometricMapUtils
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color
import com.pkukielka.stronghold.effect.FireEffect
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion

object EnemyCoreRenderer {

  object lifeBar {
    val width = 0.4f
    val height = 0.06f
    val margin = 0.02f
    val distance = 0.2f
  }

}

trait EnemyCoreRenderer extends Enemy{
  self: EnemyCore =>

  var fireEffect: ParticleEffectPool#PooledEffect = null

  override def width: Float = currentFrame.getRegionWidth * IsometricMapUtils.unitScale

  override def height: Float = currentFrame.getRegionHeight * IsometricMapUtils.unitScale

  abstract override def die() {
    super.die()
    assets.sound.die.play()
  }

  abstract override def harm(damage: Float) {
    super.harm(damage)
    if (damage > maxLife * 0.1f) {
      assets.sound.hit.play()
    }
  }

  abstract override def turn() {
    super.turn()
    if (scala.math.random > 0.95) {
      assets.sound.ment.play()
    }
  }

  private def currentFrame: TextureRegion = {
    if (isDead) {
      assets.gfx.dieAnimations(angle).getKeyFrame(animationTime, false)
    }
    else {
      assets.gfx.moveAnimations(angle).getKeyFrame(animationTime, true)
    }
  }

  private def angle = (((247.5 - Math.toDegrees(Math.atan2(directionVector.y, directionVector.x))) % 360) / 45).toInt

  private def getXAdjustment = -0.5f + currentFrame.asInstanceOf[AtlasRegion].offsetX * IsometricMapUtils.unitScale

  private def getYAdjustment = -0.5f + currentFrame.asInstanceOf[AtlasRegion].offsetY * IsometricMapUtils.unitScale

  abstract override def setOnFire(time: Float, damagePerSecond: Float) {
    super.setOnFire(time, damagePerSecond)

    if (fireEffect == null) {
      fireEffect = FireEffect.obtain
    }

    fireEffect.setDuration((time * 1000).toInt)
    fireEffect.reset()
  }

  def drawLifeBar(shapeRenderer: ShapeRenderer) {
    import EnemyCoreRenderer.lifeBar

    if (!isDead && life != maxLife) {
      val x = IsometricMapUtils.mapToCameraX(position) + (width - lifeBar.width) / 2 + getXAdjustment
      val y = IsometricMapUtils.mapToCameraY(position) + height + lifeBar.distance + getYAdjustment
      val lifeRatio = life / maxLife

      shapeRenderer.setColor(Color.BLACK)
      shapeRenderer.rect(x, y, lifeBar.width, lifeBar.height)
      shapeRenderer.setColor(1.2f - Math.pow(lifeRatio, 2).toFloat, 1f - Math.pow(1f - lifeRatio, 2).toFloat - 0.2f, 0f, 1f)
      shapeRenderer.rect(x + lifeBar.margin, y + lifeBar.margin, lifeRatio * (lifeBar.width - 2 * lifeBar.margin), lifeBar.height - 2 * lifeBar.margin)
    }
  }

  def drawModel(batch: SpriteBatch, deltaTime: Float) {
    batch.draw(currentFrame,
      IsometricMapUtils.mapToCameraX(position) + getXAdjustment,
      IsometricMapUtils.mapToCameraY(position) + getYAdjustment,
      width, height)

    if (fireEffect != null) {
      if (fireEffect.isComplete) {
        fireEffect.free()
        fireEffect = null
      }
      else {
        fireEffect.setPosition(
          IsometricMapUtils.mapToCameraX(position) + width * 0.5f + getXAdjustment,
          IsometricMapUtils.mapToCameraY(position) + height * 0.5f + getYAdjustment)
        fireEffect.draw(batch, deltaTime)
      }
    }
  }

}

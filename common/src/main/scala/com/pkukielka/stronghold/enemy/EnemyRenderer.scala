package com.pkukielka.stronghold.enemy

import com.pkukielka.stronghold.IsometricMapUtils
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color

object EnemyRenderer {

  object lifeBar {
    val width = 0.4f
    val height = 0.06f
    val margin = 0.02f
    val distance = 0.2f
  }

}

trait EnemyRenderer extends Enemy with com.pkukielka.stronghold.Renderer {
  self: EnemyCore =>

  override def depth = IsometricMapUtils.cameraToMapY(position)

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

  protected def currentFrame: TextureRegion = {
    if (isDead) {
      assets.gfx.dieAnimations(angle).getKeyFrame(animationTime, false)
    }
    else {
      assets.gfx.moveAnimations(angle).getKeyFrame(animationTime, true)
    }
  }

  private def angle = (((247.5 - Math.toDegrees(Math.atan2(directionVector.y, directionVector.x))) % 360) / 45).toInt

  protected def getXAdjustment = currentFrame.asInstanceOf[AtlasRegion].offsetX * IsometricMapUtils.unitScale - 0.5f

  protected def getYAdjustment = currentFrame.asInstanceOf[AtlasRegion].offsetY * IsometricMapUtils.unitScale - 0.5f

  def drawLifeBar(shapeRenderer: ShapeRenderer) {
    import EnemyRenderer.lifeBar

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

  override def draw(batch: SpriteBatch, deltaTime: Float) {
    batch.draw(currentFrame,
      IsometricMapUtils.mapToCameraX(position) + getXAdjustment,
      IsometricMapUtils.mapToCameraY(position) + getYAdjustment,
      width, height)
  }
}

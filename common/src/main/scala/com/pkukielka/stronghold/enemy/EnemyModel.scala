package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.pkukielka.stronghold.IsometricMapUtils
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color

object EnemyModel {

  object lifeBar {
    val width = 0.4f
    val height = 0.06f
    val margin = 0.02f
    val distance = 0.1f
  }

}

class EnemyModel(enemy: Enemy) {

  import enemy._

  val unitScale = 1 / 96f

  def currentFrame: TextureRegion = {
    if (enemy.isDead) {
      assets.gfx.dieAnimations(enemy.angle).getKeyFrame(enemy.animationTime, false)
    }
    else {
      assets.gfx.moveAnimations(enemy.angle).getKeyFrame(enemy.animationTime, true)
    }
  }

  def width: Float = currentFrame.getRegionWidth * unitScale

  def height: Float = currentFrame.getRegionHeight * unitScale

  def isHit(xScreen: Float, yScreen: Float, utils: IsometricMapUtils): Boolean = {
    val pos = utils.getScreenCoordinates(enemy.position.x, position.y)
    pos.x <= xScreen && pos.x + width >= xScreen && pos.y <= yScreen && pos.y + height >= yScreen
  }

  def drawLifeBar(shapeRenderer: ShapeRenderer, utils: IsometricMapUtils) {
    import EnemyModel.lifeBar

    if (!enemy.isDead && life != maxLife) {
      val pos = utils.getScreenCoordinates(enemy.position.x, position.y)
      val x = pos.x + (width - 0.5f) / 2
      val y = pos.y + height + lifeBar.distance
      val lifeRatio = life.toFloat / maxLife

      shapeRenderer.setColor(Color.BLACK)
      shapeRenderer.rect(x, y, lifeBar.width, lifeBar.height)
      shapeRenderer.setColor(1.2f - Math.pow(lifeRatio, 2).toFloat, 1f - Math.pow(1f - lifeRatio, 2).toFloat - 0.2f, 0f, 1f)
      shapeRenderer.rect(x + lifeBar.margin, y + lifeBar.margin, lifeRatio * (lifeBar.width - 2 * lifeBar.margin), lifeBar.height - 2 * lifeBar.margin)
    }
  }

  def drawModel(batch: SpriteBatch, utils: IsometricMapUtils) {
    val pos = utils.getScreenCoordinates(enemy.position.x, position.y)
    batch.draw(currentFrame, pos.x, pos.y, width, height)
  }

}

package com.pkukielka.stronghold.tower

import com.pkukielka.stronghold.enemy.Enemy
import com.badlogic.gdx.math.Vector2
import com.pkukielka.stronghold.tower.attacks.{FireArrow, Arrow}
import scala.collection.mutable.ArrayBuffer
import com.pkukielka.stronghold.IsometricMapUtils
import com.badlogic.gdx.graphics.g2d.{Sprite, SpriteBatch}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx

object ArcherTower {
  val towerSprite = new Sprite(new Texture(Gdx.files.internal("data/textures/towers/archerTower.png")))

  object version1 {
    val shootInterval = 1f
  }
}

class ArcherTower(position: Vector2) extends Tower {
  import ArcherTower._

  var time = Math.random().toFloat
  var properties = version1

  private def findNewTarget(enemies: Array[Enemy]) = enemies.minBy{ enemy=>
    if (enemy.isDead) Int.MaxValue else enemy.position.dst(position)
  }

  private def width: Float = towerSprite.getRegionWidth * IsometricMapUtils.unitScale

  private def height: Float = towerSprite.getRegionHeight * IsometricMapUtils.unitScale

  override def update(deltaTime: Float, enemies: Array[Enemy], bullets: ArrayBuffer[Attack]) {
    time += deltaTime
    if (time > properties.shootInterval) {
      time -= properties.shootInterval

      val count = (Math.random() * 3f).toInt

      for (i <- (0 to count)) {
        val target = findNewTarget(enemies)

        if (!target.isDead && target.position.dst(position) < 10) {
          val arrow = (Math.random() * 10).toInt match {
            case x if (x >= 0 && x < 6) => new Arrow
            case _ => new FireArrow
          }
          arrow.init(position.x, position.y, target.position.x, target.position.y, 0)
          bullets += arrow
        }
      }
    }
  }

  override def draw(batch: SpriteBatch) {
    batch.draw(towerSprite,
      IsometricMapUtils.mapToCameraX(position) - 0.5f,
      IsometricMapUtils.mapToCameraY(position) - 0.5f,
      width, height)
  }
}

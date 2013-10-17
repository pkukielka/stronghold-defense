package com.pkukielka.stronghold.tower.archer

import com.pkukielka.stronghold.enemy.EnemyCore
import com.badlogic.gdx.math.Vector2
import scala.collection.mutable.ArrayBuffer
import com.pkukielka.stronghold.tower.{Attack, Tower}

object ArcherTower {
  object version1 {
    val shootInterval = 1f
  }
}

class ArcherTower(val position: Vector2) extends Tower {
  import ArcherTower._

  var time = scala.math.random.toFloat
  var properties = version1

  private def findNewTarget(enemies: Array[EnemyCore]) = enemies.minBy{ enemy=>
    if (enemy.isDead) Int.MaxValue else enemy.position.dst(position)
  }

  override def update(deltaTime: Float, enemies: Array[EnemyCore], bullets: ArrayBuffer[Attack]) {
    time += deltaTime
    if (time > properties.shootInterval) {
      time -= properties.shootInterval

      val count = (scala.math.random * 3f).toInt

      for (i <- 0 to count) {
        val target = findNewTarget(enemies)

        if (!target.isDead && target.position.dst(position) < 10) {
          val arrow = (scala.math.random * 10).toInt match {
            case x if x >= 0 && x < 6 => new Arrow with ArrowRenderer
            case _ => new FireArrow with FireArrowRenderer
          }
          arrow.init(position.x, position.y, target.position.x, target.position.y, 0)
          bullets += arrow
        }
      }
    }
  }
}

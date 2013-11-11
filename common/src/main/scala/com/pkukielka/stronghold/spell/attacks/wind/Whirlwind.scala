package com.pkukielka.stronghold.spell.attacks.wind

import com.pkukielka.stronghold.spell.Attack
import com.pkukielka.stronghold.enemy.Enemy
import com.badlogic.gdx.math.{Bezier, Vector2}

object Whirlwind {
  val range = 5f
  val angle = 120f
  val attackRange = 1f
  val baseDamage = 40f
  val rotationsPerSecond = 3
}

class Whirlwind extends Attack {
  protected val position: Vector2 = new Vector2()
  private val path = new Bezier[Vector2]
  override protected val lifeTime= 1.4f

  object temp {
    val start: Vector2 = new Vector2()
    val middle: Vector2 = new Vector2()
    val target: Vector2 = new Vector2()
    val delta = new Vector2()
  }

  import Whirlwind._

  def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float) {
    activate()
    temp.start.set(xStart, yStart)
    temp.target.set(xEnd, yEnd).sub(temp.start).nor.scl(range)

    temp.middle.set(temp.target).div(2f).rotate((angle * 0.5f) - (scala.math.random.toFloat * angle)).add(temp.start)
    temp.target.rotate((angle * 0.5f) - (scala.math.random.toFloat * angle)).add(temp.start)

    path.set(temp.start, temp.middle, temp.target)
    position.set(temp.start)
  }

  def update(deltaTime: Float, enemies: Array[Enemy]): Unit = {
    if (advanceTime(deltaTime)) {
      temp.delta.set(position)
      path.valueAt(position, time / lifeTime)
      temp.delta.sub(position).scl(-1f)

      for (enemy <- enemies if !enemy.isDead && enemy.position.dst(position) < attackRange) {
        enemy.hit(baseDamage * deltaTime)
      }

      for (enemy <- enemies if !enemy.isDead && enemy.position.dst(position) < attackRange) {
        enemy.position.add(temp.delta)
        enemy.directionVector.rotate(360 * rotationsPerSecond * deltaTime)
        enemy.isHold = true
        return
      }

      temp.delta.scl(0.1f)
      for (enemy <- enemies if enemy.isDead && enemy.position.dst(position) < attackRange) {
        enemy.position.add(temp.delta)
        enemy.directionVector.rotate(30 * rotationsPerSecond * deltaTime)
      }
    }
  }
}

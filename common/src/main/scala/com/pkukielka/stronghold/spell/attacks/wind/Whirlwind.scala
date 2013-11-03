package com.pkukielka.stronghold.spell.attacks.wind

import com.pkukielka.stronghold.spell.Attack
import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.badlogic.gdx.math.{Bezier, Vector2}

object Whirlwind {
  val baseDamage = 20f
  val timeToComplete = 1.4f
  val range = 5f
  val rotationsPerSecond = 3
}

class Whirlwind extends Attack {
  protected val position: Vector2 = new Vector2()
  protected var time = 0f
  private val path = new Bezier[Vector2]

  object temp {
    val start: Vector2 = new Vector2()
    val middle: Vector2 = new Vector2()
    val target: Vector2 = new Vector2()
    val delta = new Vector2()
  }

  import Whirlwind._

  def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float) {
    time = 0f

    temp.start.set(xStart, yStart)
    temp.target.set(xEnd, yEnd).sub(temp.start).nor.scl(range)
    temp.middle.set(temp.target).div(2f).rotate(60f - (scala.math.random.toFloat * 120f)).add(temp.start)
    temp.target.rotate(60f - (scala.math.random.toFloat * 120f)).add(temp.start)

    path.set(temp.start, temp.middle, temp.target)
    position.set(temp.start)
  }

  def update(deltaTime: Float, enemies: Array[Enemy]): Unit = {
    if (!isCompleted) {
      time = (time + deltaTime).min(timeToComplete)
      temp.delta.set(position)
      path.valueAt(position, time / timeToComplete)
      temp.delta.sub(position).scl(-1f)

      for (enemy <- enemies if !enemy.isDead && enemy.position.dst(position) < 1f) {
        enemy.hit(baseDamage * deltaTime)
        enemy.position.add(temp.delta)
        enemy.directionVector.rotate(360 * rotationsPerSecond * deltaTime)
        enemy.isHold = true
        return
      }
    }
  }

  def isCompleted: Boolean = time == timeToComplete
}

package com.pkukielka.stronghold.spell

import com.badlogic.gdx.math.Vector2
import scala.collection.mutable.ArrayBuffer
import com.pkukielka.stronghold.enemy.{Enemy, PathFinder}
import com.pkukielka.stronghold.utils.Utils
import com.pkukielka.stronghold.Influence
import com.pkukielka.stronghold.assets.EffectGfxAssets

abstract class Spell(pathFinder: PathFinder) {
  var time = 0f
  var timeFromLastShoot = 0f
  var currentShootInterval = shootInterval
  var deactivated = false
  var influence: Influence = null
  val position = new Vector2()

  pathFinder.update

  protected var isActive = false

  private val enemyDistance = (enemy: Enemy) => if (enemy.isDead) Int.MaxValue else enemy.position.dst(position)

  def init(positionX: Float, positionY: Float) = {
    time = 0f
    timeFromLastShoot = 0f
    position.set(positionX, positionY)
    influence = pathFinder.influencesManager.add(10000f, position, 1000, 500, range)
    isActive = true
  }

  protected def assets: EffectGfxAssets

  protected def range: Float

  protected def attack: Attack

  protected def lifeTime: Float

  protected def shootInterval: Float

  protected def targetAttack(enemies: Array[Enemy], attacks: ArrayBuffer[Attack]) {
    val target = Utils.minBy(enemies, enemyDistance)
    if (!target.isDead && target.position.dst(position) < range) {
      val newAttack = attack
      newAttack.init(position.x, position.y, target.position.x, target.position.y, 0)
      attacks += newAttack
    }
  }

  def update(deltaTime: Float, enemies: Array[Enemy], attacks: ArrayBuffer[Attack]) {
    time += deltaTime

    if (time < lifeTime) {
      timeFromLastShoot += deltaTime

      if (timeFromLastShoot > currentShootInterval) {
        timeFromLastShoot -= currentShootInterval
        currentShootInterval = shootInterval
        targetAttack(enemies, attacks)
      }
    }
    else if (isActive) {
      isActive = false
      pathFinder.influencesManager.remove(influence)
      influence = null
    }
  }
}

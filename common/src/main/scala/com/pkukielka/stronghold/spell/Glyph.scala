package com.pkukielka.stronghold.spell

import com.badlogic.gdx.math.Vector2
import scala.collection.mutable.ArrayBuffer
import com.pkukielka.stronghold.enemy.{Enemy, PathFinder}
import com.pkukielka.stronghold.utils.Utils

class Glyph(val position: Vector2, pathFinder: PathFinder, attack: => Attack, attackProperties: AttackProperties) {
  var time = 0f
  var timeFromLastShoot = 0f
  val lifeTime = 5f
  val shootInterval = 1f
  var deactivated = false
  val influence = pathFinder.influencesManager.add(10000f, position, 1000, 500, attackProperties.range)

  pathFinder.update

  protected def isActive = time < lifeTime

  protected def assets = attackProperties.assets

  private val enemyDistance = (enemy: Enemy) => if (enemy.isDead) Int.MaxValue else enemy.position.dst(position)

  def update(deltaTime: Float, enemies: Array[Enemy], attacks: ArrayBuffer[Attack]) {
    time += deltaTime

    if (isActive) {
      timeFromLastShoot += deltaTime

      if (timeFromLastShoot > shootInterval) {
        timeFromLastShoot -= shootInterval

        val target = Utils.minBy(enemies, enemyDistance)
        if (!target.isDead && target.position.dst(position) < attackProperties.range) {
          val newAttack = attack
          newAttack.init(position.x, position.y, target.position.x, target.position.y, 0)
          attacks += newAttack
        }
      }
    }
    else if (!deactivated) {
      deactivated = true
      pathFinder.influencesManager.remove(influence)
    }
  }
}

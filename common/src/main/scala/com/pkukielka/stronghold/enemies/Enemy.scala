package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.graphics.g2d.{Animation, TextureAtlas, TextureRegion}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import scala.language.implicitConversions
import scala.collection.convert.WrapAsScala.asScalaIterator
import com.pkukielka.stronghold.PathFinder
import scala.annotation.tailrec

class EnemySound(enemyName: String) {
  val hit = loadSound(enemyName + "_hit")
  val die = loadSound(enemyName + "_die")
  val ment = loadSound(enemyName + "_ment")
  val phys = loadSound(enemyName + "_phys")
  val criticDie = loadSound(enemyName + "_critdie")

  def loadSound(name: String) = Gdx.audio.newSound(Gdx.files.internal("data/sound/enemies/" + name + ".ogg"));
}

object Enemy {
  val moveAtlas = new TextureAtlas(Gdx.files.internal("data/textures/enemies/enemies_move.atlas"))
  val dieAtlas = new TextureAtlas(Gdx.files.internal("data/textures/enemies/enemies_die.atlas"))

  def createDieAnimations(regionName: String, frameDuration: Float) =
    createAnimations(dieAtlas, regionName + "_die", frameDuration)

  def createMoveAnimations(regionName: String, frameDuration: Float) =
    createAnimations(moveAtlas, regionName + "_move", frameDuration)

  def createAnimations(atlas: TextureAtlas, regionName: String, frameDuration: Float): Array[Animation] = {
    val regions: com.badlogic.gdx.utils.Array[AtlasRegion] = atlas.findRegions(regionName)
    regions.iterator().toArray.grouped(8).map {
      frames =>
        new Animation(frameDuration, new com.badlogic.gdx.utils.Array(frames))
    }.toArray
  }
}

abstract class Enemy(pathFinder: PathFinder) {
  var animationTime = 0.0f
  var xOffset = (0.5f + (Math.random() - 0.5f) / 4).toFloat
  var yOffset = (0.5f + (Math.random() - 0.5f) / 4).toFloat
  var position = pathFinder.getFreePosition.add(xOffset, yOffset, 0.0f)
  var target = pathFinder.getFreePosition
  var directionVector = new Vector3()
  var life = getMaxLife

  def getMaxLife = 100

  def getVelocity: Float

  def getMoveAnimations: Array[Animation]

  def getDeathAnimations: Array[Animation]

  def getSound: EnemySound

  def isDead = life <= 0

  def getCurrentFrame: TextureRegion = {
    if (isDead) {
      getDeathAnimations(getAngle).getKeyFrame(animationTime, false)
    }
    else {
      getMoveAnimations(getAngle).getKeyFrame(animationTime, true)
    }
  }

  private def getAngle = (((247.5 - Math.toDegrees(Math.atan2(directionVector.y, directionVector.x))) % 360) / 45).toInt

  def hit(damage: Int) {
    if (!isDead)
    {
      life -= damage
      if (isDead) {
        animationTime = 0
        getSound.die.play()
      }
      else {
        getSound.hit.play()
      }
    }
  }

  def move(deltaTime: Float) {
    val next = pathFinder.getNextStep(position, target)
    directionVector.set(
      pathFinder.node2posX(next) + xOffset - position.x,
      pathFinder.node2posY(next) + yOffset - position.y,
      0.0f).nor()
    directionVector.scl(getVelocity * deltaTime)
    position.add(directionVector)


    if (position.epsilonEquals(target, 1.0f)) {
      target = pathFinder.getFreePosition
      if (Math.random() > 0.95) getSound.ment.play()
    }
  }

  def update(deltaTime: Float) {
    if (deltaTime < 0.05)
    {
      animationTime += deltaTime
      if (!isDead) move(deltaTime)
    }

  }
}

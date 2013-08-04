package com.pkukielka.stronghold.tower.bullet

import com.badlogic.gdx.graphics.g2d.{Sprite, SpriteBatch}
import com.badlogic.gdx.math.{Vector2, Bezier}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx

object Arrow {
  val maxMiss = 1f
  val maxRange = 20f
  val velocity = 30f
  val heightRangeIncrease = 1.2f
  val spriteScale = 1 / 256f

  val arrow_broken_sprite = new Sprite(new Texture(Gdx.files.internal("data/textures/bullets/arrow_broken.png")))
  val arrow_sprite = new Sprite(new Texture(Gdx.files.internal("data/textures/bullets/arrow.png")))
}

class Arrow extends Bullet {
  val start: Vector2 = new Vector2()
  val middle: Vector2 = new Vector2()
  val direction: Vector2 = new Vector2()
  val path = new  Bezier[Vector2]
  var angle = 0f
  var time = 0f
  var timeToComplete = 0f

  import Arrow._

  override def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float) {
    super.init(xStart, yStart, xEnd, yEnd, heightsDifference)

    val distance = position.dst(target)
    val currentMiss = (distance / maxRange) * maxMiss
    val heightAddition = (distance / maxRange) * distance * 0.5f

    start.set(position)
    target.add(((0.5 - Math.random()) * currentMiss).toFloat, ((0.5 - Math.random()) * currentMiss).toFloat)
    middle.set(position).add(target).scl(0.5f).add(0, heightAddition)
    path.set(start, middle, target)

    time = 0f
    timeToComplete = distance / velocity
  }

  def isCompleted = time == timeToComplete

  def drawSpecialEffects(batch: SpriteBatch) {
  }

  def update(deltaTime: Float) {
    if (!isCompleted) {
      time = (time + deltaTime).min(timeToComplete)
      direction.set(position)
      path.valueAt(position, time / timeToComplete)
      direction.sub(position).nor().scl(-1, -1)
      angle = Math.toDegrees(Math.atan2(direction.y, direction.x)).toFloat
    }
  }

  def draw(batch: SpriteBatch) {
    if (isCompleted) {
      batch.draw(arrow_broken_sprite, position.x, position.y, 0, 0,
        arrow_broken_sprite.getWidth, arrow_broken_sprite.getHeight, spriteScale, spriteScale, angle)
    }
    else {
      batch.draw(arrow_sprite, position.x, position.y, 0, 0,
        arrow_sprite.getWidth, arrow_sprite.getHeight, spriteScale, spriteScale, angle)
    }

    drawSpecialEffects(batch)
  }
}

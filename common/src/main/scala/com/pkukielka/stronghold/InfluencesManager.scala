package com.pkukielka.stronghold

import com.badlogic.gdx.math.Vector3
import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.utils.Pool.Poolable
import com.badlogic.gdx.utils.Pool

class Influence() extends Poolable {
  var endTime: Float = 0.0f
  val center: Vector3 = new Vector3()
  var valueCenter: Float = 0.0f
  var valueEdge: Float = 0.0f
  var range: Float = 0.0f

  def init(endTime: Float, center: Vector3, valueCenter: Float, valueEdge: Float, range: Float) {
    this.endTime = endTime
    this.center.set(center)
    this.valueCenter = valueCenter
    this.valueEdge = valueEdge
    this.range = range
  }

  def reset() {
    endTime = 0.0f
    center.set(0.0f, 0.0f, 0.0f)
    valueCenter = 0.0f
    valueEdge = 0.0f
    range = 0.0f
  }
}

class InfluencesManager(width: Int, height: Int) {
  val influencesFiled = Array.fill(width, height)(0)
  val influences = ArrayBuffer.empty[Influence]
  var currentTime = 0.0f

  val influencesPool = new Pool[Influence] {
    def newObject(): Influence = new Influence()
  }

  def add(duration: Float, center: Vector3, valueCenter: Float, valueEdge: Float, range: Float): Influence = {
    val influence = influencesPool.obtain()
    influence.init(currentTime + duration, center, valueCenter, valueEdge, range)
    updateField(influence, influence.valueCenter, influence.valueEdge)
    influences += influence
    influence
  }

  def remove(influence: Influence) {
    influences -= influence
    influencesPool.free(influence)
    updateField(influence, -influence.valueCenter, -influence.valueEdge)
  }

  def updateField(influence: Influence, valueCenter: Float, valueEdge: Float) {
    for {
      x <- 0 until width
      y <- 0 until height
    } {
      val distance = influence.center.dst(x, y, 0f)
      if (distance <= influence.range) {
        val ratio = distance / influence.range
        influencesFiled(x)(y) += ((1 - ratio) * valueCenter + ratio * valueEdge).toInt
      }
    }
  }

  def getSum(x: Int, y: Int): Int = influencesFiled(x)(y)

  def update(deltaTime: Float) {
    currentTime += deltaTime
    influences.filter(_.endTime < currentTime).foreach(remove)
  }
}

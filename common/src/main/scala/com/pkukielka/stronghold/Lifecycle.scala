package com.pkukielka.stronghold

sealed abstract class LifecycleState
case object Active extends LifecycleState
case object Freed extends LifecycleState

trait Lifecycle {
  private var state: LifecycleState = Active
  var time = 0.0f

  protected def lifeTime: Float

  def activate() {
    time = 0.0f
    state = Active
  }

  def deactivate() {
    state = Freed
  }

  def resetTime() {
    time = 0f
  }

  def advanceTime(t: Float): Boolean = {
    if (isActive) {
      time += t

      if (time > lifeTime) {
        deactivate()
      }
    }

    isActive
  }

  def isActive = (state == Active)
}

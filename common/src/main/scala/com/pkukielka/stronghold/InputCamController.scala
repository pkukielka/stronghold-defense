package com.pkukielka.stronghold

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector3

class InputCamController(val camera: OrthographicCamera) extends InputAdapter {
  val curr = new Vector3()
  val last = new Vector3(-1, -1, -1)
  val delta = new Vector3()

  override def touchDragged(x: Int, y: Int, pointer: Int): Boolean = {
    if (pointer == 0) {
      if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
        camera.unproject(curr.set(x, y, 0))
        camera.unproject(delta.set(last))
        camera.position.add(delta.sub(curr))
      }
      last.set(x, y, 0)
    }
    false
  }

  override def touchUp(x: Int, y: Int, pointer: Int, button: Int): Boolean = {
    last.set(-1, -1, -1)
    false
  }

  override def scrolled(amount: Int): Boolean = {
    val newZoom = camera.zoom + amount / 10f
    if (newZoom >= 0.5 && newZoom <= 1) {
      camera.zoom = newZoom
    }
    false
  }
}

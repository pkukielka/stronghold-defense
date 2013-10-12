package com.pkukielka.stronghold

import com.badlogic.gdx.input.GestureDetector.GestureAdapter
import com.badlogic.gdx.graphics.OrthographicCamera

class GestureCamController(val camera: OrthographicCamera, val onTap: (Float, Float) => Unit)
  extends GestureAdapter {
  var initialScale = 1f
  val utils = new IsometricMapUtils(camera)

  override def touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean = {
    initialScale = camera.zoom
    false
  }

  override def tap(x: Float, y: Float, count: Int, button: Int): Boolean = {
    val tilePosition = utils.screenToCameraCoordinates(x, y)
    onTap(tilePosition.x, tilePosition.y)
    false
  }

  override def zoom(initialDistance: Float, distance: Float): Boolean = {
    val newZoom = initialScale * (initialDistance / distance)
    if (newZoom >= 0.5 && newZoom <= 2) {
      camera.zoom = newZoom
    }
    false
  }
}
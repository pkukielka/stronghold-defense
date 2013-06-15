package com.pkukielka.stronghold

import com.badlogic.gdx.input.GestureDetector.GestureAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.{Matrix4, Vector3}

class GestureCamController(val camera: OrthographicCamera, val map: TiledMap, val onTap: (Int, Int) => Unit)
  extends GestureAdapter {
  var initialScale = 1f
  val transformTranslate = new Matrix4().translate(0.0f, -0.22f, 0.0f)
  val transformScale = new Matrix4().scale(Math.sqrt(2.0).toFloat, (2.0f * Math.sqrt(2.0)).toFloat, 1.0f)
  val transformRotate = new Matrix4().rotate(0.0f, 0.0f, 1.0f, 45.0f)

  override def touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean = {
    initialScale = camera.zoom
    false
  }

  override def tap(x: Float, y: Float, count: Int, button: Int): Boolean = {
    val clickPoint: Vector3 = new Vector3(x, y, 0)
    camera.unproject(clickPoint)
    clickPoint.mul(transformTranslate).mul(transformScale).mul(transformRotate)
    onTap(clickPoint.x.toInt, clickPoint.y.toInt)

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
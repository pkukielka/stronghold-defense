package com.pkukielka.stronghold

import com.badlogic.gdx.math.{Vector3, Vector2, Matrix3}
import com.badlogic.gdx.graphics.OrthographicCamera

class IsometricMapUtils(val camera: OrthographicCamera) {
  val screenToTileTranslate = new Matrix3().translate(0.0f, -0.22f)
  val screenToTileScale = new Matrix3().scale(Math.sqrt(2.0).toFloat, (2.0f * Math.sqrt(2.0)).toFloat)
  val screenToTileRotate = new Matrix3().rotate(45.0f)

  val mapToScreenTranslate = new Matrix3().translate(0.0f, 0.22f)
  val mapToScreenScale = new Matrix3().scale(1.0f / Math.sqrt(2.0).toFloat, 1.0f / (2.0f * Math.sqrt(2.0)).toFloat)
  val mapToScreenRotate = new Matrix3().rotate(-45.0f)

  val mutablePosition =  new Vector2()
  val mutablePosition3 =  new Vector3()

  def cameraToMapCoordinates(cameraX: Float, cameraY: Float): Vector2 = {
    mutablePosition.set(cameraX, cameraY)
    mutablePosition.mul(screenToTileTranslate).mul(screenToTileScale).mul(screenToTileRotate)
  }

  def screenToCameraCoordinates(screenX: Float, screenY: Float): Vector2 = {
    mutablePosition3.set(screenX, screenY, 0f)
    camera.unproject(mutablePosition3)
    mutablePosition.set(mutablePosition3.x, mutablePosition3.y)
  }

  def mapToCameraCoordinates(mapX: Float, mapY: Float): Vector2 = {
    mutablePosition.set(mapX, mapY)
    mutablePosition.mul(mapToScreenRotate).mul(mapToScreenScale).mul(mapToScreenTranslate)
  }
}

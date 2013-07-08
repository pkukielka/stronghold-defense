package com.pkukielka.stronghold

import com.badlogic.gdx.math.{Vector3, Matrix4}
import com.badlogic.gdx.graphics.OrthographicCamera

class IsometricMapUtils(val camera: OrthographicCamera) {
  val screenToTileTranslate = new Matrix4().translate(0.0f, -0.22f, 0.0f)
  val screenToTileScale = new Matrix4().scale(Math.sqrt(2.0).toFloat, (2.0f * Math.sqrt(2.0)).toFloat, 1.0f)
  val screenToTileRotate = new Matrix4().rotate(0.0f, 0.0f, 1.0f, 45.0f)

  val tileToScreenTranslate = new Matrix4().translate(0.0f, 0.22f, 0.0f)
  val tileToScreenScale = new Matrix4().scale(1.0f / Math.sqrt(2.0).toFloat, 1.0f / (2.0f * Math.sqrt(2.0)).toFloat, 1.0f)
  val tileToScreenRotate = new Matrix4().rotate(0.0f, 0.0f, 1.0f, -45.0f)

  val mutablePosition =  new Vector3()

  def getTilePosition(screenX: Float, screenY: Float): Vector3 = {
    mutablePosition.set(screenX, screenY, 0)
    camera.unproject(mutablePosition)
    mutablePosition
//    mutablePosition.mul(screenToTileTranslate).mul(screenToTileScale).mul(screenToTileRotate)
  }

  def getScreenCoordinates(tileX: Float, tileY: Float): Vector3 = {
    mutablePosition.set(tileX, tileY, 0)
    mutablePosition.mul(tileToScreenRotate).mul(tileToScreenScale).mul(tileToScreenTranslate)
  }
}

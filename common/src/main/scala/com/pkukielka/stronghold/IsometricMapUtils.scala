package com.pkukielka.stronghold

import com.badlogic.gdx.math.{Vector3, Vector2, Matrix3}
import com.badlogic.gdx.graphics.OrthographicCamera

object IsometricMapUtils {
  var unitScale: Float = _
  private var halfTileWidth: Float = _
  private var halfTileHeight: Float = _
  private var camera: OrthographicCamera = null
  private val mutablePosition3 = new Vector3()
  private val mutablePosition = new Vector2()

  def init(camera: OrthographicCamera, unitScale: Float, tileWidth: Float, tileHeight: Float) =
  {
    this.unitScale = unitScale
    this.halfTileWidth = tileWidth * 0.5f *  unitScale
    this.halfTileHeight = tileHeight * 0.5f * unitScale
    this.camera = camera
  }

  def mapToCameraX(mapPositionX: Float, mapPositionY: Float) =
    (mapPositionY * halfTileWidth) + (mapPositionX * halfTileWidth)

  def mapToCameraY(mapPositionX: Float, mapPositionY: Float) =
    (mapPositionY * halfTileHeight) - (mapPositionX * halfTileHeight)

  def mapToCameraX(mapPosition: Vector2): Float = mapToCameraX(mapPosition.x, mapPosition.y)

  def mapToCameraY(mapPosition: Vector2): Float = mapToCameraY(mapPosition.x, mapPosition.y)

  def cameraToMapX(cameraPositionX: Float, cameraPositionY: Float) =
    (cameraPositionX / (2 * halfTileWidth)) -  (cameraPositionY / (2 * halfTileHeight))

  def cameraToMapY(cameraPositionX: Float, cameraPositionY: Float) =
    (cameraPositionX / (2 * halfTileWidth)) +  (cameraPositionY / (2 * halfTileHeight))

  def cameraToMapX(cameraPosition: Vector2): Float =
    cameraToMapX(cameraPosition.x, cameraPosition.y)

  def cameraToMapY(cameraPosition: Vector2): Float =
    cameraToMapY(cameraPosition.x, cameraPosition.y)

  def screenToCameraCoordinates(screenX: Float, screenY: Float): Vector2 = {
    mutablePosition3.set(screenX, screenY, 0f)
    camera.unproject(mutablePosition3)
    mutablePosition.set(mutablePosition3.x, mutablePosition3.y)
  }
}


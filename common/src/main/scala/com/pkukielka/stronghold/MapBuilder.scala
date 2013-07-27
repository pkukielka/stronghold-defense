package com.pkukielka.stronghold

import com.badlogic.gdx.maps.tiled.{TiledMapTileLayer, TiledMap}
import com.badlogic.gdx.maps.MapProperties
import scala.collection.mutable.MutableList
import scala.collection.convert.WrapAsScala.asScalaIterator
import com.pkukielka.stronghold.enemy.PathFinder

case class HeightMap(downLeft: Int, topLeft: Int, topRight: Int, downRight: Int) {
  def isGroundLevel: Boolean =
    downLeft == 0 && topLeft == 0 && topRight == 0 && downRight == 0

  def +(that: HeightMap) =
    new HeightMap(downLeft + that.downLeft, topLeft + that.topLeft, topRight + that.topRight, downRight + that.downRight)

  def isMatchingRight(hm: HeightMap) = topRight == hm.topLeft && downRight == hm.downLeft

  def isMatchingTop(hm: HeightMap) = topRight == hm.downRight && topLeft == hm.downLeft

  def isMatchingDiagonal(hm: HeightMap) = topRight == hm.downLeft
}

class MapBuilder(map: TiledMap) {
  import PathFinder._

  val height = map.getProperties.get("height").asInstanceOf[Int]
  val width = map.getProperties.get("width").asInstanceOf[Int]
  val nodesCount = height * width

  val heights = Array.ofDim[HeightMap](width, height)
  val edges = Array.fill[MutableList[(Node)]](nodesCount)(MutableList.empty[Node])
  val weights = Array.fill[Distance](nodesCount, nodesCount)(inf)

  {
    for (x <- 0 to (width - 1)) {
      for (y <- 0 to (height - 1)) {
        updateNodeHeightMap(x, y)
      }
    }

    for (x <- 0 until (width - 1)) {
      for (y <- 0 until (height - 1)) {
        addEdgesForNode(x, y)
      }
    }
  }

  private def getProperty(properties: MapProperties, key: String) = properties.get(key).asInstanceOf[String]

  private def isPropertySet(properties: MapProperties, key: String) = getProperty(properties, key) match {
    case null => false
    case prop => prop == "true"
  }

  private def updateNodeHeightMap(x: Int, y: Int) {
    val cellsColumnProperties = map.getLayers.iterator().toList
      .map(_.asInstanceOf[TiledMapTileLayer].getCell(x, y))
      .filterNot(_ == null)
      .map(_.getTile.getProperties)
      .filterNot(isPropertySet(_, "isIgnored"))

    if (cellsColumnProperties.lastOption.exists(isPropertySet(_, "isEnterable"))) {
      heights(x)(y) = cellsColumnProperties
        .map(getProperty(_, "heightMap"))
        .filterNot(_ == null)
        .map(_.toCharArray.map(_.toString).map(Integer.parseInt(_)))
        .map(h => HeightMap(h(0), h(1), h(2), h(3)))
        .fold(HeightMap(0, 0, 0, 0))(_ + _)
    }
  }

  private def addEdgesForNode(x: Int, y: Int) {
    def setEdge(x1: Int, y1: Int)(x2: Int, y2: Int)(value: Int) = {
      val n1 = y1 * width + x1
      val n2 = y2 * width + x2
      edges(n1) += n2
      edges(n2) += n1
      weights(n1)(n2) = value
      weights(n2)(n1) = value
    }

    val baseNode = heights(x)(y)

    val rightNode = heights(x + 1)(y)
    val isMatchingRight = baseNode != null && rightNode != null && baseNode.isMatchingRight(rightNode)
    if (isMatchingRight) setEdge(x, y)(x + 1, y)(100)

    val upperNode = heights(x)(y + 1)
    val isMatchingTop = baseNode != null && upperNode != null && baseNode.isMatchingTop(upperNode)
    if (isMatchingTop) setEdge(x, y)(x, y + 1)(100)

    val diagonalNode = heights(x + 1)(y + 1)
    if (isMatchingRight && isMatchingTop && diagonalNode != null && baseNode.isMatchingDiagonal(diagonalNode)) {
      setEdge(x, y)(x + 1, y + 1)(144)
      setEdge(x, y + 1)(x + 1, y)(144)
    }
  }
}

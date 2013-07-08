package com.pkukielka.stronghold

import com.badlogic.gdx.math.Vector3
import scala.Array
import scala.annotation.tailrec
import com.badlogic.gdx.Gdx
import java.nio.{ByteOrder, ByteBuffer}
import com.badlogic.gdx.Application.ApplicationType
import com.badlogic.gdx.maps.tiled.{TiledMapTileLayer, TiledMap}
import scala.collection.mutable.MutableList
import scala.collection.convert.WrapAsScala.asScalaIterator
import com.badlogic.gdx.maps.MapProperties

object PathFinder {
  type Node = Int
  type Distance = Int
}

case class HeightMap(downLeft: Int, topLeft: Int, topRight: Int, downRight: Int) {
  def isGroundLevel: Boolean =
    downLeft == 0 && topLeft == 0 && topRight == 0 && downRight == 0

  def +(that:HeightMap) =
    new HeightMap(downLeft + that.downLeft, topLeft + that.topLeft, topRight + that.topRight, downRight + that.downRight)

  def isMatchingRight(hm: HeightMap) = topRight == hm.topLeft && downRight == hm.downLeft

  def isMatchingTop(hm: HeightMap) = topRight == hm.downRight && topLeft == hm.downLeft

  def isMatchingDiagonal(hm: HeightMap) = topRight == hm.downLeft
}

class PathFinder(map: TiledMap, mapName: String) {
  import PathFinder._
  private val inf = Int.MaxValue
  private val height = map.getProperties.get("height").asInstanceOf[Int]
  private val width = map.getProperties.get("width").asInstanceOf[Int]
  private val nodesCount = height * width

  private val heights = Array.ofDim[HeightMap](width, height)
  private val edges = Array.fill[MutableList[(Node, Distance)]](nodesCount)(MutableList.empty[(Node, Distance)])

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

  private val distances = {
    if (Gdx.app.getType == ApplicationType.Desktop) {
      val file = Gdx.files.local("data/maps/" + mapName + ".bin")
      val dist = findAllPairsShortestPath(nodesCount)
      val bytes = ByteBuffer.allocate(nodesCount * nodesCount * 4).order(ByteOrder.LITTLE_ENDIAN)
      dist.foreach(nodeDistances => nodeDistances.foreach(bytes.putInt(_)))
      file.writeBytes(bytes.array(), false)
      dist
    }
    else {
      val file = Gdx.files.internal("data/maps/" + mapName + ".bin")
      val in = ByteBuffer.wrap(file.readBytes())
      val arr = Array.ofDim[Int](nodesCount * nodesCount)
      in.order(ByteOrder.LITTLE_ENDIAN).asIntBuffer().get(arr)
      arr.grouped(nodesCount).toArray
    }
  }

  @tailrec
  final def getFreePosition(): Vector3 = {
    val pos = new Vector3((Math.random() * 29.0f).toInt, (Math.random() * 29.0f).toInt, 0.0f)
    if (hasConnections(pos) && heights(pos.x.toInt)(pos.y.toInt).isGroundLevel) return pos
    return getFreePosition
  }

  def hasConnections(position: Vector3): Boolean =
    hasConnections(pos2node(position))

  def hasConnections(node: Node): Boolean = !edges(node).isEmpty

  def getNextStep(fromNode: Node, toNode: Node): Node =
    edges(fromNode).minBy(edge => distances(edge._1)(toNode))._1

  def getNextStep(fromNode: Vector3, toNode: Vector3): Node =
    getNextStep(pos2node(fromNode), pos2node(toNode))

  def node2posX(node: Node): Int = node % width

  def node2posY(node: Node): Int = node / width

  def pos2node(position: Vector3): Node =
    position.y.toInt * width + position.x.toInt

  def getMinDistance(fromNode: Node, toNode: Node) =
    distances(fromNode)(toNode)

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
      edges(y1 * width + x1) += ((y2 * width + x2, value))
      edges(y2 * width + x2) += ((y1 * width + x1, value))
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

  private def findAllPairsShortestPath(nodesCount: Int): Array[Array[Distance]] = {
    val ds = Array.fill[Int](nodesCount, nodesCount)(inf)
    for (i <- 0 until nodesCount) ds(i)(i) = 0
    for (node <- 0 until edges.length)
      for (edge <- edges(node))
        ds(node)(edge._1) = edge._2

    val ns = Array.fill[Int](nodesCount, nodesCount)(-1)
    for {
      k <- 0 until nodesCount
      i <- 0 until nodesCount if ds(i)(k) != inf
      j <- 0 until nodesCount if ds(k)(j) != inf
    } {
      if (ds(i)(k) + ds(k)(j) < ds(i)(j)) {
        ds(i)(j) = ds(i)(k) + ds(k)(j)
        ns(i)(j) = k
      }
    }

    ds
  }

}

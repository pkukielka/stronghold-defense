package com.pkukielka.stronghold

import com.badlogic.gdx.maps.tiled.{TiledMapTileLayer, TiledMap}
import scalax.collection.mutable.Graph
import scalax.collection.edge.WUnDiEdge
import scala.language.implicitConversions
import scala.collection.convert.WrapAsScala.asScalaIterator
import com.badlogic.gdx.maps.MapProperties

class MapGraph(map: TiledMap) {
  var graph: Graph[Int, WUnDiEdge] = Graph.empty[Int, WUnDiEdge]
  val height = map.getProperties.get("height").asInstanceOf[Int]
  val width = map.getProperties.get("width").asInstanceOf[Int]
  val heightMap = Array.ofDim[Array[Int]](width, height)

  buildGraph(0, width - 1, 0, height - 1)

  def rebuildNode(x: Int, y: Int) {
    heightMap(x)(y) = null
    graph.remove(y * width + x)
    buildGraph(
      (x - 1).max(0),
      (x + 1).min(width - 1),
      (y - 1).max(0),
      (y + 1).min(height - 1)
    )
  }

  private def getProperty(properties: MapProperties, key: String) = properties.get(key).asInstanceOf[String]

  private def isPropertySet(properties: MapProperties, key: String) = getProperty(properties, key) match {
    case null => false
    case prop => prop == "true"
  }

  private def buildGraph(xStart: Int, xEnd: Int, yStart: Int, yEnd: Int) {
    for (x <- xStart to xEnd) {
      for (y <- yStart to yEnd) {
        updateNodeHeightMap(x, y)
      }
    }

    for (x <- xStart until xEnd) {
      for (y <- yStart until yEnd) {
        addEdgesForNode(x, y)
      }
    }
  }

  private def updateNodeHeightMap(x: Int, y: Int) {
    val cellsColumnProperties = map.getLayers.iterator().toList
      .map(_.asInstanceOf[TiledMapTileLayer].getCell(x, y))
      .filterNot(_ == null)
      .map(_.getTile.getProperties)
      .filterNot(isPropertySet(_, "isIgnored"))

    if (cellsColumnProperties.lastOption.exists(isPropertySet(_, "isEnterable"))) {
      heightMap(x)(y) = cellsColumnProperties
        .map(getProperty(_, "heightMap"))
        .filterNot(_ == null)
        .map(_.toCharArray.map(_.toString).map(Integer.parseInt(_)))
        .foldLeft(Array(0, 0, 0, 0))((acc, curr) => (acc, curr).zipped.map(_ + _))
    }
  }

  private def addEdgesForNode(x: Int, y: Int) {
    val baseNode = heightMap(x)(y)

    val upperNode = heightMap(x)(y + 1)
    if (baseNode != null && upperNode != null) {
      if (baseNode(1) == upperNode(0) && baseNode(2) == upperNode(3)) {
        graph.add(WUnDiEdge(y * width + x, (y + 1) * width + x)(10))
      }
    }

    val rightNode = heightMap(x + 1)(y)
    if (baseNode != null && rightNode != null) {
      if (baseNode(3) == rightNode(0) && baseNode(2) == rightNode(1)) {
        graph.add(WUnDiEdge(y * width + x, y * width + x + 1)(10))
      }
    }

    val diagonalNode = heightMap(x + 1)(y + 1)
    if (baseNode != null && diagonalNode != null && upperNode != null && rightNode != null) {
      if (baseNode(2) == diagonalNode(0) && baseNode(3) == rightNode(1) && baseNode(2) == upperNode(3)) {
        graph.add(WUnDiEdge(y * width + x, (y + 1) * width + x + 1)(14))
        graph.add(WUnDiEdge((y + 1) * width + x, y * width + x + 1)(14))
      }
    }
  }
}

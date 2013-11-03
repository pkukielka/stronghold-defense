package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.math.Vector2
import scala.Array
import scala.annotation.tailrec
import scala.collection.mutable
import com.pkukielka.stronghold.{InfluencesManager, MapBuilder}
import com.pkukielka.stronghold.MapBuilder._

class PathFinder(val destination: Node, val map: MapBuilder, val influencesManager: InfluencesManager) {

  private val distances = Array.fill[Distance](map.nodesCount)(inf)
  private val queue = mutable.PriorityQueue.empty[NodeWithPriority]
  val target = new Vector2()

  def update = findShortestPathsTo(destination)

  @tailrec
  final def getFreePosition: Vector2 = {
    val side = (scala.math.random * 4).toInt
    val x = (scala.math.random * 29.0f).toInt
    val y = (scala.math.random * 29.0f).toInt
    val maxX = map.width - 1
    val maxY = map.height - 1

    side match {
      case 0 => if (hasConnections(x, 0) && map.heights(x)(0).isGroundLevel) return new Vector2(x, 0)
      case 1 => if (hasConnections(x, maxY) && map.heights(x)(maxY).isGroundLevel) return new Vector2(x, maxY)
      case 2 => if (hasConnections(0, y) && map.heights(0)(y).isGroundLevel) return new Vector2(0, y)
      case 3 => if (hasConnections(maxX, y) && map.heights(maxX)(y).isGroundLevel) return new Vector2(maxX, y)
    }

    getFreePosition
  }

  def hasConnections(x: Int, y: Int): Boolean = !map.edges(map.getNode(x, y)).isEmpty

  def getInfluence(node: Node) = influencesManager.getSum(map.node2posX(node), map.node2posY(node))

  @tailrec
  private def findClosestNode(edges: List[Node], closestNode: Node): Node = {
    if (edges.isEmpty) {
      closestNode
    }
    else {
      findClosestNode(edges.tail, if (distances(edges.head) < distances(closestNode)) edges.head else closestNode)
    }
  }

  def getNextStep(currentPosition: Vector2): Node =
    findClosestNode(map.edges(map.getNode(currentPosition)).tail, map.edges(map.getNode(currentPosition)).head)

  def findShortestPathsTo(destination: Node) = {
    for (i <- distances.indices) distances(i) = inf
    distances(destination) = 0

    target.set(map.node2posX(destination), map.node2posY(destination))

    queue.enqueue(destination)

    while (!queue.isEmpty) {
      val currentNode = (queue.dequeue() & 0xffffffff).toInt

      for (neighbourNode <- map.edges(currentNode)) {
        val updatedNeighbourDistance =
          distances(currentNode) + map.weights(currentNode)(neighbourNode) + getInfluence(neighbourNode)
        if (updatedNeighbourDistance < distances(neighbourNode)) {
          distances(neighbourNode) = updatedNeighbourDistance
          queue.enqueue(((inf - updatedNeighbourDistance).toLong << 32) + neighbourNode)
        }
      }
    }

    distances
  }

}

package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.math.Vector2
import scala.Array
import scala.annotation.tailrec
import scala.collection.mutable
import com.pkukielka.stronghold.{InfluencesManager, MapBuilder}

object PathFinder {
  val inf: Distance = Int.MaxValue

  type Node = Int
  type NodeWithPriority = Long
  type Distance = Int
}

class PathFinder(map: MapBuilder, influencesManager: InfluencesManager) {

  import PathFinder._

  private val distances = Array.fill[Distance](map.nodesCount)(inf)
  private val queue = mutable.PriorityQueue.empty[NodeWithPriority]
  val target = new Vector2()

  @tailrec
  final def getFreePosition: Vector2 = {
    val side = (Math.random() * 4).toInt
    val x = (Math.random() * 29.0f).toInt
    val y = (Math.random() * 29.0f).toInt
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

  def hasConnections(x: Int, y: Int): Boolean = !map.edges(getNode(x, y)).isEmpty

  def getInfluence(node: Node) = influencesManager.getSum(node2posX(node), node2posY(node))

  def getNextStep(currentPosition: Vector2): Node = map.edges(getNode(currentPosition)).minBy(distances(_))

  def getNode(position: Vector2): Node = getNode(position.x.toInt, position.y.toInt)

  def getNode(x: Int, y: Int): Node = y * map.width + x

  def node2posX(node: Node): Int = node % map.width

  def node2posY(node: Node): Int = node / map.width

  def findShortestPathsTo(destination: Node) = {
    for (i <- distances.indices) distances(i) = inf
    distances(destination) = 0

    target.set(node2posX(destination), node2posY(destination))

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

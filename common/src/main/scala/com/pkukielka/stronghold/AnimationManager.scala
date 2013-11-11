package com.pkukielka.stronghold

import scala.Array
import scala.collection.mutable.ArrayBuffer
import com.pkukielka.stronghold.enemy._
import com.pkukielka.stronghold.enemy.units._
import com.pkukielka.stronghold.spell._
import com.pkukielka.stronghold.utils.{Poolable, Sorting}
import com.pkukielka.stronghold.spell.attacks.thunder._
import com.pkukielka.stronghold.enemy.features.flammable._
import com.pkukielka.stronghold.spell.attacks.wind._
import com.badlogic.gdx.graphics.{Texture, OrthographicCamera}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.utils.Pool
import com.badlogic.gdx.Gdx

class AnimationManager(batch: SpriteBatch, camera: OrthographicCamera, map: TiledMap, mapName: String) {
  private val menu = new Menu()
  private val fog = new Texture(Gdx.files.internal("data/textures/fog.png"))
  private val shapeRenderer = new ShapeRenderer()
  private val mapBuilder = new MapBuilder(map)
  private var delta = 0.0f
  private var loopCounter = 0

  private val sceneObjects = ArrayBuffer.empty[Lifecycle with Renderer]
  private val helperBuffer = ArrayBuffer.fill[Lifecycle with Renderer](1000)(null)

  private implicit val influencesManager = new InfluencesManager(mapBuilder.width, mapBuilder.height)
  private implicit val pathFinder = new PathFinder(mapBuilder.getNode(7, 29), mapBuilder, influencesManager)

  pathFinder.update

  class PoolWrapper[T <: Lifecycle with Renderer](create: => T) extends Pool[T] {
    def newObject(): T = {
      val obj = create
      obj.cleanup = () => free(obj)
      sceneObjects += obj
      obj
    }
  }

  object Pools {
    val whirlwindAttack = new PoolWrapper(new Whirlwind with WhirlwindRenderer with Poolable[Whirlwind with WhirlwindRenderer])
    val whirlwindSpell = new PoolWrapper(new WhirlwindSpell(pathFinder, whirlwindAttack.obtain()) with SpellRenderer with Poolable[WhirlwindSpell with SpellRenderer])
    val thunderAttack = new PoolWrapper(new Thunder with ThunderRenderer with Poolable[Thunder with ThunderRenderer])
    val thunderSpell = new PoolWrapper(new ThunderSpell(pathFinder, thunderAttack.obtain()) with SpellRenderer with Poolable[ThunderSpell with SpellRenderer])
  }

  trait DefaultExtensions extends FlammableCore with EnemyRenderer with FlammableRenderer {
    self: EnemyCore =>
  }

  private val enemies: Array[Enemy with EnemyRenderer] = ArrayBuffer.fill(10)(Array(
    new Skeleton with DefaultExtensions, new SkeletonArcher with DefaultExtensions,
    new SkeletonMage with DefaultExtensions, new SkeletonWeak with DefaultExtensions,
    new Goblin with DefaultExtensions, new EliteGoblin with DefaultExtensions,
    new EarthAnt with DefaultExtensions, new FireAnt with DefaultExtensions, new IceAnt with DefaultExtensions,
    new Minotaur with DefaultExtensions, new Stealth with DefaultExtensions, new Zombie with DefaultExtensions,
    new WyvernWater with DefaultExtensions, new WyvernFire with DefaultExtensions,
    new WyvernAir with DefaultExtensions, new WyvernEarth with DefaultExtensions
  )).flatten.toArray

  sceneObjects ++= enemies

  def hit(x: Float, y: Float) {
    val spell = menu.activeButton match {
      case menu.airButton => Pools.whirlwindSpell.obtain()
      case menu.lightButton => Pools.thunderSpell.obtain()
      case _ => return
    }

    spell.init(IsometricMapUtils.cameraToMapX(x, y), IsometricMapUtils.cameraToMapY(x, y))
  }

  private val drawLifeBar = (enemy: Enemy with EnemyRenderer) => enemy.drawLifeBar(shapeRenderer)

  private val isDeadEnemy = (obj: Lifecycle with Renderer) => obj match {
    case e: Enemy => e.isDead
    case _ => false
  }

  private val draw = (obj: Renderer) => obj.draw(batch, delta)

  private val update = (obj: Lifecycle with Renderer) => obj match {
    case enemy: Enemy => enemy.update(delta)
    case spell: Spell => spell.update(delta, enemies.asInstanceOf[Array[Enemy]])
    case attack: Attack => attack.update(delta, enemies.asInstanceOf[Array[Enemy]])
  }

  def update(deltaTime: Float) {
    delta = deltaTime
    loopCounter += 1

    if (loopCounter % 3 == 0)
    {
      val partitionActive = Sorting.partition[Lifecycle with Renderer](sceneObjects, 0, sceneObjects.length - 1, _.isActive)
      val partitionDeadEnemies = Sorting.partition[Lifecycle with Renderer](sceneObjects, 0, partitionActive - 1, isDeadEnemy)
      Sorting.stableSort[Enemy](sceneObjects.asInstanceOf[ArrayBuffer[Enemy]], 0, partitionDeadEnemies - 1, helperBuffer.asInstanceOf[ArrayBuffer[Enemy]], _.time)
      Sorting.stableSort[Lifecycle with Renderer](sceneObjects, partitionDeadEnemies, partitionActive - 1, helperBuffer, _.depth)
    }

    sceneObjects foreach update

    batch.begin()
    sceneObjects foreach draw
    batch.end()

    shapeRenderer.setProjectionMatrix(camera.combined)
    shapeRenderer.begin(ShapeType.Filled)
    enemies.foreach(drawLifeBar)
    shapeRenderer.end()

    // Hackish fog of war
    batch.begin()
    batch.draw(fog, -1, -7.5f, 32, 16)
    batch.end()

    menu.updateAndDraw(delta)
  }
}

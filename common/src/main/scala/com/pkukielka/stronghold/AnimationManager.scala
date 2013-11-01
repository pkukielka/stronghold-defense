package com.pkukielka.stronghold

import com.pkukielka.stronghold.enemy._
import com.pkukielka.stronghold.enemy.units._
import com.pkukielka.stronghold.spell._
import com.pkukielka.stronghold.utils.{Poolable, Sorting}
import scala.Array
import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.maps.tiled.TiledMap
import com.pkukielka.stronghold.enemy.features.flammable.{FlammableRenderer, FlammableCore}
import com.pkukielka.stronghold.spell.attacks.wind.{WhirlwindRenderer, Whirlwind, WhirlwindSpell}
import com.badlogic.gdx.utils.Pool

class AnimationManager(batch: SpriteBatch, camera: OrthographicCamera, map: TiledMap, mapName: String) {
  private val shapeRenderer = new ShapeRenderer()

  private val mapBuilder = new MapBuilder(map)
  private implicit val influencesManager = new InfluencesManager(mapBuilder.width, mapBuilder.height)
  private implicit val pathFinder = new PathFinder(mapBuilder.getNode(7, 29), mapBuilder, influencesManager)

  object Pools {
    val whirlwindAttack = new Pool[Whirlwind with WhirlwindRenderer] {
      val that = this
      def newObject() = new Whirlwind with WhirlwindRenderer
        with Poolable[Whirlwind with WhirlwindRenderer] {
          def free(instance: Whirlwind with WhirlwindRenderer): Unit = that.free(instance)
        }
    }
    val whirlwindSpell = new Pool[WhirlwindSpell with SpellRenderer] {
      val that = this
      def newObject() = new WhirlwindSpell(pathFinder, whirlwindAttack.obtain()) with SpellRenderer
        with Poolable[WhirlwindSpell with SpellRenderer] {
          def free(instance: WhirlwindSpell with SpellRenderer): Unit = that.free(instance)
        }
    }
  }

  val bullets = ArrayBuffer.empty[Attack]
  val glyphs = ArrayBuffer.empty[Spell with Renderer]
  var totalTime = 0.0f
  var delta = 0.0f

  pathFinder.update

  trait DefaultExtensions extends FlammableCore with EnemyRenderer with FlammableRenderer { self: EnemyCore => }

  private val enemies: Array[Enemy with Renderer] = ArrayBuffer.fill(10)(Array(
    new Skeleton with DefaultExtensions, new SkeletonArcher with DefaultExtensions,
    new SkeletonMage with DefaultExtensions, new SkeletonWeak with DefaultExtensions,
    new Goblin with DefaultExtensions, new EliteGoblin with DefaultExtensions,
    new EarthAnt with DefaultExtensions, new FireAnt with DefaultExtensions, new IceAnt with DefaultExtensions,
    new Minotaur with DefaultExtensions, new Stealth with DefaultExtensions, new Zombie with DefaultExtensions,
    new WyvernWater with DefaultExtensions, new WyvernFire with DefaultExtensions,
    new WyvernAir with DefaultExtensions, new WyvernEarth with DefaultExtensions
  )).flatten.toArray

  var renderers: ArrayBuffer[Renderer] = enemies.map(_.asInstanceOf[Renderer]).to[ArrayBuffer]

  def hit(x: Float, y: Float) {
    val glyph = Pools.whirlwindSpell.obtain()
    glyph.init(IsometricMapUtils.cameraToMapX(x, y), IsometricMapUtils.cameraToMapY(x, y))
    glyphs += glyph
    renderers += glyph
  }

  private val updateEnemy = (enemy: Enemy) => enemy.update(delta)
  private val updateAttacks = (attack: Attack) => attack.update(delta, enemies.asInstanceOf[Array[Enemy]], pathFinder)
  private val updateGlyphs = (glyph: Spell) => glyph.update(delta, enemies.asInstanceOf[Array[Enemy]], bullets)
  private val renderCompletedAttacks = (attack: Attack) => if (attack.isCompleted) attack.asInstanceOf[Renderer].draw(batch, delta)
  private val isDeadEnemy = (obj: Renderer) => obj.isInstanceOf[Enemy] && obj.asInstanceOf[Enemy].isDead
  private val renderDeadEnemies = (obj: Renderer) => if (isDeadEnemy(obj)) obj.draw(batch, delta)
  private val renderLivingEnemiesAndGlyphs = (obj: Renderer) => if (!isDeadEnemy(obj)) obj.draw(batch, delta)
  private val renderAttacksInProgress = (attack: Attack) => if (!attack.isCompleted) attack.asInstanceOf[Renderer].draw(batch, delta)
  private val renderLifeBar = (enemy: Enemy) => enemy.asInstanceOf[EnemyRenderer].drawLifeBar(shapeRenderer)

  def update(deltaTime: Float) {
    delta = deltaTime

    influencesManager.update(delta)

    Sorting.sort(renderers, 0, renderers.length)

    enemies.foreach(updateEnemy)
    bullets.foreach(updateAttacks)
    glyphs.foreach(updateGlyphs)

    batch.begin()
    bullets.foreach(renderCompletedAttacks)
    renderers.foreach(renderDeadEnemies)
    renderers.foreach(renderLivingEnemiesAndGlyphs)
    bullets.foreach(renderAttacksInProgress)
    batch.end()

    shapeRenderer.setProjectionMatrix(camera.combined)
    shapeRenderer.begin(ShapeType.Filled)
    enemies.foreach(renderLifeBar)
    shapeRenderer.end()
  }
}

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
  private val attacks = ArrayBuffer.empty[Attack with Renderer]
  private val spells = ArrayBuffer.empty[Spell with Renderer]
  private var delta = 0.0f

  private implicit val influencesManager = new InfluencesManager(mapBuilder.width, mapBuilder.height)
  private implicit val pathFinder = new PathFinder(mapBuilder.getNode(7, 29), mapBuilder, influencesManager)

  object Pools {
    val whirlwindAttack = new Pool[Whirlwind with WhirlwindRenderer] {
      def newObject() = new Whirlwind with WhirlwindRenderer with Poolable[Whirlwind with WhirlwindRenderer]
    }
    val whirlwindSpell =new Pool[WhirlwindSpell with SpellRenderer] {
      def newObject() = new WhirlwindSpell(pathFinder, whirlwindAttack.obtain()) with SpellRenderer with Poolable[WhirlwindSpell with SpellRenderer]
    }

    val thunderAttack = new Pool[Thunder with ThunderRenderer] {
      def newObject() = new Thunder with ThunderRenderer with Poolable[Thunder with ThunderRenderer]
    }
    val thunderSpell = new Pool[ThunderSpell with SpellRenderer] {
      def newObject() = new ThunderSpell(pathFinder, thunderAttack.obtain()) with SpellRenderer with Poolable[ThunderSpell with SpellRenderer]
    }
  }

  pathFinder.update

  trait DefaultExtensions extends FlammableCore with EnemyRenderer with FlammableRenderer {
    self: EnemyCore =>
  }

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
    val spell = menu.activeButton match {
      case menu.airButton => Pools.whirlwindSpell.obtain()
      case menu.lightButton => Pools.thunderSpell.obtain()
      case _ => return
    }

    spell.init(IsometricMapUtils.cameraToMapX(x, y), IsometricMapUtils.cameraToMapY(x, y))
    spells += spell
    renderers += spell
  }

  private val updateEnemy = (enemy: Enemy) => enemy.update(delta)
  private val updateAttacks = (attack: Attack) => attack.update(delta, enemies.asInstanceOf[Array[Enemy]])
  private val updateSpells = (spell: Spell) => spell.update(delta, enemies.asInstanceOf[Array[Enemy]], attacks.asInstanceOf[ArrayBuffer[Attack]])
  private val renderCompletedAttacks = (attack: Attack) => if (attack.isCompleted) attack.asInstanceOf[Renderer].draw(batch, delta)
  private val isDeadEnemy = (renderer: Renderer) => renderer.isInstanceOf[Enemy] && renderer.asInstanceOf[Enemy].isDead
  private val renderDeadEnemies = (renderer: Renderer) => if (isDeadEnemy(renderer)) renderer.draw(batch, delta)
  private val renderAttacksInProgress = (attack: Attack) => if (!attack.isCompleted) attack.asInstanceOf[Renderer].draw(batch, delta)
  private val renderLifeBar = (enemy: Enemy) => enemy.asInstanceOf[EnemyRenderer].drawLifeBar(shapeRenderer)
  private val renderLivingEnemiesAndSpells = (renderer: Renderer) => if (!isDeadEnemy(renderer)) renderer.draw(batch, delta)

  def update(deltaTime: Float) {
    delta = deltaTime

    influencesManager.update(delta)

    Sorting.sort(renderers, 0, renderers.length)

    enemies.foreach(updateEnemy)
    attacks.foreach(updateAttacks)
    spells.foreach(updateSpells)

    batch.begin()
    attacks.foreach(renderCompletedAttacks)
    renderers.foreach(renderDeadEnemies)
    renderers.foreach(renderLivingEnemiesAndSpells)
    attacks.foreach(renderAttacksInProgress)
    batch.end()

    shapeRenderer.setProjectionMatrix(camera.combined)
    shapeRenderer.begin(ShapeType.Filled)
    enemies.foreach(renderLifeBar)
    shapeRenderer.end()

    // Hackish fog of war
    batch.begin()
    batch.draw(fog, -1, -7.5f, 32, 16)
    batch.end()

    menu.updateAndDraw(delta)
  }
}

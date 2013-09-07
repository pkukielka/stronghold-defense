package com.pkukielka.stronghold.assets

import com.badlogic.gdx.maps.tiled.{TmxMapLoader, TiledMap}
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.assets.AssetManager

object Assets {
  val assetManager = new AssetManager()
  var isLoaded = false

  assetManager.setLoader(classOf[TiledMap], new TmxMapLoader(new InternalFileHandleResolver()))

  val antEarth = new Assets("ant_earth", 0.10f)
  val antFire = new Assets("ant_fire", 0.05f)
  val antIce = new Assets("ant_ice", 0.07f)
  val goblin = new Assets("goblin", 0.07f)
  val goblinElite = new Assets("goblin_elite", 0.06f)
  val minotaur = new Assets("minotaur", 0.10f)
  val skeleton = new Assets("skeleton", 0.10f)
  val skeletonArcher = new Assets("skeleton_archer", 0.08f)
  val skeletonMage = new Assets("skeleton_mage", 0.10f)
  val skeletonWeak = new Assets("skeleton_weak", 0.10f)
  val stealth = new Assets("stealth", 0.09f)
  val wyvernAir = new Assets("wyvern_air", 0.10f)
  val wyvernEarth = new Assets("wyvern_earth", 0.10f)
  val wyvernFire = new Assets("wyvern_fire", 0.10f)
  val wyvernWater = new Assets("wyvern_water", 0.10f)
  val zombie = new Assets("zombie", 0.10f)

  val enemies = Array(antEarth, antFire, antIce, goblin, goblinElite, minotaur, skeleton, skeletonArcher,
    skeletonMage, skeletonWeak, stealth,  wyvernAir, wyvernEarth, wyvernFire, wyvernWater, zombie)

  enemies.foreach(_.load(assetManager))

  def update() = {
    if (!isLoaded && assetManager.update()) {
      enemies.foreach(_.cache(assetManager))
      isLoaded = true
    }

    isLoaded
  }
}

class Assets(name: String, frameDuration: Float) {
  val baseType = name.split("_").head
  val gfx = new EnemyGfxAssets(name, frameDuration)
  val sound = new EnemySoundAssets(baseType)

  def load(assetManager: AssetManager) {
    gfx.load(assetManager)
    sound.load(assetManager)
  }

  def cache(assetManager: AssetManager) {
    gfx.cache(assetManager)
    sound.cache(assetManager)
  }
}
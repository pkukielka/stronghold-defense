package com.pkukielka.stronghold.assets

import com.badlogic.gdx.maps.tiled.{TmxMapLoader, TiledMap}
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.assets.loaders.{TextureAtlasLoader, MusicLoader, SoundLoader}
import com.badlogic.gdx.audio.{Music, Sound}
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.Gdx

object Assets {
  val assetManager = new AssetManager()
  var isLoaded = false

  assetManager.setLoader(classOf[TiledMap], new TmxMapLoader(new InternalFileHandleResolver()))
  assetManager.setLoader(classOf[TextureAtlas], new TextureAtlasLoader(new InternalFileHandleResolver()))
  assetManager.setLoader(classOf[Music], new MusicLoader(new InternalFileHandleResolver()))
  assetManager.setLoader(classOf[Sound], new SoundLoader(new InternalFileHandleResolver()))

  assetManager.load("data/textures/enemies/enemies_move.atlas", classOf[TextureAtlas])
  assetManager.load("data/textures/enemies/enemies_die.atlas", classOf[TextureAtlas])
  assetManager.load("data/maps/test.tmx", classOf[TiledMap])

  var antEarth, antFire, antIce, goblin, goblinElite, minotaur,
  skeleton, skeletonArcher, skeletonMage, skeletonWeak, stealth,
  wyvernAir, wyvernEarth, wyvernFire, wyvernWater, zombie = null.asInstanceOf[Assets]

  def update() = {
    if (!isLoaded && assetManager.update()) {
      antEarth = new Assets("ant_earth", 0.10f)
      antFire = new Assets("ant_fire", 0.05f)
      antIce = new Assets("ant_ice", 0.07f)
      goblin = new Assets("goblin", 0.07f)
      goblinElite = new Assets("goblin_elite", 0.06f)
      minotaur = new Assets("minotaur", 0.10f)
      skeleton = new Assets("skeleton", 0.10f)
      skeletonArcher = new Assets("skeleton_archer", 0.08f)
      skeletonMage = new Assets("skeleton_mage", 0.10f)
      skeletonWeak = new Assets("skeleton_weak", 0.10f)
      stealth = new Assets("stealth", 0.09f)
      wyvernAir = new Assets("wyvern_air", 0.10f)
      wyvernEarth = new Assets("wyvern_earth", 0.10f)
      wyvernFire = new Assets("wyvern_fire", 0.10f)
      wyvernWater = new Assets("wyvern_water", 0.10f)
      zombie = new Assets("zombie", 0.10f)

      isLoaded = true
    }

    isLoaded
  }

  def moveAtlas = assetManager.get("data/textures/enemies/enemies_move.atlas", classOf[TextureAtlas])

  def dieAtlas = assetManager.get("data/textures/enemies/enemies_die.atlas", classOf[TextureAtlas])

}

class Assets(name: String, frameDuration: Float) {
  val baseType = name.split("_").head
  val gfx = new EnemyGfxAssets(name, frameDuration)
  val sound = new EnemySoundAssets(baseType)
}
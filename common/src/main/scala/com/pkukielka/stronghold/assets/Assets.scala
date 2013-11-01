package com.pkukielka.stronghold.assets

import com.badlogic.gdx.maps.tiled.{TmxMapLoader, TiledMap}
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.assets.AssetManager

object Assets {
  val assetManager = new AssetManager()
  var isLoaded = false

  assetManager.setLoader(classOf[TiledMap], new TmxMapLoader(new InternalFileHandleResolver()))

  val antEarth = new EnemyAssets("ant_earth", 0.10f)
  val antFire = new EnemyAssets("ant_fire", 0.05f)
  val antIce = new EnemyAssets("ant_ice", 0.07f)
  val goblin = new EnemyAssets("goblin", 0.07f)
  val goblinElite = new EnemyAssets("goblin_elite", 0.06f)
  val minotaur = new EnemyAssets("minotaur", 0.10f)
  val skeleton = new EnemyAssets("skeleton", 0.10f)
  val skeletonArcher = new EnemyAssets("skeleton_archer", 0.08f)
  val skeletonMage = new EnemyAssets("skeleton_mage", 0.10f)
  val skeletonWeak = new EnemyAssets("skeleton_weak", 0.10f)
  val stealth = new EnemyAssets("stealth", 0.09f)
  val wyvernAir = new EnemyAssets("wyvern_air", 0.10f)
  val wyvernEarth = new EnemyAssets("wyvern_earth", 0.10f)
  val wyvernFire = new EnemyAssets("wyvern_fire", 0.10f)
  val wyvernWater = new EnemyAssets("wyvern_water", 0.10f)
  val zombie = new EnemyAssets("zombie", 0.10f)

  val enemies = Array(antEarth, antFire, antIce, goblin, goblinElite, minotaur, skeleton, skeletonArcher,
    skeletonMage, skeletonWeak, stealth, wyvernAir, wyvernEarth, wyvernFire, wyvernWater, zombie)

  enemies.foreach(_.load(assetManager))

  val spellDarkness1 = new EffectGfxAssets("darkness_001")
  val spellDarkness2 = new EffectGfxAssets("darkness_002")
  val spellEarth1 = new EffectGfxAssets("earth_001")
  val spellEarth2 = new EffectGfxAssets("earth_002")
  val spellFire1 = new EffectGfxAssets("fire_001")
  val spellFire2 = new EffectGfxAssets("fire_002")
  val spellFire3 = new EffectGfxAssets("fire_003")
  val spellHeal1 = new EffectGfxAssets("heal_001")
  val spellHeal2 = new EffectGfxAssets("heal_002")
  val spellIce1 = new EffectGfxAssets("ice_001")
  val spellIce2 = new EffectGfxAssets("ice_002")
  val spellLight1 = new EffectGfxAssets("light_001")
  val spellLight2 = new EffectGfxAssets("light_002")
  val spellLight3 = new EffectGfxAssets("light_003")
  val spellLight4 = new EffectGfxAssets("light_004")
  val spellSpecial1 = new EffectGfxAssets("special_001", 0.05f, 5, 25, 2)
  val spellSpecial2 = new EffectGfxAssets("special_002")
  val spellSpecial3 = new EffectGfxAssets("special_003")
  val spellSpecial4 = new EffectGfxAssets("special_004")
  val spellThunder1 = new EffectGfxAssets("thunder_001")
  val spellThunder2 = new EffectGfxAssets("thunder_002")
  val spellWater1 = new EffectGfxAssets("water_001")
  val spellWater2 = new EffectGfxAssets("water_002")
  val spellWater3 = new EffectGfxAssets("water_004")
  val spellWater4 = new EffectGfxAssets("water_005")
  val spellWind1 = new EffectGfxAssets("wind_001", 0.05f, 16, 22, 10)
  val spellWind2 = new EffectGfxAssets("wind_002", 0.05f, 14, 24, 5)
  val spellWind3 = new EffectGfxAssets("wind_003")
  val spellSlash1 = new EffectGfxAssets("slash_001")

  val spells = Array(
    spellDarkness1, spellDarkness2,
    spellEarth1, spellEarth2,
    spellFire1, spellFire2, spellFire3,
    spellHeal1, spellHeal2,
    spellIce1, spellIce2,
    spellLight1, spellLight2, spellLight3, spellLight4,
    spellSpecial1, spellSpecial2, spellSpecial3, spellSpecial4,
    spellThunder1, spellThunder2,
    spellWater1, spellWater2, spellWater3, spellWater4,
    spellWind1, spellWind2, spellWind3,
    spellSlash1
  )

  spells.foreach(_.load(assetManager))

  val glyphFire = new EffectGfxAssets("glyph_001")
  val glyphIce = new EffectGfxAssets("glyph_002")

  val glyphs = Array(glyphFire, glyphIce)

  glyphs.foreach(_.load(assetManager))

  def update() = {
    if (!isLoaded && assetManager.update()) {
      enemies.foreach(_.cache(assetManager))
      spells.foreach(_.cache(assetManager))
      glyphs.foreach(_.cache(assetManager))
      isLoaded = true
    }

    isLoaded
  }
}

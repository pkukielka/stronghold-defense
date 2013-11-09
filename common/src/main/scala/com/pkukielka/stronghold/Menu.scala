package com.pkukielka.stronghold

import com.badlogic.gdx.scenes.scene2d.{Actor, Stage}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent
import com.badlogic.gdx.scenes.scene2d.ui.{TextButton, Table}
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite}

class Menu {
  val click = Gdx.audio.newSound(Gdx.files.internal("data/sound/menu/click.ogg"))

  private val stage: Stage = new Stage()
  Gdx.input.setInputProcessor(stage)

  private val table = new Table()
  table.right().setFillParent(true)
  stage.addActor(table)

  private val spellsBar = new Table()
  spellsBar.setFillParent(false)
  spellsBar.setBackground(spriteDrawable("bar.png"))
  table.add(spellsBar)

  val airButton = addNewButton("air/whirlwind")
  val earthButton = addNewButton("earth/rock")
  val fireButton = addNewButton("fire/fire_arrows")
  val lightButton = addNewButton("light/lightning")
  val mindButton = addNewButton("mind/revive")

  var activeButton = airButton
  activeButton.toggle()

  val buttons = List(airButton, earthButton, fireButton, lightButton, mindButton)
  buttons.foreach { button=>
    button.addListener(new ChangeListener() {
      def changed(event: ChangeEvent, actor: Actor) {
        click.play()

        activeButton = button

        buttons.filter(_ != button).foreach { b =>
          if (b.isChecked) {
            b.setDisabled(true)
            b.toggle()
            b.setDisabled(false)
          }
        }

        if (!button.isChecked) {
          button.setDisabled(true)
          button.toggle()
          button.setDisabled(false)
        }
      }
    })
  }

  def addNewButton(spellName: String) = {
    val buttonStyle = new TextButtonStyle()
    buttonStyle.up = spriteDrawable("spells/" + spellName + "2_up.png")
    buttonStyle.down = buttonStyle.up
    buttonStyle.checked = spriteDrawable("spells/" + spellName + "3_down.png")
    buttonStyle.font = new BitmapFont()
    val button = new TextButton("", buttonStyle)
    spellsBar.row()
    spellsBar.add(button).size(64, 64).pad(8, 20, 8, 20)
    button
  }

  def spriteDrawable(name: String) = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("data/textures/menu/" + name))))

  def updateAndDraw(delta: Float) {
    stage.act(delta)
    stage.draw()
  }

  def resize (width: Int, height: Int) {
    stage.setViewport(width, height, true)
  }

  def dispose() {
    stage.dispose()
  }
}

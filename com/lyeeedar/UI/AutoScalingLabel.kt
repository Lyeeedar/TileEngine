package com.lyeeedar.UI


import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import com.lyeeedar.Util.AssetManager
import ktx.actors.alpha


class AutoScalingLabel(var text: String, val skin: Skin, style: String = "default") : Widget()
{
	val labelStyle = skin.get(style, AutoScalingLabelStyle::class.java)
	val fonts = Array<BitmapFont>()
	val layout = GlyphLayout()
	lateinit var chosenFont: BitmapFont

	init
	{
		val sizes = intArrayOf(2, 4, 6, 8, 12, 16, 20, 24, 30, 40)
		for (size in sizes)
		{
			fonts.add(AssetManager.loadFont(labelStyle.fontFile, size, labelStyle.colour, labelStyle.borderWidth, labelStyle.borderColour, labelStyle.shadow))
		}
	}

	override fun layout()
	{
		super.layout()

		for (font in fonts)
		{
			layout.setText(font, text, Color.WHITE, width, Align.center, false)
			val fontWidth = layout.width
			if (fontWidth > width)
			{
				break
			}

			chosenFont = font
		}
	}

	val tempCol = Color()
	override fun draw(batch: Batch, parentAlpha: Float)
	{
		tempCol.set(color)
		tempCol.mul(batch.color)
		tempCol.a = parentAlpha * alpha

		layout.setText(chosenFont, text, tempCol, width, Align.center, false)
		chosenFont.draw(batch, layout, x, y + layout.height * 0.5f)
	}

	class AutoScalingLabelStyle
	{
		lateinit var fontFile: String
		lateinit var colour: Color
		var borderWidth: Int = 1
		var borderColour: Color = Color.BLACK
		var shadow: Boolean = false
	}
}
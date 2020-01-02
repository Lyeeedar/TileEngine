package com.lyeeedar.Components

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import com.exp4j.Helpers.evaluate
import com.lyeeedar.Renderables.Renderable
import com.lyeeedar.Renderables.Sprite.Sprite
import com.lyeeedar.Util.AssetManager
import com.lyeeedar.Util.XmlData
import com.lyeeedar.Util.round
import ktx.collections.set

fun Entity.renderable(): RenderableComponent = renderableOrNull()!!
fun Entity.renderableOrNull(): RenderableComponent? = RenderableComponent.mapper.get(this)
fun Entity.renderOffset() = this.renderableOrNull()?.renderable?.animation?.renderOffset(false)
class RenderableComponent() : AbstractComponent()
{
	lateinit var renderable: Renderable
	var overrideSprite = false
	var lockRenderable = false

	fun set(renderable: Renderable): RenderableComponent
	{
		this.renderable = renderable
		return this
	}

	override fun parse(xml: XmlData, entity: Entity, parentPath: String)
	{
		var renderableEl = xml.getChildByName("Renderable")!!

		val variantsEl = xml.getChildByName("Variants")
		if (variantsEl != null)
		{
			for (el in variantsEl.children())
			{
				val chance = el.get("Chance")
				if (chance.evaluate().round() == 1)
				{
					renderableEl = el.getChildByName("Renderable")!!
					break
				}
			}
		}

		fun loadRenderable(): Renderable
		{
			val renderable = when (renderableEl.getAttribute("meta:RefKey"))
			{
				"Sprite" -> AssetManager.loadSprite(renderableEl)
				"TilingSprite" -> AssetManager.loadTilingSprite(renderableEl)
				"ParticleEffect" -> AssetManager.loadParticleEffect(renderableEl).getParticleEffect()
				else -> throw Exception("Unknown renderable type '" + renderableEl.getAttribute("meta:RefKey") + "'!")
			}

			val pos = entity.pos()
			if (pos != null)
			{
				renderable.size[0] = pos.size
				renderable.size[1] = pos.size
			}

			return renderable
		}

		if (xml.getBoolean("IsShared", false))
		{
			synchronized(EntityLoader.sharedRenderableMap)
			{
				val key = renderableEl.toString().hashCode()

				if (!EntityLoader.sharedRenderableMap.containsKey(key))
				{
					EntityLoader.sharedRenderableMap[key] = loadRenderable()
				}

				renderable = EntityLoader.sharedRenderableMap[key]
			}
		}
		else
		{
			renderable = loadRenderable()
		}
	}

	override fun saveData(kryo: Kryo, output: Output)
	{
		output.writeBoolean(overrideSprite)

		if (overrideSprite)
		{
			kryo.writeClassAndObject(output, renderable as Sprite)
		}
	}

	override fun loadData(kryo: Kryo, input: Input)
	{
		overrideSprite = input.readBoolean()

		if (overrideSprite)
		{
			renderable = kryo.readClassAndObject(input) as Sprite
		}
	}

	var obtained: Boolean = false
	companion object
	{
		val mapper: ComponentMapper<RenderableComponent> = ComponentMapper.getFor(RenderableComponent::class.java)
		fun get(entity: Entity): RenderableComponent? = mapper.get(entity)

		private val pool: Pool<RenderableComponent> = object : Pool<RenderableComponent>() {
			override fun newObject(): RenderableComponent
			{
				return RenderableComponent()
			}

		}

		@JvmStatic fun obtain(): RenderableComponent
		{
			val obj = RenderableComponent.pool.obtain()

			if (obj.obtained) throw RuntimeException()
			obj.reset()

			obj.obtained = true
			return obj
		}
	}
	override fun free() { if (obtained) { RenderableComponent.pool.free(this); obtained = false } }
	override fun reset()
	{
		overrideSprite = false
		lockRenderable = false
	}
}
package com.lyeeedar.Components

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import com.lyeeedar.Util.XmlData

fun Entity.name(): NameComponent = NameComponent.mapper.get(this)
class NameComponent() : AbstractComponent()
{
	lateinit var name: String
	lateinit var title: String

	fun set(name: String): NameComponent
	{
		this.name = name
		return this
	}

	override fun parse(xml: XmlData, entity: Entity, parentPath: String)
	{
		name = xml.get("Name", "")!!
		title = xml.get("Title", "")!!
	}

	var obtained: Boolean = false
	companion object
	{
		val mapper: ComponentMapper<NameComponent> = ComponentMapper.getFor(NameComponent::class.java)
		fun get(entity: Entity): NameComponent? = mapper.get(entity)

		private val pool: Pool<NameComponent> = object : Pool<NameComponent>() {
			override fun newObject(): NameComponent
			{
				return NameComponent()
			}

		}

		@JvmStatic fun obtain(): NameComponent
		{
			val obj = NameComponent.pool.obtain()

			if (obj.obtained) throw RuntimeException()
			obj.reset()

			obj.obtained = true
			return obj
		}
	}
	override fun free() { if (obtained) { NameComponent.pool.free(this); obtained = false } }
	override fun reset()
	{
		name = ""
		title = ""
	}
}
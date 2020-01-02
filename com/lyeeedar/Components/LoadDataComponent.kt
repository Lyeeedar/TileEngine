package com.lyeeedar.Components

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import com.lyeeedar.Util.XmlData

fun Entity.loadData(): LoadDataComponent? = LoadDataComponent.mapper.get(this)
class LoadDataComponent() : AbstractComponent()
{
	lateinit var path: String
	lateinit var xml: XmlData

	fun set(path: String, xml: XmlData, fromLoad: Boolean): LoadDataComponent
	{
		this.path = path
		this.xml = xml
		this.fromLoad = fromLoad

		return this
	}

	override fun parse(xml: XmlData, entity: Entity, parentPath: String)
	{

	}

	var obtained: Boolean = false
	companion object
	{
		val mapper: ComponentMapper<LoadDataComponent> = ComponentMapper.getFor(LoadDataComponent::class.java)
		fun get(entity: Entity): LoadDataComponent? = mapper.get(entity)

		private val pool: Pool<LoadDataComponent> = object : Pool<LoadDataComponent>() {
			override fun newObject(): LoadDataComponent
			{
				return LoadDataComponent()
			}

		}

		@JvmStatic fun obtain(): LoadDataComponent
		{
			val obj = LoadDataComponent.pool.obtain()

			if (obj.obtained) throw RuntimeException()

			obj.obtained = true
			return obj
		}
	}
	override fun free() { if (obtained) { LoadDataComponent.pool.free(this); obtained = false } }
}
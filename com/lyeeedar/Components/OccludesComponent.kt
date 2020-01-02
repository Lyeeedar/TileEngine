package com.lyeeedar.Components

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import com.lyeeedar.Util.XmlData

fun Entity.occludes(): OccludesComponent? = OccludesComponent.mapper.get(this)
class OccludesComponent : AbstractComponent()
{
	var occludes = true

	override fun parse(xml: XmlData, entity: Entity, parentPath: String)
	{
		occludes = xml.getBoolean("Occludes", true)
	}

	var obtained: Boolean = false
	companion object
	{
		val mapper: ComponentMapper<OccludesComponent> = ComponentMapper.getFor(OccludesComponent::class.java)
		fun get(entity: Entity): OccludesComponent? = mapper.get(entity)

		private val pool: Pool<OccludesComponent> = object : Pool<OccludesComponent>() {
			override fun newObject(): OccludesComponent
			{
				return OccludesComponent()
			}

		}

		@JvmStatic fun obtain(): OccludesComponent
		{
			val obj = OccludesComponent.pool.obtain()

			if (obj.obtained) throw RuntimeException()

			obj.obtained = true
			return obj
		}
	}
	override fun free() { if (obtained) { OccludesComponent.pool.free(this); obtained = false } }
}
package com.lyeeedar.Components

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import com.lyeeedar.Util.XmlData

fun Entity.transient(): TransientComponent? = TransientComponent.mapper.get(this)
fun Entity.isTransient() = this.transient() != null
class TransientComponent : AbstractComponent()
{
	override fun parse(xml: XmlData, entity: Entity, parentPath: String)
	{

	}

	var obtained: Boolean = false
	companion object
	{
		val mapper: ComponentMapper<TransientComponent> = ComponentMapper.getFor(TransientComponent::class.java)
		fun get(entity: Entity): TransientComponent? = mapper.get(entity)

		private val pool: Pool<TransientComponent> = object : Pool<TransientComponent>() {
			override fun newObject(): TransientComponent
			{
				return TransientComponent()
			}

		}

		@JvmStatic fun obtain(): TransientComponent
		{
			val obj = TransientComponent.pool.obtain()

			if (obj.obtained) throw RuntimeException()

			obj.obtained = true
			return obj
		}
	}
	override fun free() { if (obtained) { TransientComponent.pool.free(this); obtained = false } }
}
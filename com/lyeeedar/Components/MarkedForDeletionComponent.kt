package com.lyeeedar.Components

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import com.lyeeedar.Util.XmlData

fun Entity.markedForDeletion(): MarkedForDeletionComponent? = MarkedForDeletionComponent.mapper.get(this)
fun Entity.isMarkedForDeletion() = this.markedForDeletion() != null
class MarkedForDeletionComponent : AbstractComponent()
{
	var deletionEffectDelay: Float = 0f

	override fun parse(xml: XmlData, entity: Entity, parentPath: String)
	{

	}

	fun set(delay: Float): MarkedForDeletionComponent
	{
		deletionEffectDelay = delay
		return this
	}

	var obtained: Boolean = false
	companion object
	{
		val mapper: ComponentMapper<MarkedForDeletionComponent> = ComponentMapper.getFor(MarkedForDeletionComponent::class.java)
		fun get(entity: Entity): MarkedForDeletionComponent? = mapper.get(entity)

		private val pool: Pool<MarkedForDeletionComponent> = object : Pool<MarkedForDeletionComponent>() {
			override fun newObject(): MarkedForDeletionComponent
			{
				return MarkedForDeletionComponent()
			}

		}

		@JvmStatic fun obtain(): MarkedForDeletionComponent
		{
			val obj = MarkedForDeletionComponent.pool.obtain()

			if (obj.obtained) throw RuntimeException()

			obj.reset()

			obj.obtained = true
			return obj
		}
	}
	override fun free() { if (obtained) { MarkedForDeletionComponent.pool.free(this); obtained = false } }

	override fun reset()
	{
		deletionEffectDelay = 0f
	}
}
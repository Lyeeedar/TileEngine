package com.lyeeedar.Components

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pool

class EntityPool
{
	companion object
	{
		private val pool: Pool<Entity> = object : Pool<Entity>() {
			override fun newObject(): Entity
			{
				return Entity()
			}

		}

		@JvmStatic fun obtain(): Entity
		{
			val obj = pool.obtain()
			if (obj.components.size() != 0) throw RuntimeException()
			return obj
		}

		val toBeFreed = Array<Entity>()

		fun free(entity: Entity) = toBeFreed.add(entity)

		fun flushFreedEntities()
		{
			for (entity in toBeFreed)
			{
				for (component in entity.components)
				{
					if (component is AbstractComponent)
					{
						component.free()
					}
				}

				//entity.removeAll()
				//pool.free(entity)
			}
			toBeFreed.clear()
		}
	}
}
fun Entity.free() = EntityPool.free(this)

fun <T: AbstractComponent> Entity.hasComponent(c: Class<T>) = this.getComponent(c) != null
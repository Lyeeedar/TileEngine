package com.lyeeedar.Components

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.IntMap
import com.badlogic.gdx.utils.Pool
import ktx.collections.set

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

			if (obj.obtained) throw RuntimeException()
			obj.obtained = true

			return obj
		}

		@JvmStatic fun obtain(signature: Int): Entity?
		{
			val matchingBlock = cache[signature]
			if (matchingBlock != null && matchingBlock.size > 0)
			{
				val entity = matchingBlock.pop()

				for (type in ComponentType.Values)
				{
					entity.components[type]?.reset()
				}

				return entity
			}

			return null
		}

		private val cacheLengthFrames = 200
		private var cacheCounter = 0
		private val cache = IntMap<Array<Entity>>()

		private val toBeFreed = Array<Entity>(false, 16)

		@JvmStatic fun free(entity: Entity)
		{
			toBeFreed.add(entity)
		}

		private fun flushFreedEntity(entity: Entity)
		{
			for (type in ComponentType.Values)
			{
				val component = entity.components[type]
				component?.free()
			}
			entity.components.clear()
			entity.signature.clear()

			entity.free()
		}

		@JvmStatic fun flushFreedEntities()
		{
			cacheCounter++
			if (cacheCounter == cacheLengthFrames)
			{
				for (block in cache.values())
				{
					for (entity in block)
					{
						flushFreedEntity(entity)
					}
					block.clear()
				}
			}

			for (entity in toBeFreed)
			{
				for (type in ComponentType.Temporary)
				{
					val component = entity.removeComponent(type)
					component?.free()
				}

				if (entity.components.size > 3)
				{
					var block = cache[entity.signature.bitFlag]
					if (block == null)
					{
						block = Array(false, 16)
						cache[entity.signature.bitFlag] = block
					}
					block.add(entity)
				}
				else
				{
					flushFreedEntity(entity)
				}
			}
			toBeFreed.clear()
		}
	}
}
package com.lyeeedar.Components

import com.badlogic.gdx.utils.Array
import com.lyeeedar.Util.EnumBitflag

class EntityArchetypeBuilder
{
	this should handle pooling its own entities

	val requiredComponents = Array<ComponentType>()

	fun add(componentType: ComponentType): EntityArchetypeBuilder
	{
		requiredComponents.add(componentType)
		return this
	}

	fun build(): Entity
	{
		val bitflag = EnumBitflag<ComponentType>()
		for (type in requiredComponents)
		{
			bitflag.setBit(type)
		}

		var entity = EntityPool.obtain(bitflag.bitFlag)

		if (entity == null)
		{
			entity = EntityPool.obtain()

			for (type in requiredComponents)
			{
				entity.addComponent(ComponentPool.obtain(type))
			}
		}

		return entity
	}
}
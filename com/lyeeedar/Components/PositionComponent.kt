package com.lyeeedar.Components

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import com.lyeeedar.Direction
import com.lyeeedar.SpaceSlot
import com.lyeeedar.Util.Point
import com.lyeeedar.Util.XmlData

fun Entity.pos(): PositionComponent = posOrNull()!!
fun Entity.posOrNull(): PositionComponent? = PositionComponent.mapper.get(this)
class PositionComponent(): AbstractComponent()
{
	var position: Point = Point(-1, -1) // bottom left pos
		set(value)
		{
			if (value != field)
			{
				facing = Direction.getCardinalDirection(value.x - field.x, value.y - field.y)

				field = value
				max = value
				turnsOnTile = 0
			}
		}

	var min: Point
		set(value) { position = value }
		get() { return position }

	var offset: Vector2 = Vector2()

	var max: Point = Point(-1, -1)

	var size: Int = 1

	var slot: SpaceSlot = SpaceSlot.ENTITY

	var moveable = true

	var moveLocked = false

	var canFall = true

	var facing: Direction = Direction.SOUTH

	var turnsOnTile: Int = 0

	val x: Int
		get() = position.x
	val y: Int
		get() = position.y

	override fun parse(xml: XmlData, entity: Entity, parentPath: String)
	{
		val slotEl = xml.get("SpaceSlot", null)
		if (slotEl != null) slot = SpaceSlot.valueOf(slotEl.toUpperCase())

		canFall = xml.getBoolean("CanFall", true)
		moveable = xml.getBoolean("Moveable", true)

		size = xml.getInt("Size", 1)
		if (size != -1)
		{
			val renderable = entity.renderableOrNull()
			if (renderable != null)
			{
				renderable.renderable.size[0] = size
				renderable.renderable.size[1] = size
			}

			val directional = entity.directionalSprite()
			if (directional != null)
			{
				directional.directionalSprite.size = size
			}

			val additional = entity.additionalRenderable()
			if (additional != null)
			{
				for (r in additional.below.values())
				{
					r.size[0] = size
					r.size[1] = size
				}

				for (r in additional.above.values())
				{
					r.size[0] = size
					r.size[1] = size
				}
			}
		}
	}

	var obtained: Boolean = false
	companion object
	{
		private val pool: Pool<PositionComponent> = object : Pool<PositionComponent>() {
			override fun newObject(): PositionComponent
			{
				return PositionComponent()
			}

		}

		@JvmStatic fun obtain(): PositionComponent
		{
			val obj = PositionComponent.pool.obtain()

			if (obj.obtained) throw RuntimeException()
			obj.reset()

			obj.obtained = true
			return obj
		}

		val mapper: ComponentMapper<PositionComponent> = ComponentMapper.getFor(PositionComponent::class.java)
		fun get(entity: Entity): PositionComponent? = mapper.get(entity)
	}
	override fun free() { if (obtained) { PositionComponent.pool.free(this); obtained = false } }
	override fun reset()
	{
		offset.set(0f, 0f)
		turnsOnTile = 0
		moveLocked = false
		facing = Direction.SOUTH
		position = Point(-1, -1)
		slot = SpaceSlot.ENTITY
		size = 1
		max = Point(-1, -1)
		moveable = true
		canFall = true
	}
}
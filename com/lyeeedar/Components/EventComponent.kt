package com.lyeeedar.Components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.lyeeedar.Util.Event0Arg

fun Entity.event(): EventComponent? {
	var event = EventComponent.get(this)
	if (event == null)
	{
		event = EventComponent()
		this.add(event)
	}

	return event
}
class EventComponent : Component
{
	val onTurn = Event0Arg()

	companion object
	{
		val mapper: ComponentMapper<EventComponent> = ComponentMapper.getFor(EventComponent::class.java)
		fun get(entity: Entity): EventComponent? = mapper.get(entity)
	}
}

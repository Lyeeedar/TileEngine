package com.lyeeedar.Components

import com.lyeeedar.Util.XmlData

fun Entity.markedForDeletion(): MarkedForDeletionComponent? = this.components[ComponentType.MarkedForDeletion] as MarkedForDeletionComponent?
fun Entity.isMarkedForDeletion() = this.markedForDeletion() != null
class MarkedForDeletionComponent : AbstractComponent()
{
	override val type: ComponentType = ComponentType.MarkedForDeletion

	var deletionEffectDelay: Float = 0f
	var reason: String = ""

	override fun parse(xml: XmlData, entity: Entity, parentPath: String)
	{

	}

	fun set(delay: Float): MarkedForDeletionComponent
	{
		deletionEffectDelay = delay
		return this
	}

	override fun reset()
	{
		deletionEffectDelay = 0f
	}
}
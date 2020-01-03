package com.lyeeedar.Components

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.ObjectMap
import com.lyeeedar.Global
import com.lyeeedar.Renderables.Renderable
import com.lyeeedar.Util.directory
import com.lyeeedar.Util.getXml

class EntityLoader()
{
	companion object
	{
		val sharedRenderableMap = ObjectMap<Int, Renderable>()

		@JvmStatic fun load(path: String): Entity
		{
			val xml = getXml(path)

			val entity = if (xml.get("Extends", null) != null) load(xml.get("Extends")) else EntityPool.obtain()

			entity.add(LoadDataComponent.obtain().set(path, xml, true))

			val componentsEl = xml.getChildByName("Components") ?: return entity

			for (componentEl in componentsEl.children())
			{
				if (Global.resolveInstant)
				{
					if (
						componentEl.name.toUpperCase() == "ADDITIONALRENDERABLES" ||
						componentEl.name.toUpperCase() == "DIRECTIONALSPRITE" ||
						componentEl.name.toUpperCase() == "RENDERABLE")
					{
						continue
					}
				}

				val component: AbstractComponent = when(componentEl.name.toUpperCase())
				{
					"ADDITIONALRENDERABLES" -> AdditionalRenderableComponent.obtain()
					"DIRECTIONALSPRITE" -> DirectionalSpriteComponent.obtain()
					"METAREGION" -> MetaRegionComponent.obtain()
					"NAME" -> NameComponent.obtain()
					"OCCLUDES" -> OccludesComponent.obtain()
					"POSITION" -> PositionComponent.obtain()
					"RENDERABLE" -> RenderableComponent.obtain()

					else -> {
						val comp = loadGameComponents(componentEl.name.toUpperCase())
						comp ?: throw Exception("Unknown component '" + componentEl.name + "'")
					}
				}

				component.fromLoad = true
				component.parse(componentEl, entity, path.directory())
				entity.add(component)
			}

			if (NameComponent.mapper.get(entity) == null)
			{
				val name = NameComponent.obtain()
				name.set(path)
				name.fromLoad = true
				entity.add(name)
			}

			return entity
		}
	}
}
package com.lyeeedar.Util

import com.badlogic.gdx.utils.IntMap
import ktx.collections.set

class Localisation
{
	companion object
	{
		val localisedIds: IntMap<String> by lazy {
			val map = IntMap<String>()

			for (file in XmlData.enumeratePaths("Localisation", "Localisation"))
			{
				val fileName = file.filename(false)
				val language = file.directory().filename(false)
				val xml = getXml(file)

				for (el in xml.children)
				{
					val trueId = "$language@$fileName/${el.getAttribute("ID")}"
					map[trueId.hashCode()] = el.value.toString()
				}
			}

			map
		}

		fun getText(id: String, file: String): String
		{
			val trueId = "${Statics.language}@$file/$id"
			return localisedIds[trueId.hashCode()]
		}
	}
}
package com.lyeeedar.ResourceProcessors

import com.lyeeedar.Renderables.Particle.ParticleEffect
import com.lyeeedar.Renderables.Particle.ParticleEffectDescription
import com.lyeeedar.Util.XmlData

class XmlLoadTester
{
	companion object
	{
		fun test()
		{
			for (path in XmlData.getExistingPaths().toList())
			{
				try
				{
					val xml = XmlData.getXml(path)
					when (xml.name.toUpperCase())
					{
						"EFFECT" -> ParticleEffect.load(path.split("Particles/")[1], ParticleEffectDescription(""))
						else -> GameXmlLoadTester.testLoad(xml, path)
					}

					System.out.println("Test loaded '$path'")
				}
				catch (ex: Exception)
				{
					System.err.println("Failed to load '$path'")
					throw ex
				}
			}
		}
	}
}
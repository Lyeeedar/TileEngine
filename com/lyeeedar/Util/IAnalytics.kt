package com.lyeeedar.Util

interface IAnalytics
{
	fun appOpen()
	fun levelStart(levelName: Long)
	fun levelEnd(levelName: Long, success: String)
	fun selectContent(type: String, id: String)
	fun tutorialBegin()
	fun tutorialEnd()
}

class DummyAnalytics :  IAnalytics
{
	override fun appOpen()
	{

	}

	override fun levelStart(levelName: Long)
	{

	}

	override fun levelEnd(levelName: Long, success: String)
	{

	}

	override fun selectContent(type: String, id: String)
	{

	}

	override fun tutorialBegin()
	{

	}

	override fun tutorialEnd()
	{

	}
}
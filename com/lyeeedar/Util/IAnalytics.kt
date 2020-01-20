package com.lyeeedar.Util

interface IAnalytics<B: IBundle>
{
	fun appOpen()
	fun levelStart(levelName: Long)
	fun levelEnd(levelName: Long, success: String)
	fun selectContent(type: String, id: String)
	fun tutorialBegin()
	fun tutorialEnd()

	fun getParamBundle(): B
	fun customEvent(name: String, paramBundle: B)
}

interface IBundle
{
	fun setString(key: String, value: String)
	fun setInt(key: String, value: Int)
	fun setBool(key: String, value: Boolean)
}

class DummyAnalytics : IAnalytics<DummyBundle>
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

	override fun getParamBundle(): DummyBundle
	{
		return DummyBundle()
	}

	override fun customEvent(name: String, paramBundle: DummyBundle)
	{

	}
}

class DummyBundle : IBundle
{
	override fun setString(key: String, value: String)
	{

	}

	override fun setInt(key: String, value: Int)
	{

	}

	override fun setBool(key: String, value: Boolean)
	{

	}

}
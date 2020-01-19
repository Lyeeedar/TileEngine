package com.lyeeedar.Util

interface ICrashReporter
{
	fun setCustomKey(key: String, value: String)
	fun setCustomKey(key: String, value: Boolean)
	fun setCustomKey(key: String, value: Double)
	fun setCustomKey(key: String, value: Float)
	fun setCustomKey(key: String, value: Int)

	fun logDebug(message: String)
	fun logWarning(message: String)
	fun logError(message: String)
}

class DummyCrashReporter : ICrashReporter
{
	override fun setCustomKey(key: String, value: String)
	{

	}

	override fun setCustomKey(key: String, value: Boolean)
	{

	}

	override fun setCustomKey(key: String, value: Double)
	{

	}

	override fun setCustomKey(key: String, value: Float)
	{

	}

	override fun setCustomKey(key: String, value: Int)
	{

	}

	override fun logDebug(message: String)
	{

	}

	override fun logWarning(message: String)
	{

	}

	override fun logError(message: String)
	{

	}
}
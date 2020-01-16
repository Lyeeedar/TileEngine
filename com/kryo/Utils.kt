package com.kryo

import com.badlogic.gdx.Gdx
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import com.lyeeedar.Util.registerGameSerializers
import com.lyeeedar.Util.registerGdxSerialisers
import com.lyeeedar.Util.registerLyeeedarSerialisers

val kryo: Kryo by lazy { initKryo() }
fun initKryo(): Kryo
{
	val kryo = Kryo()
	kryo.isRegistrationRequired = false

	kryo.registerGdxSerialisers()
	kryo.registerLyeeedarSerialisers()
	kryo.registerGameSerializers()

	return kryo
}

fun serialize(obj: Any, path: String)
{
	val outputFile = Gdx.files.local(path)

	var output: Output? = null
	try
	{
		output = Output(outputFile.write(false))
	}
	catch (e: Exception)
	{
		e.printStackTrace()
		return
	}

	kryo.writeClassAndObject(output, obj)

	output.close()
}

fun serialize(obj: Any): ByteArray
{
	val output: Output
	try
	{
		output = Output(128, -1)
	}
	catch (e: Exception)
	{
		e.printStackTrace()
		throw e
	}

	kryo.writeClassAndObject(output, obj)

	output.close()

	return output.buffer
}

fun deserialize(byteArray: ByteArray): Any
{
	var input: Input? = null

	val data: Any
	try
	{
		input = Input(byteArray)
		data = kryo.readClassAndObject(input)
	}
	catch (e: Exception)
	{
		e.printStackTrace()
		throw e
	}
	finally
	{
		input?.close()
	}

	return data
}

fun deserialize(path: String): Any?
{
	var input: Input? = null

	var data: Any?
	try
	{
		input = Input(Gdx.files.local(path).read())
		data = kryo.readClassAndObject(input)
	}
	catch (e: Exception)
	{
		e.printStackTrace()
		data = null
	}
	finally
	{
		input?.close()
	}

	return data
}
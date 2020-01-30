package com.lyeeedar.Util

interface ImmutableBag<E> : Iterable<E>
{
	/**
	 * Returns the element at the specified position in Bag.
	 *
	 * @param index
	 * index of the element to return
	 *
	 * @return the element at the specified position in bag
	 */
	operator fun get(index: Int): E

	/**
	 * Returns the number of elements in this bag.
	 *
	 * @return the number of elements in this bag
	 */
	fun size(): Int

	/**
	 * Returns true if this bag contains no elements.
	 *
	 * @return true if this bag contains no elements
	 */
	val isEmpty: Boolean

	/**
	 * Check if bag contains this element.
	 *
	 * @param e
	 *
	 * @return true if the bag contains this element
	 */
	operator fun contains(e: E): Boolean
}
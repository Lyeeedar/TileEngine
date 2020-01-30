package com.lyeeedar.Components

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pool

fun <T: AbstractComponent> Entity.hasComponent(c: Class<T>) = this.getComponent(c) != null
package com.chenqiao.compiler.activity.entity

import com.sun.tools.javac.code.Symbol

open class Field(private val symbol: Symbol):Comparable<Field>{

    val name = symbol.qualifiedName.toString()

    open val prefix = "REQUIRED_"

    val isPrivate = symbol.isPrivate

    val isPrimitive = symbol.type.isPrimitive

    override fun toString(): String {
        return "$name:${symbol.type}"
    }

    override fun compareTo(other: Field): Int {
        return name.compareTo(other.name)
    }


}
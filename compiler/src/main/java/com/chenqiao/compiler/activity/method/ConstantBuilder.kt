package com.chenqiao.compiler.activity.method

import com.chenqiao.compiler.activity.ActivityClass
import com.chenqiao.compiler.utils.camelToUnderline
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

class ConstantBuilder (private val activityClass: ActivityClass){


    //public static final String REQUIRED_NAME = "name";
    fun build(typeBuilder: TypeSpec.Builder){
        activityClass.fields.forEach {field ->
            typeBuilder.addField(FieldSpec.builder(String::class.java, field.prefix + field.name.camelToUnderline().toUpperCase(), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL).initializer("\$S", field.name).build())
        }
    }

}
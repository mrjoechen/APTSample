package com.chenqiao.compiler.activity.method

import com.chenqiao.compiler.activity.ActivityClass
import com.chenqiao.compiler.activity.ActivityClassBuilder
import com.chenqiao.compiler.activity.entity.OptionalField
import com.squareup.javapoet.TypeSpec

/**
 * Created by chenqiao on 2019-12-21.
 * e-mail : mrjctech@gmail.com
 */

class StartMethodBuilder(private val activityClass: ActivityClass) {

    fun build(typeBuilder: TypeSpec.Builder) {
        val startMethod = StartMethod(activityClass, ActivityClassBuilder.METHOD_NAME)

        val groupedFields = activityClass.fields.groupBy { it is OptionalField }

        val requiredFields = groupedFields[false] ?: emptyList()
        val optionalFields = groupedFields[true] ?: emptyList()

        startMethod.addAllField(requiredFields)

        val startMethodNoOptional = startMethod.copy(ActivityClassBuilder.METHOD_NAME_NO_OPTIONAL)

        startMethod.addAllField(optionalFields)
        startMethod.build(typeBuilder)

        if (optionalFields.isNotEmpty()){
            startMethodNoOptional.build(typeBuilder)
        }

    }

}
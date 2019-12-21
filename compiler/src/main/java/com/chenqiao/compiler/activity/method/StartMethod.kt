package com.chenqiao.compiler.activity.method

import com.chenqiao.compiler.activity.ActivityClass
import com.chenqiao.compiler.activity.entity.Field
import com.chenqiao.compiler.prebuilt.ACTIVITY_BUILDER
import com.chenqiao.compiler.prebuilt.CONTEXT
import com.chenqiao.compiler.prebuilt.INTENT
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

/**
 * Created by chenqiao on 2019-12-21.
 * e-mail : mrjctech@gmail.com
 */
class StartMethod (private val activityClass: ActivityClass, private val name: String){

    private val fields = ArrayList<Field>()

    private var isStaticMethod = true

    fun staticMethod(stativMethod: Boolean): StartMethod{
        this.isStaticMethod = stativMethod
        return this
    }

    fun addAllField(fields: List<Field>){
        this.fields += fields
    }

    fun addField(field: Field){
        this.fields += field
    }

    fun copy(name: String) = StartMethod(activityClass, name).also { it.fields.addAll(fields) }

    fun build(typeBuilder: TypeSpec.Builder){

        val methodBuilder = MethodSpec.methodBuilder(name)
            .addModifiers(Modifier.PUBLIC)
            .returns(TypeName.VOID)
            .addParameter(CONTEXT.java, "context")

        methodBuilder.addStatement("\$T intent = new \$T(context, \$T.class)", INTENT.java, INTENT.java, activityClass.typeElement)

        fields.forEach {
            field -> methodBuilder.addParameter(field.asJavaTypeName(), field.name)
            //intent.putExtra("age", age)
            .addStatement("intent.putExtra(\$S, \$L)", field.name, field.name)
        }


        if (isStaticMethod){
            methodBuilder.addModifiers(Modifier.STATIC)
        }else{
            methodBuilder.addStatement("fillintent(intent)")
        }
        methodBuilder.addStatement("\$T.INSTANCE.startActivity(context, intent)", ACTIVITY_BUILDER.java)
        typeBuilder.addMethod(methodBuilder.build())


    }


}
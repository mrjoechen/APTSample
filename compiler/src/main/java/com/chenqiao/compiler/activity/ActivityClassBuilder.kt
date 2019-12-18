package com.chenqiao.compiler.activity

import com.chenqiao.compiler.activity.method.ConstantBuilder
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import javax.annotation.processing.Filer
import javax.lang.model.element.Modifier

class ActivityClassBuilder(private val activityClass: ActivityClass) {

    companion object{
        const val POSIX = "Builder"
    }

    fun build(filer: Filer){
        if (activityClass.isAbstract) return
        val typeBuilder = TypeSpec.classBuilder(activityClass.simpleName + POSIX)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)

        ConstantBuilder(activityClass).build(typeBuilder)
        writeJavaToFile(filer, typeBuilder.build())
    }

    private fun writeJavaToFile(filer: Filer, typeSpec: TypeSpec){

        try {
            JavaFile.builder(activityClass.packageName, typeSpec).build().writeTo(filer)

        }catch (e: Exception){
            e.printStackTrace()
        }
    }

}
package com.chenqiao.compiler.activity

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

    }

    private fun writeJavaToFile(filer: Filer, typeSpec: TypeSpec){

    }

}
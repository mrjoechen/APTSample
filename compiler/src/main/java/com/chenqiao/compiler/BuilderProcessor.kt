package com.chenqiao.compiler

import com.bennyhuo.aptutils.AptContext
import com.bennyhuo.aptutils.logger.Logger
import com.bennyhuo.aptutils.types.isSubTypeOf
import com.chenqiao.annotations.Builder
import com.chenqiao.annotations.Optional
import com.chenqiao.annotations.Required
import com.chenqiao.compiler.activity.ActivityClass
import com.chenqiao.compiler.activity.entity.Field
import com.chenqiao.compiler.activity.entity.OptionalField
import com.sun.tools.javac.code.Symbol
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind


class BuilderProcessor:AbstractProcessor(){

    private val supportAnnotations = setOf(Builder::class.java, Required::class.java, Optional::class.java)

    override fun getSupportedAnnotationTypes() = supportAnnotations.mapTo(HashSet<String>(), Class<*>::getCanonicalName)

    override fun getSupportedSourceVersion() = SourceVersion.RELEASE_7

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment
    ): Boolean {

        val activityClasses = HashMap<Element, ActivityClass>()

//        roundEnv.getElementsAnnotatedWith(Builder::class.java).forEach {
//            Logger.warn(it, "到此一游："+it.simpleName.toString())
//        }

        roundEnv.getElementsAnnotatedWith(Builder::class.java)
            .filter { it.kind.isClass }
            .forEach { element ->
                try {
                    if (element.asType().isSubTypeOf("android.app.Activity")) {
                        activityClasses[element] = ActivityClass(element as TypeElement)
                    } else {
                        Logger.error(element, "Unsupported type: %s", element.simpleName)
                    }
                } catch (e: Exception) {
                    Logger.logParsingError(element, Builder::class.java, e)
                }
            }

        roundEnv.getElementsAnnotatedWith(Required::class.java)
            .filter { it.kind == ElementKind.FIELD }
            .forEach { element ->
                (activityClasses[element.enclosingElement]
                    ?.fields?.add(Field(element as Symbol.VarSymbol))
                    ?: Logger.error(element, "Field " + element + " annotated as Required while " + element.enclosingElement + " not annotated."))
            }

        roundEnv.getElementsAnnotatedWith(Optional::class.java)
            .filter { it.kind == ElementKind.FIELD }
            .forEach { element ->
                (activityClasses[element.enclosingElement]
                    ?.fields?.add(OptionalField(element as Symbol.VarSymbol))
                    ?: Logger.error(element, "Field " + element + " annotated as Optional while " + element.enclosingElement + " not annotated."))
            }

        activityClasses.values.forEach {
            Logger.warn(it.toString())
            it.builder.build(AptContext.filer)

        }
        return true
    }

}
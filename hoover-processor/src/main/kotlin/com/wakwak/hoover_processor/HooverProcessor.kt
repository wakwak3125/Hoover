package com.wakwak.hoover_processor

import com.wakwak.hoover.CascadeDelete
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement


class HooverProcessor : AbstractProcessor() {

    override fun init(env: ProcessingEnvironment?) {
        super.init(env)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_8
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return hashSetOf(CascadeDelete::class.java.canonicalName)
    }

    override fun process(annotations: MutableSet<out TypeElement>?, env: RoundEnvironment?): Boolean {
        println("processing start")
        return true
    }
}

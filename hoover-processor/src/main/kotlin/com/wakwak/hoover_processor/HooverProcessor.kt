package com.wakwak.hoover_processor

import com.squareup.javapoet.*
import com.wakwak.hoover.CascadeDelete
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

class HooverProcessor : AbstractProcessor() {

    private val typeSpecBuilder by lazy {
        val tv = TypeVariableName.get("T", ClassName.get("io.realm", "RealmObject"))
        return@lazy TypeSpec.classBuilder("CascadeDeleteFunctions")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(
                        MethodSpec.methodBuilder("cascadeDelete")
                                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .returns(TypeName.VOID)
                                .addTypeVariable(tv)
                                .addParameter(
                                        ClassName.get("io.realm", "Realm"),
                                        "realm"
                                )
                                .addParameter(tv, "realmObject")
                                .build())
    }

    private val methods = mutableListOf<MethodSpec>()

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
        env ?: return true

        env.getElementsAnnotatedWith(CascadeDelete::class.java).forEach {
            it.enclosedElements.forEach {
                if (it.kind.isField) {
                    val methodSpec = MethodSpec.methodBuilder("${it.simpleName}_cascadeDelete")
                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                            .returns(TypeName.VOID)
                            .addStatement("System.out.println(\"Not implemented yet.\")")
                            .build()
                    typeSpecBuilder.addMethod(methodSpec).build()
                    methods.add(methodSpec)
                }
            }
        }

        JavaFile.builder("com.wakwak.hoover.generated", typeSpecBuilder.build()).build().writeTo(System.out)

        return true
    }
}

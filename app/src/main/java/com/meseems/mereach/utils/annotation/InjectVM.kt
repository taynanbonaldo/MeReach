package com.meseems.mereach.utils.annotation

import java.lang.annotation.Inherited

/**
 * Created by taynanbonaldo on 08/03/18
 */
@Inherited
@Target(AnnotationTarget.CLASS)
annotation class InjectVM(vararg val tag: String)
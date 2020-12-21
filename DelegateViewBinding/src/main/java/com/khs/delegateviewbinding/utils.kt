package com.khs.delegateviewbinding

import android.os.Looper
import android.util.Log
import android.view.View
import androidx.collection.ArrayMap
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method


/**
 * 메인스레드 호출 여부 검사.
 *
 * check(Looper.getMainLooper() == Looper.myLooper()), true일 경우 메인스레드에서 호출되고 있음.
 *
 * @author 권혁신
 * @version 1.0.0
 * @since 2020-12-21 오전 9:01
 **/
@PublishedApi
internal fun ensureMainThread() = check(Looper.getMainLooper() == Looper.myLooper()) {
    "Expected to be called on the main thread but was " + Thread.currentThread().name
}

private const val debug = false

internal inline fun log(crossinline message: () -> String) {
    if (debug) {
        Log.d("ViewBinding", message())
    }
}

internal object GetBindMethod {

    init {
        ensureMainThread()
    }

    private val methodSignature = View::class.java
    private val methodMap = ArrayMap<Class<out ViewBinding>, Method>()

    internal operator fun <T : ViewBinding> invoke(clazz: Class<T>): Method =
        methodMap.getOrPut(clazz) {
            clazz.getMethod(
                "bind",
                methodSignature
            )
        }.also {
            log{
                "methodMap.size: ${methodMap.size}"
            }
        }
}
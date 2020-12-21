package com.khs.delegateviewbinding

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

public class ActivityViewBindingDelegate<T: ViewBinding> private constructor(
    viewBindingBind:((View) -> T)? = null,
    viewBindingClazz:Class<T>? =null
):ReadOnlyProperty<Activity,T>{

    private var binding:T? =null
    private val bind = viewBindingBind?:{ view:View ->
        @Suppress("UNCHECKED_CAST")
        GetBindMethod(viewBindingClazz!!)(null,view) as T
    }

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        ensureMainThread()
        return binding?:bind(thisRef.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)).also {
            binding = it
        }
    }
}
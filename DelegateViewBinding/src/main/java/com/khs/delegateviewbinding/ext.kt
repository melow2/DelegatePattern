package com.khs.delegateviewbinding

import android.app.Activity
import androidx.annotation.MainThread
import androidx.viewbinding.ViewBinding

@MainThread
public inline fun<reified T:ViewBinding> Activity.viewBinding():ActivityViewBindingDelegate<T> = ActivityViewBindingDelegate<T>()
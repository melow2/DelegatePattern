package com.jeit.delegatepattern


/**
 * DelegatePattern Simple1
 *
 *
 * @author 권혁신
 * @version 1.0.0
 * @since 2020-12-17 오전 9:49
 **/

interface IWindow {
    fun getWidth(): Int
    fun getHeight(): Int
}

open class FirstOffset : IWindow {
    override fun getWidth() = 400
    override fun getHeight() = 200
}

open class SecondOffset : IWindow {
    override fun getWidth() = 1300
    override fun getHeight() = 2020
}

open class ThirdOffset : IWindow {
    override fun getWidth() = 13200
    override fun getHeight() = 20230
}

class Example1(private val mWindow: IWindow) : IWindow {
    override fun getWidth(): Int = mWindow.getWidth()
    override fun getHeight(): Int = mWindow.getHeight()
}

class Example2(private val mWindow:IWindow):IWindow by mWindow

package com.jeit.delegatepattern


/**
 * DelegatePattern Simple1
 *
 *
 * 디자인 패턴에서 Delegate Pattern은 어떤 기능을 자신이 처리하지 않고 다른 객체에 위임을 시켜 그 객체가 일을 처리하도록 하는 것입니다. Delegate Pattern을 설명할 때, 상속(Inheritance)과 구성(Composition)에 대해서 이야기를 합니다.
 * 상속은 클래스의 변수와 메소드를 모두 받기 때문에 재구현할 필요가 없어서 편리합니다. 하지만 올바르지 않은 상속은 많은 문제를 발생시킵니다. 그 문제점 중 하나는 객체의 유연성이 떨어진다는 것인데요.. 이런 해결방법으로 Composition(또는 Aggregation)관계로 구현하는 것을 권장하고 있습니다.
 * Composition(또는 Aggregation)은 상속이 아닌, 클래스 안에 객체를 소유하고 있는 관계를 말합니다.(흔히 has-a라는 관계라고 하고, 상속은 is-a라고 말합니다)
 * Delegate Pattern은 Composition을 이용하는 일반적인 패턴입니다. Composition 객체의 함수가 많아지면 형식적인 코드(boilerplate code)를 많이 작성해야 할 수 있는데요.
 * 코틀린은 by라는 키워드를 이용하여 적은 코드로 적용할 수 있도록 지원하고 있습니다.
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
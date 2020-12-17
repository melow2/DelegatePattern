# DelegatePattern.
* 디자인 패턴에서 Delegate Pattern은 어떤 기능을 자신이 처리하지 않고 다른 객체에 위임을 시켜 그 객체가 일을 처리하도록 하는 것.
* Delegate Pattern을 설명할 때, 상속(Inheritance)과 구성(Composition)에 대해서 이야기를 함.
* 상속은 클래스의 변수와 메소드를 모두 받기 때문에 재구현할 필요가 없어서 편리함. 
* 하지만 올바르지 않은 상속은 많은 문제를 발생시킴. 
* 그 문제점 중 하나는 객체의 유연성이 떨어진다는 것. 
* 이런 해결방법으로 Composition(또는 Aggregation)관계로 구현하는 것을 권장.
* Composition(또는 Aggregation)은 상속이 아닌, 클래스 안에 객체를 소유하고 있는 관계.(흔히 has-a라는 관계라고 하고, 상속은 is-a)
* Delegate Pattern은 Composition을 이용하는 일반적인 패턴. 
* Composition 객체의 함수가 많아지면 형식적인 코드(boilerplate code)를 많이 작성해야 함
* 코틀린은 by라는 키워드를 이용하여 적은 코드로 적용할 수 있도록 지원.

#
## Delegation for property.
```
import kotlin.reflect.KProperty

class Delegate {
    // for get() method, ref - a reference to the object from 
    // which property is read. prop - property
    operator fun getValue(ref: Any?, prop: KProperty<*>) = "textA"
    // for set() method, 'v' stores the assigned value
    operator fun setValue(ref: Any?, prop: KProperty<*>, v: String) {
        println("value = $v")
    }
}

object SampleBy {
    var s: String by Delegate() // delegation for property
    @JvmStatic fun main(args: Array<String>) {
        println(s)
        s = "textB"
    }
}
```
### Result.
```
textA
value = textB
```
#
## Delegation for class.
```
interface BaseInterface {
    val value: String
    fun f()
}

class ClassA: BaseInterface {
    override val value = "property from ClassA"
    override fun f() { println("fun from ClassA") }
}

// The ClassB can implement the BaseInterface by delegating all public 
// members from the ClassA.
class ClassB(classA: BaseInterface): BaseInterface by classA {}

object SampleBy {
    @JvmStatic fun main(args: Array<String>) {
        val classB = ClassB(ClassA())
        println(classB.value)
        classB.f()
    }
}
```
### Result.
```
property from ClassA
fun from ClassA
```
#
## Delegation for parameters.
```
// for val properties Map is used; for var MutableMap is used
class User(mapA: Map<String, Any?>, mapB: MutableMap<String, Any?>) {
    val name: String by mapA
    val age: Int by mapA
    var address: String by mapB
    var id: Long by mapB
}

object SampleBy {
    @JvmStatic fun main(args: Array<String>) {
        val user = User(mapOf("name" to "John", "age" to 30),
        mutableMapOf("address" to "city, street", "id" to 5000L))

        println("name: ${user.name}; age: ${user.age}; " +
        "address: ${user.address}; id: ${user.id}")
    }
}
```
### Result.
```
name: John; age: 30; address: city, street; id: 5000
```

#
# Reflection.
* 런타임 시 자신의 프로그램 구조를 조사할 수 있도록 허용하는 언어와 라이브러리 기능의 집합.
* 가장 일반적인 기능은 val myClass = MyClass::class 와 같이 런타임 참조를 클래스로 가져오는 것.

#
## Bound Class References. 
```
fun main(args: Array<String>) {
val myClass = MyClass()
    assert(myClass is MyClass) {"Bad myClass: ${myClass::class.qualifiedName}"}
}
class MyClass
```
* ::class 구문을 사용하여 특정 object의 클래스에 대한 참조를 가져올 수 있다.

#
## Function References. 1
```
fun main(args: Array<String>) {
    val nums = listOf(1,2,3,4,5)
    println(nums.filter(::isEven))
    val strs = listOf("altongmon","antongmon","tongtongmon","tistory","tastory")
    print(strs.filter(::isEven))
}

fun isEven(a : Int) = a % 2 == 0
fun isEven(str : String) = str == "altongmon" || str == "tistory"
```
### Result.
```
[2,4]
[altongmon,tistory]
```
#
## Function References. 2
```
fun main(args: Array<String>) {
    val evenLength = compose(::isEven, ::length)
    val strs = listOf("atongmon","antongmon","tongtmon","tistory","tastory")
    print(strs.filter(evenLength))
}

fun isEven(a : Int) = a % 2 == 0
fun length(str : String) = str.length
fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
    return { x -> f(g(x)) }
}

```
### Result.
```
[atongmon, tongtmon]
```

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
* 가장 일반적인 기능은 val myClass = MyClass::class 와 같이 런타임 참조를 클래스로 가져오는 것. (리턴값은 KClass), (자바의 클래스 참조 MyClass:class.java)
* 함수를 참조하는 방법은 예를 들어, fun isOdd(a:Int) = a%2!=0 에서 ::isOdd로 참조될 수 있으며, 이는 (Int) -> Boolean 이다. 
* val predicate:(Int)->Boolean = ::isOdd 로 참조 될 수도 있다. 


#
## Bound Class References. 1
```
fun main(args: Array<String>) {
val myClass = MyClass()
    assert(myClass is MyClass) {"Bad myClass: ${myClass::class.qualifiedName}"}
}
class MyClass
```
#
## Bound Class References. 2
```
class Foo
fun function(factory: () -> Foo) {
    val x: Foo = factory()
}


fun reperenceConstuctorTest(){
    function(::Foo)
}
```
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
#
## Function References. 3
```
val isEmptyStringList:List<String>.() ->Boolean = List<String>::isEmpty
val emptyList = emptyList<String>()
val temp = isEmptyStringList(emptyList)

1) InputType을 강제로 형변환(List<String>)시킬 수 있다. 
```
#
## Property References. 1
```
val x = 1

fun getPropertyTest(){
    println(::x.get())  // 1
    println(::x.name()) // x 
}

1) ::x의 반환값은 Kproperty
2) val은 immutable이므로 Kproperty 이며, get()만 가능하다.
3) 이 객체를 사용하면 속성이름을 사용하여 Kproperty<Int> 값을 읽거나(get()), 속성이름(name())을 검색할 수 있다. 
```

#
## Property References. 2
```
var y = 1
    @Test
    fun setPropertyTest() {
        ::y.set(2)
        println(y)
    }
}
1) 변경 가능한 속성에 대해 예를 들어 var y = 1, 
2) ::y의 반환값은 KMutableProperty이다.
3) get(), set() 모두 가능하다.

@Test
    fun lengthTest(){
        val strs = listOf("a", "bc", "def")
        println(strs.map(String::length)
        )
    }
    //[1, 2, 3]
```

#
# inline fun <refied T> genericFunc()
* 일반적인 제네릭 함수 body에서 타입 T는 컴파일 타임에 존재하지만, 런타임에는 Type Erasure 때문에 접근할 수 없다.
* 따라서 일반적인 클래스에 작성된 함수 body에서 제네릭 타입에 런타임에서 접근하려면, fun <T> genericFunc(c:Class<T>)와 같이 명시적으로 타입을 파라미터로 전달 해주어야한다.
* 하지만 reified 와 함께 inline 함수를 추가적으로 만들면 Class<T>를 파라미터로 넘겨 줄 필요 없이 런타임에 타입 T에 접근할 수 있다. 
* 따라서 myVar is T 처럼 변수가 T의 인스턴스인지 쉽게 검사할 수 있다.
* refied는 inline function과 조합해서만 사용할 수 있다. 이런 함수는 컴파일러가 함수의 바이트코드를 함수가 사용되는 모든 곳에 복사하도록 만든다.
* refied타입과 함께 인라인 함수가 호출되면 컴파일러는 argument(T)로 사용된 실제 타입을 알고 만들어진 바이트코드를 직접 클래스에 대응할 수 있도록 바꿔준다.
* 그래서 myVar is T는 런타임과 바이트코드에서 myVar is String이 될 수 있다.
#
## 1. refied 없이 접근하기.
```
// 컴파일 되지 않음
fun <T> String.toKotlinObject(): T {
  val mapper = jacksonObjectMapper()
  return mapper.readValue(this, T::class.java)
}
```
* readValue 메소드는 JsonObject를 파싱하는데 사용하기 위해 타입 하나를 받는다.
* 타입 파라미터 T의 Class를 얻으려고 하면 컴파일 에러가 발생한다. 
* "Cannot use 'T' as reified type parameter. User a class instead."
#
## 2. 명시적으로 Class 파라미터를 전달하기.
```
fun <T : Any> String.toKotlinObject(c:KClass<T>):T{
    val mapper = jacksonObjetMapper()
    return mapper.readValue(this,c.java)
}
```
* 메소드 파라미터로 전달된 T의 Class는 readValue의 argument로 사용된다.
* 일반적인 제네릭 자바 코드와 같은 형태이고 아래와 같이 사용할 수 있다.
```
data class Foo(val name: String)

val json = """{"name":"example"}"""
json.toKotlinObject(Foo::class)
```
#
## 3. refied 사용하기.
```
inline fun <reified T: Any> String.toKotlinObject(): T{
    val mapper = jacksonObjectMapper()
    return mapper.readValue(this,T::class.java)
}
```
* 위 코드에서는 추가적으로 T의 Class를 받을 필요도 없고, T는 일반적인 클래스로 사용될 수 있다.
* json.toKotlinObject<Foo>()


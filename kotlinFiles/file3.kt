sealed interface Foo

class FooImpl1(val intFromImpl1: Int) : Foo
class FooImpl2(val intFromImpl2: Int) : Foo

fun bar(foo: Foo): Int {
    return when(foo) {
        is FooImpl1 -> foo.intFromImpl1
        is FooImpl2 -> foo.intFromImpl2
    }
}

fun main() {
    println(FooImpl1(100))
    println(FooImpl2(999))
}

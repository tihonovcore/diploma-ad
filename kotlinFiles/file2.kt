open class A {
    val x = 4
}

class B : A() {
    var y = 4
}

fun main() {
    val a = A()
    val b = B()

    println(a.x == b.x)

    println(b.y)
    b.y = 100
    println(b.y)
}

package yk.yast.kotlin

/**
 * Created by Yuri Kravchik on 15.10.2018
 */

class SimpleClass (inX:Float = 0f, inY:Float = 0f, inZ:Float = 0f) {
    var x = inX
    var y = inY
    var z = inZ

    fun foo() {

    }

    operator fun plus(b:SimpleClass): SimpleClass {

        val result = SimpleClass(x + b.x, y + b.y, z + b.z)
//        {->}.fun Function<*>.(){}()
//        var o:Array<Int> = Array(2) {_-> 0}

        return result
    }

    override fun toString(): String {
        return "SimpleClass(x=$x, y=$y, z=$z)"
    }

    fun fooUser() {
        val f = 5.0F
        val o = Object()
        //SimpleClass().foo<Object>(o) ANTLR cannot parse this
    }

    fun <T>foo(t:T):T {
        return t;
    }


}

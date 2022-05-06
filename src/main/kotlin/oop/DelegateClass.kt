package oop

interface Worker {
    fun work()
    fun takeVacation()
}

class JavaProgrammer : Worker {
    override fun work() {
        println("write java")
    }

    override fun takeVacation() {
        println("code at beach")
    }

}

class CSharpProgrammer : Worker {
    override fun work() {
        println("write C#")
    }

    override fun takeVacation() {
        println("branch at ranch")
    }

}

/**
 * 手工实现委托
 */
class ManagerDelegate(private val worker: Worker = JavaProgrammer()) {

    fun work() {
        worker.work()
    }

    fun takeVacation() {
        worker.takeVacation()
    }
}

/**
 * 具有局限性,只能委托到JavaProgrammer实例
 */
class Manager1() : Worker by JavaProgrammer()

/**
 * 委托到参数
 */
class Manager2(private val staff: Worker) : Worker by staff {
    fun meeting() {
        println("type:${staff.javaClass.simpleName}")
    }
}

/**
 * 使用override声明覆盖委托中的方法
 */
class Manager3(private val staff: Worker) : Worker by staff {
    fun meeting() {
        println("type:${staff.javaClass.simpleName}")
    }

    override fun takeVacation() {
        println("of course")
    }
}

/**
 * 推荐使用val 即使使用var在运行时改变属性也不能改变委托 委托给参数而不是属性
 */
class Manager4(var staff: Worker) : Worker by staff {

}

interface A {
    fun doThis()
    fun doA()
}

interface B {
    fun doThis()
    fun doB()
}

/**
 * 委托的多个接口中有相同方法时必须主动覆盖
 */
class C(private val a: A, private val b: B) : A by a, B by b {
    override fun doThis() {
        println("must override the same method to select impl")
        //you can also select one impl to delegate
        a.doThis()
    }
}

fun main() {
    ManagerDelegate().work()
    Manager1().work()
    Manager2(CSharpProgrammer()).work()
    Manager2(JavaProgrammer()).work()
    val manager3 = Manager3(JavaProgrammer())
    manager3.work()
    manager3.takeVacation()
    //委托类可以当做委托接口的实现类
    val m: Worker = Manager3(Manager2(Manager1()))
    m.work()
    //委托不会在运行时改变
    val m4 = Manager4(JavaProgrammer())
    m4.work() //write java
    m4.staff = CSharpProgrammer()
    m4.work() //write java
}
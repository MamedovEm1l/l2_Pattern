// PushModel.kt

// Subject class
abstract class PushSubject {
    private val observers = mutableListOf<PushObserver>()

    fun attach(observer: PushObserver) {
        observers.add(observer)
    }

    fun detach(observer: PushObserver) {
        observers.remove(observer)
    }

    protected fun notifyObservers(info: String) {
        for (observer in observers.toList()) {
            observer.onInfo(this, info)
        }
    }
}

// Concrete Subject class
class PushConcreteSubject : PushSubject() {
    private var state: String = ""

    fun setState(newState: String) {
        if (newState != state) {
            state = newState
            notifyObservers(state)
        }
    }
}

// Observer class
abstract class PushObserver {
    abstract fun onInfo(sender: PushSubject, info: String)
}

// Concrete Observer class
class PushConcreteObserver(private val detachInfo: Char) : PushObserver() {
    private var log: String = ""

    override fun onInfo(sender: PushSubject, info: String) {
        log += info
        if (info.last() == detachInfo) {
            sender.detach(this)
        }
    }

    fun getLog(): String = log
}

// Test
fun main() {
    val subject1 = PushConcreteSubject()
    val subject2 = PushConcreteSubject()

    val observers = listOf(
        PushConcreteObserver('a'),
        PushConcreteObserver('b'),
        PushConcreteObserver('c')
    )

    for (observer in observers) {
        subject1.attach(observer)
        subject2.attach(observer)
    }

    val testStrings = listOf("1a", "2b", "1c")
    for (str in testStrings) {
        if (str[0] == '1') {
            subject1.setState(str)
        } else {
            subject2.setState(str)
        }
    }

    for (observer in observers) {
        println(observer.getLog())
    }
}

// ObserverPattern.kt

// Subject class
// Subject class with a safer notifyObservers method
abstract class Subject {
    private val observers = mutableListOf<Observer>()

    fun attach(observer: Observer) {
        observers.add(observer)
    }

    fun detach(observer: Observer) {
        observers.remove(observer)
    }

    protected fun notifyObservers() {
        // Создаем копию списка, чтобы избежать ConcurrentModificationException
        val observersSnapshot = observers.toList()
        for (observer in observersSnapshot) {
            observer.update()
        }
    }
}


// Concrete Subject class
class ConcreteSubject : Subject() {
    private var state: Char = ' '

    fun setState(newState: Char) {
        if (newState != state) {
            state = newState
            notifyObservers()
        }
    }

    fun getState(): Char = state
}

// Observer class
abstract class Observer {
    abstract fun update()
}

// Concrete Observer class
class ConcreteObserver(private val subject: ConcreteSubject, private val detachInfo: Char) : Observer() {
    private var log: String = ""

    override fun update() {
        val state = subject.getState()
        log += state
        if (state == detachInfo) {
            subject.detach(this)
        }
    }

    fun getLog(): String = log
}

// Test
fun main() {
    val subject = ConcreteSubject()
    val observers = listOf(
        ConcreteObserver(subject, 'a'),
        ConcreteObserver(subject, 'b'),
        ConcreteObserver(subject, 'c')
    )

    for (observer in observers) {
        subject.attach(observer)
    }

    val testString = "abcde"
    for (char in testString) {
        subject.setState(char)
    }

    for (observer in observers) {
        println(observer.getLog())
    }
}

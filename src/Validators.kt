// Validators.kt

// Base Validator class
open class Validator {
    open fun validate(s: String): String = ""
}

// EmptyValidator class
class EmptyValidator : Validator() {
    override fun validate(s: String): String {
        return if (s.isEmpty()) "!Empty text" else ""
    }
}

// NumberValidator class
class NumberValidator : Validator() {
    override fun validate(s: String): String {
        return s.toIntOrNull()?.let { "" } ?: "!$s: not a number"
    }
}

// RangeValidator class
class RangeValidator(private val min: Int, private val max: Int) : Validator() {
    override fun validate(s: String): String {
        val num = s.toIntOrNull()
        return if (num != null && num in min..max) "" else "!$s: not in range $min..$max"
    }
}

// TextBox class
class TextBox {
    private var text: String = ""
    private var validator: Validator = Validator()

    fun setText(text: String) {
        this.text = text
    }

    fun setValidator(v: Validator) {
        this.validator = v
    }

    fun validate(): String {
        return validator.validate(text)
    }
}

// TextForm class
class TextForm(n: Int) {
    private val textBoxes = Array(n) { TextBox() }

    fun setText(ind: Int, text: String) {
        textBoxes[ind].setText(text)
    }

    fun setValidator(ind: Int, v: Validator) {
        textBoxes[ind].setValidator(v)
    }

    fun validate(): String {
        return textBoxes.joinToString { it.validate() }
    }
}

// Test
fun main() {
    val form = TextForm(3)
    form.setValidator(0, EmptyValidator())
    form.setValidator(1, NumberValidator())
    form.setValidator(2, RangeValidator(1, 100))

    form.setText(0, "")
    form.setText(1, "123")
    form.setText(2, "150")

    println(form.validate())
}

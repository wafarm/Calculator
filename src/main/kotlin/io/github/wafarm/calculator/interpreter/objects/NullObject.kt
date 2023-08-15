package io.github.wafarm.calculator.interpreter.objects

class NullObject : BaseObject {
    override val type: ObjectType = ObjectType.NULL
    override fun stringRepresentation(): String {
        return "null"
    }

    companion object {
        private val obj = NullObject()
        fun get(): NullObject {
            return obj
        }
    }
}

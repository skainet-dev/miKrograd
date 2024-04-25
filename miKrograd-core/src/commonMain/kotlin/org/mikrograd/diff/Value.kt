package org.mikrograd.diff

class Value(var data: Double, private val _children: List<Value> = listOf(), private val _op: String = "") {
    var grad: Double = 0.0
    private var _backward: () -> Unit = {}

    init {
        _backward = { }
    }

    operator fun plus(other: Value): Value {
        val out = Value(data + other.data, listOf(this, other), "+")
        out._backward = {
            this.grad += out.grad
            other.grad += out.grad
        }
        return out
    }

    operator fun times(other: Value): Value {
        val out = Value(data * other.data, listOf(this, other), "*")
        out._backward = {
            this.grad += other.data * out.grad
            other.grad += this.data * out.grad
        }
        return out
    }

    infix fun pow(other: Double): Value {
        val out = Value(Math.pow(data, other), listOf(this), "**$other")
        out._backward = {
            this.grad += (other * Math.pow(data, other - 1)) * out.grad
        }
        return out
    }

    fun relu(): Value {
        val out = Value(if (data < 0) 0.0 else data, listOf(this), "ReLU")
        out._backward = {
            if (data > 0) this.grad += out.grad
        }
        return out
    }

    fun backward() {
        val topo = mutableListOf<Value>()
        val visited = mutableSetOf<Value>()

        fun buildTopo(v: Value) {
            if (!visited.contains(v)) {
                visited.add(v)
                v._children.forEach { buildTopo(it) }
                topo.add(v)
            }
        }

        buildTopo(this)

        grad = 1.0
        topo.asReversed().forEach { it._backward() }
    }

    operator fun unaryMinus() = this.times(Value(-1.0))

    operator fun minus(other: Value) = this + (-other)

    operator fun div(other: Value) = this * (other pow -1.0)

    override fun toString(): String = "Value(data=$data, grad=$grad)"
}

// Extension functions for Double to seamlessly interact with Value instances
operator fun Double.plus(value: Value): Value = Value(this) + value
operator fun Double.times(value: Value): Value = Value(this) * value
operator fun Double.minus(value: Value): Value = Value(this) - value
operator fun Double.div(value: Value): Value = Value(this) / value

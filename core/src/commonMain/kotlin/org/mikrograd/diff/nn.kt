package org.mikrograd.diff

import kotlin.random.Random

// Base module class
open class Module {
    fun zeroGrad() {
        parameters().forEach { it.grad = 0.0 }
    }

    open fun parameters(): List<Value> = listOf()
}

// Neuron class, derived from Module
class Neuron(nin: Int, private val nonlin: Boolean = true) : Module() {
    private val w: List<Value> = List(nin) { Value(Random.nextDouble(-1.0, 1.0)) }
    private val b: Value = Value(0.0)

    operator fun invoke(x: List<Value>): Value {
        val act = (w.zip(x) { wi, xi -> wi * xi } + b).fold(Value(0.0)) { a: Value, xi: Value -> a + xi }
        return if (nonlin) act.relu() else act
    }

    override fun parameters(): List<Value> = w + b

    override fun toString(): String = "${if (nonlin) "ReLU" else "Linear"}Neuron(${w.size})"
}

// Layer class, derived from Module
class Layer(nin: Int, nout: Int, nonlin: Boolean = true) : Module() {
    private val neurons: List<Neuron> = List(nout) { Neuron(nin, nonlin) }

    operator fun invoke(x: List<Value>): List<Value> {
        return neurons.map { it(x) }
    }

    override fun parameters(): List<Value> = neurons.flatMap { it.parameters() }

    override fun toString(): String = "Layer of [${neurons.joinToString()}]"
}

// MLP class, derived from Module
class MLP(nin: Int, nouts: List<Int>) : Module() {
    private val layers: List<Layer> = nouts.mapIndexed { index, nout ->
        Layer(nin = if (index == 0) nin else nouts[index - 1], nout = nout, nonlin = index != nouts.size - 1)
    }

    operator fun invoke(x: List<Value>): List<Value> {
        var data = x
        layers.forEach { layer ->
            data = layer(data).map { it }
        }
        return data
    }

    override fun parameters(): List<Value> = layers.flatMap { it.parameters() }

    override fun toString(): String = "MLP of [${layers.joinToString()}]"
}

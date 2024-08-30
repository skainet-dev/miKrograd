package org.mikrograd.diff

import kotlin.random.Random

open class Module {
    fun zeroGrad() {
        parameters().forEach { it.grad = 0.0 }
    }

    open fun parameters(): List<Value> = listOf()
}

class Neuron(nin: Int, private val nonlin: Boolean = true, private val label: String = "") : Module() {
    private val w: List<Value> = List(nin) { Value(Random.nextDouble(-1.0, 1.0), label = "${label}_w${it}") }
    private val b: Value = Value(Random.nextDouble(-1.0, 1.0), label = "${label}-b")

    operator fun invoke(x: List<Value>): Value {
        val act: List<Value> = w.zip(x) { wi: Value, xi: Value ->
            wi * xi
        }
        var sum = act[0]
        for (i in 1 until act.size) {
            sum += act[i]
        }

        val weighted = sum + b


        val result = if (nonlin) (weighted).relu() else (sum + b)
        return result
    }

    override fun parameters(): List<Value> {
        val result = w + listOf(b)
        return result
    }

    override fun toString(): String = "${if (nonlin) "ReLU" else "Linear"}Neuron(${w.size})"
}

class Layer(nin: Int, nout: Int, nonlin: Boolean = true, label: String = "") : Module() {
    private val neurons: List<Neuron> = List(nout) { Neuron(nin, nonlin, "${label}_N$it") }

    operator fun invoke(x: List<Value>): List<Value> {
        val neurons: List<Value> = neurons.map { it(x) }
        return neurons
    }

    override fun parameters(): List<Value> = neurons.flatMap { it.parameters() }

    override fun toString(): String = "Layer of [${neurons.joinToString()}]"
}

class MLP(nin: Int, nouts: List<Int>) : Module() {
    private val layers: List<Layer> = nouts.mapIndexed { index, nout ->
        Layer(
            nin = if (index == 0) nin else nouts[index - 1],
            nout = nout,
            nonlin = index != nouts.size - 1,
            label = "L$index"
        )
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

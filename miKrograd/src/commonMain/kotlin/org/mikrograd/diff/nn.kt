package org.mikrograd.diff

import kotlin.random.Random

/*
open class Module {
    fun zeroGrad() {
        parameters().forEach { it.grad = 0.0 }
    }

    open fun parameters(): List<Value> = listOf()
}

class Neuron(nin: Int, private val label: String = "") : Module() {
    private val w: List<Value> = List(nin) { Value(Random.nextDouble(-1.0, 1.0), label = "${label}_w${it}") }
    private val b: Value = Value(Random.nextDouble(-1.0, 1.0), label = "${label}-b")

    operator fun invoke(x: List<Value>): Value {
        val act = w.zip(x) { xi, wi ->
            wi * xi
        }
        var sum = act[0]
        for (i in 1 until act.size) {
            sum += act[i]
        }

        val weighted = sum + b

        val result = (weighted).tanh()
        return result
    }

    override fun parameters(): List<Value> {
        val result = w + listOf(b)
        return result
    }

    override fun toString(): String = "${"Linear"}Neuron(${w.size})"
}

class Layer(nin: Int, nout: Int, label: String = "") : Module() {
    private val neurons: List<Neuron> = List(nout) { Neuron(nin, "${label}_N$it") }

    operator fun invoke(x: List<Value>): List<Value> {
        val neurons: List<Value> = neurons.map { it(x) }
        return neurons
    }

    override fun parameters(): List<Value> = neurons.flatMap { it.parameters() }

    override fun toString(): String = "Layer of [${neurons.joinToString()}]"
}

class MLP(nin: Int, nouts: List<Int>) : Module() {
    val sz = listOf(nin) + nouts

    private val layers: List<Layer> = nouts.indices.mapIndexed { index, v ->
        Layer(
            nin = sz[index],
            nout = sz[index + 1],
            label = "L$index"
        )
    }

    init {
        val layerss = mutableListOf<Layer>()


    }

    operator fun invoke(x: List<Double>): List<Value> {
        var first = x.map { value -> Value(value) }
        layers.forEach { layer ->
            first = layer(first)
        }
        return first
    }

    override fun parameters(): List<Value> = layers.flatMap { it.parameters() }

    override fun toString(): String = "MLP of [${layers.joinToString()}]"
}

 */

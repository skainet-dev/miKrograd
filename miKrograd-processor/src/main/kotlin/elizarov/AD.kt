package elizarov

//https://gist.github.com/elizarov/1ad3a8583e88cb6ea7a0ad09bb591d3d

/*
 * Implementation of backward-mode automatic differentiation.
 */

/**
 * Differentiable variable with value and derivative of differentiation ([grad]) result
 * with respect to this variable.
 */
data class D(var x: Double, var d: Double = 0.0) {
    constructor(x: Int): this(x.toDouble())
}

/**
 * Runs differentiation and establishes [AD] context inside the block of code.
 *
 * Example:
 * ```
 * val x = D(2) // define variable(s) and their values
 * val y = grad { sqr(x) + 5 * x + 3 } // write formulate in grad context
 * assertEquals(17.0, y.x) // the value of result (y)
 * assertEquals(9.0, x.d)  // dy/dx
 * ```
 */
fun grad(body: AD.() -> D): D =
    ADImpl().run {
        val result = body()
        result.d = 1.0 // computing derivative w.r.t result
        runBackwardPass()
        result
    }

/**
 * Automatic Differentiation context class.
 */
abstract class AD {
    /**
     * Performs update of derivative after the rest of the formula in the back-pass.
     *
     * For example, implementation of `sin` function is:
     *
     * ```
     * fun AD.sin(x: D): D = derive(D(sin(x.x)) { z -> // call derive with function result
     *     x.d += z.d * cos(x.x) // update derivative using chain rule and derivative of the function
     * }
     * ```
     */
    abstract fun <R> derive(value: R, block: (R) -> Unit): R

    // Basic math (+, -, *, /)

    operator fun D.plus(that: D): D = derive(D(this.x + that.x)) { z ->
        this.d += z.d
        that.d += z.d
    }

    operator fun D.minus(that: D): D = derive(D(this.x - that.x)) { z ->
        this.d += z.d
        that.d -= z.d
    }

    operator fun D.times(that: D): D = derive(D(this.x * that.x)) { z ->
        this.d += z.d * that.x
        that.d += z.d * this.x
    }

    operator fun D.div(that: D): D = derive(D(this.x / that.x)) { z ->
        this.d += z.d / that.x
        that.d -= z.d * this.x / (that.x * that.x)
    }

    // Overloads for Double constants

    operator fun Double.plus(that: D): D = derive(D(this + that.x)) { z ->
        that.d += z.d
    }

    operator fun D.plus(b: Double): D = b.plus(this)

    operator fun Double.minus(that: D): D = derive(D(this - that.x)) { z ->
        that.d -= z.d
    }

    operator fun D.minus(that: Double): D = derive(D(this.x - that)) { z ->
        this.d += z.d
    }

    operator fun Double.times(that: D): D = derive(D(this * that.x)) { z ->
        that.d += z.d * this
    }

    operator fun D.times(b: Double): D = b.times(this)

    operator fun Double.div(that: D): D = derive(D(this / that.x)) { z ->
        that.d -= z.d * this / (that.x * that.x)
    }

    operator fun D.div(that: Double): D = derive(D(this.x / that)) { z ->
        this.d += z.d / that
    }

    // Overloads for Int constants

    operator fun Int.plus(b: D): D = toDouble().plus(b)
    operator fun D.plus(b: Int): D = plus(b.toDouble())
    operator fun Int.minus(b: D): D = toDouble().minus(b)
    operator fun D.minus(b: Int): D = minus(b.toDouble())
    operator fun Int.times(b: D): D = toDouble().times(b)
    operator fun D.times(b: Int): D = times(b.toDouble())
    operator fun Int.div(b: D): D = toDouble().div(b)
    operator fun D.div(b: Int): D = div(b.toDouble())
}

// ---------------------------------------- ENGINE IMPLEMENTATION ----------------------------------------

// Private implementation class
private class ADImpl : AD() {
    // this stack contains pairs of blocks and values to apply them to
    private var stack = arrayOfNulls<Any?>(8)
    private var sp = 0

    @Suppress("UNCHECKED_CAST")
    override fun <R> derive(value: R, block: (R) -> Unit): R {
        // save block to stack for backward pass
        if (sp >= stack.size) stack = stack.copyOf(stack.size * 2)
        stack[sp++] = block
        stack[sp++] = value
        return value
    }

    @Suppress("UNCHECKED_CAST")
    fun runBackwardPass() {
        while (sp > 0) {
            val value = stack[--sp]
            val block = stack[--sp] as (Any?) -> Unit
            block(value)
        }
    }
}

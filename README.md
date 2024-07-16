# miKrograd

This project is an attempt to port micrograd - [a tiny scalar-valued autograd engine and a neural net library on top of it with PyTorch-like API](https://github.com/karpathy/micrograd) by Andrej Karpathy into Kotlin multiplatform.

### Example usage

Below is already running Kotlin code. Its a contrived example showing a number of possible supported operations:

```kotlin
import org.mikrograd.diff.Value
import org.mikrograd.diff.div
import org.mikrograd.diff.plus

val a = Value(-4.0)
val b = Value(2.0)
var c = a + b
var d = a * b + b.pow(3.0)
c += c + 1
c += 1.0 + c + (-a)
d += d * 2 + (b + a).relu()
d += d * 3.0 + (b - a).relu()
val e = c - d
val f = e.pow(2.0)
var g = f / 2
g += 10.0 / f
println("$g")  // prints 24.7041, the outcome of this forward pass
g.backward()
println("${a.grad}") // prints 138.8338, i.e. the numerical value of dg/da
println("${b.grad}") // prints 645.5773, i.e. the numerical value of dg/db```
```

Vizualized DAG
![DAG](dag.png)

### License

MIT

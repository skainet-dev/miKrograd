# miKrograd

This project is an attempt to port micrograd - [a tiny scalar-valued autograd engine and a neural net library on top of it with PyTorch-like API](https://github.com/karpathy/micrograd) by Andrej Karpathy into Kotlin multiplatform.

### Example usage

Below is a Kotlin, what we want to achive. Its a contrived example showing a number of possible supported operations:

```kotlin
import mikrograd.engine.Value

val a = Value(-4.0)
val b = Value(2.0)
val c = a + b
val d = a * b + b**3
val c += c + 1
val c += 1 + c + (-a)
val d += d * 2 + (b + a).relu()
val d += 3 * d + (b - a).relu()
val e = c - d
val f = e**2
val g = f / 2.0f
val g += 10.0 / f
print("${g.data:.4f}') // prints 24.7041, the outcome of this forward pass
g.backward()
print("${a.grad:.4f}") // prints 138.8338, i.e. the numerical value of dg/da
print("${b.grad:.4f}") // prints 645.5773, i.e. the numerical value of dg/db
```

### License

MIT

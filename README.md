# miKrograd

This project is an attempt to port of [a tiny scalar-valued autograd engine and a neural net library on top of it with PyTorch-like API ]([https://pages.github.com/](https://github.com/karpathy/micrograd)) by Andrej Karpathy into Kotlin multiplatform.

# micrograd

![awww](puppy.jpg)

A tiny Autograd engine (with a bite! :)). Implements backpropagation (reverse-mode autodiff) over a dynamically built DAG and a small neural networks library on top of it with a PyTorch-like API. Both are tiny, with about 100 and 50 lines of code respectively. The DAG only operates over scalar values, so e.g. we chop up each neuron into all of its individual tiny adds and multiplies. However, this is enough to build up entire deep neural nets doing binary classification, as the demo notebook shows. Potentially useful for educational purposes.

### Installation

```bash
pip install micrograd
```

### Example usage

Below is a slightly contrived example showing a number of possible supported operations:

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

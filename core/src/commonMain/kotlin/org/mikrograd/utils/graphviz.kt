package org.mikrograd.utils

import org.markup.dot.Graph
import org.markup.dot.graph
import org.mikrograd.diff.Value

fun trace(root: Value): Pair<Set<Value>, Set<Pair<Value, Value>>> {
    val nodes = mutableSetOf<Value>()
    val edges = mutableSetOf<Pair<Value, Value>>()

    fun build(v: Value) {
        if (v !in nodes) {
            nodes.add(v)
            for (child in v._children) {
                edges.add(child to v)
                build(child)
            }
        }
    }

    build(root)
    return nodes to edges
}

fun drawDot(root: Value, withGradient:Boolean = false, rankdir: String = "LR"): Graph {
    require(rankdir in listOf("LR", "TB"))

    val (nodes, edges) = trace(root)

    return graph {
        directed()
        rankdir(rankdir)

        // Add nodes
        for (n in nodes) {
            node(id = n.hashCode().toString()) {
                shape("record")

                val labelMarkerStart =  if (rankdir != "LR") "{" else ""
                val labelMarkerEnd =  if (rankdir != "LR") "}" else ""

                if (withGradient) {
                    // Using '|' to separate data and grad, only data in this case
                    label("$labelMarkerStart %s | value %.4f | grad %.4f $labelMarkerEnd".format(n.label, n.data, n.grad))
                } else {
                    label("$labelMarkerStart %s | value %.4f $labelMarkerEnd".format(n.label, n.data, n.grad))

                }
            }
            n.op.takeIf { it.isNotEmpty() }?.let { safeOp ->
                val opId = "\"${n.hashCode()}$safeOp\""
                node(id = opId) {
                    label(safeOp)
                }
                edge(from = opId, to = n.hashCode().toString())
            }
        }

        // Add edges
        for ((n1, n2) in edges) {
            val n2Op = n2.op.takeIf { it.isNotEmpty() }?.let { "\"${n2.hashCode()}$it\"" } ?: n2.hashCode().toString()
            edge(from = n1.hashCode().toString(), to = n2Op)
        }
    }
}

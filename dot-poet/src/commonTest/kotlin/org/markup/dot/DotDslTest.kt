package org.markup.dot

import kotlin.test.Test
import kotlin.test.assertEquals

class DotDslTest {

    @Test
    fun `simple graph`() {
        val myGraph = graph {
            name("StyledGraph")
            node("A") {
                shape("box")
                color("red")
            }
            node("B") {
                shape("ellipse")
                color("blue")
            }
            node("C") {
                shape("diamond")
                color("green")
            }
            edge("A", "B") {
                style("dashed")
                color("black")
            }
            edge("B", "C") {
                style("bold")
                color("orange")
            }
            edge("C", "A") {
                style("dotted")
                color("purple")
            }
        }

        assertEquals(
            """
                graph StyledGraph {
                A [shape="box", color="red"];
                B [shape="ellipse", color="blue"];
                C [shape="diamond", color="green"];
                A -- B [style="dashed", color="black"];
                B -- C [style="bold", color="orange"];
                C -- A [style="dotted", color="purple"];
                }
            """.trimIndent(),
            myGraph.render().trimIndent()
        )

    }

    @Test
    fun `simple directed graph`() {
        val myGraph = graph {
            directed()
            name("StyledGraph")
            node("A") {
                shape("box")
                color("red")
            }
            node("B") {
                shape("ellipse")
                color("blue")
            }
            node("C") {
                shape("diamond")
                color("green")
            }
            edge("A", "B") {
                style("dashed")
                color("black")
            }
            edge("B", "C") {
                style("bold")
                color("orange")
            }
            edge("C", "A") {
                style("dotted")
                color("purple")
            }
        }
        assertEquals(
            """
                digraph StyledGraph {
                A [shape="box", color="red"];
                B [shape="ellipse", color="blue"];
                C [shape="diamond", color="green"];
                A -> B [style="dashed", color="black"];
                B -> C [style="bold", color="orange"];
                C -> A [style="dotted", color="purple"];
                }
            """.trimIndent(),
            myGraph.render().trimIndent()
        )
    }

    @Test
    fun `simple directed graph with rankdir`() {
        val myGraph = graph {
            rankdir("LR")
            directed()
            name("StyledGraph")
            node("A") {
                shape("box")
                color("red")
            }
            node("B") {
                shape("ellipse")
                color("blue")
            }
            node("C") {
                shape("diamond")
                color("green")
            }
            edge("A", "B") {
                style("dashed")
                color("black")
            }
            edge("B", "C") {
                style("bold")
                color("orange")
            }
            edge("C", "A") {
                style("dotted")
                color("purple")
            }
        }
        assertEquals(
            """
                digraph StyledGraph {
                A [shape="box", color="red"];
                B [shape="ellipse", color="blue"];
                C [shape="diamond", color="green"];
                A -> B [style="dashed", color="black"];
                B -> C [style="bold", color="orange"];
                C -> A [style="dotted", color="purple"];
                }
            """.trimIndent(),
            myGraph.render().trimIndent()
        )
    }

}
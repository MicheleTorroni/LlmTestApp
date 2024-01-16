package llmTests.a201901a.scala

import java.util
import java.util.{List, Set}


import llmTests.a201901a.scala.Graph

trait GraphFactory { /**
 * @param <X>
 * @param nodes, say x1,..,xn, all distinct
 * @return a graph with edges x1->x2, x2->x3, .., x(n-1)->xn
 */def createDirectedChain[X](nodes: util.List[X]): Graph[X]
  /**
   * @param <X>
   * @param nodes, say x1,..,xn, all distinct
   * @return a graph with edges x1<->x2, x2<->x3, .., x(n-1)<->xn
   */def createBidirectionalChain[X](nodes: util.List[X]): Graph[X]
  /**
   * @param <X>
   * @param nodes, say x1,..,xn, all distinct
   * @return a graph with edges x1->x2, x2->x3, .., x(n-1)->xn, xn->x1
   */def createDirectedCircle[X](nodes: util.List[X]): Graph[X]
  /**
   * @param <X>
   * @param nodes, say x1,..,xn, all distinct
   * @return a graph with edges x1<->x2, x2<->x3, .., x(n-1)<->xn, xn<->x1
   */def createBidirectionalCircle[X](nodes: util.List[X]): Graph[X]
  /**
   * @param <X>
   * @param nodes, say x1,..,xn
   * @param center, say c
   * @return a graph with edges c->x1, c->x2, .., x->xn
   */def createDirectedStar[X](center: X, nodes: util.Set[X]): Graph[X]
  /**
   * @param <X>
   * @param nodes, say x1,..,xn
   * @param center, say c
   * @return a graph with edges c<->x1, c<->x2, .., x<->xn
   */def createBidirectionalStar[X](center: X, nodes: util.Set[X]): Graph[X]
  /**
   * @param <X>
   * @param nodes, say x1,..,xn
   * @return a graph with an edge xi -> xj for each i!=j
   */def createFull[X](nodes: util.Set[X]): Graph[X]
  /**
   * @param <X>
   * @param g1
   * @param g2
   * @return a graph obtained by set-union of nodes and edges of g1 and g2
   * IT IS IN THE OPTIONAL PART OF THE EXAM!
   */def combine[X](g1: Graph[X], g2: Graph[X]): Graph[X]
}

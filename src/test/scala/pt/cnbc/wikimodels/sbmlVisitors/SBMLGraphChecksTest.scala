/*
 * Copyright (c) 2013. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.sbmlVisitors.helpers

import org.junit._
import Assert._
import pt.cnbc.wikimodels.dataModel.{Compartment, SBMLModel}
import collection.immutable.Stream.Empty
import java.lang.String
import scalax.collection.GraphEdge.DiEdge

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 1/18/13
  *         Time: 5:43 PM */
class SBMLGraphChecksTest {

  @Before
  def setUp: Unit = {
    Console.println(this.getClass + ".setUp() is running ")
  }

  @After
  def tearDown: Unit = {
    Console.println(this.getClass + ".tearDown() is running ")
  }

  @Test
  def checkGraphLibraryBug = {
    import scalax.collection.mutable.Graph
    import scalax.collection.GraphPredef._, scalax.collection.GraphEdge._

    var g = Graph(2~>3)
    assertTrue(g.
      isAcyclic)
  }

  @Test
  def checkForCyclesInCompartments = {
    var m = new SBMLModel()
    val c1 = new Compartment("1", Empty, "1", "1", null, 3, 1.1,null, "2",true)
    val c2 = new Compartment("2", Empty, "2", "1", null, 3, 1.1,null, "3",true)
    val c3 = new Compartment("3", Empty, "3", "1", null, 3, 1.1,null, "4",true)
    val c4 = new Compartment("4", Empty, "4", "1", null, 3, 1.1,null, "5",true)
    val c5 = new Compartment("5", Empty, "5", "1", null, 3, 1.1,null, "6",true)
    val c6 = new Compartment("6", Empty, "6", "1", null, 3, 1.1,null, "1",true)

    import scala.collection.JavaConversions._
    m.listOfCompartments = List(c1, c2, c3, c4, c5, c6)
    Console.println(m)
    assertEquals( SBMLGraphChecks.
      checkForCyclesInCompartments(m).
      size  ,  1)

  }
}


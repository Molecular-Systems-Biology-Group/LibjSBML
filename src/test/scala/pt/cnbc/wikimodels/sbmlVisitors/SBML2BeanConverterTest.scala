/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.sbmlVisitors

import org.junit._
import Assert._

import pt.cnbc.wikimodels.dataModel.Compartment

/**TODO: Please document.
 *   @author: alex
 *  Date: 10-01-2012
 *  Time: 6:55 */
class SBML2BeanConverterTest {

  @Before
  def setUp: Unit = {
    Console.println(this.getClass+".setUp() is running ")
  }

  @After
  def tearDown: Unit = {
    Console.println(this.getClass+".tearDown() is running ")
  }

  @Test
  def checkIfCompartmentSizeRemainsEmpty = {
    val cXml =
      <compartment  constant="true" metaid="c1" name="c1"  compartmentType="" spatialDimensions="3"  id="c1">
        <notes>
          <p xmlns="http://www.w3.org/1999/xhtml">
            Compartment 1
          </p>
        </notes>
      </compartment>
    assertNull ( SBML2BeanConverter.visitCompartment(cXml).size )
    val cXMLFinal = ( SBML2BeanConverter.visitCompartment(cXml) ).toXML

    assertEquals(cXMLFinal \ "@size" text, "" )
  }

}


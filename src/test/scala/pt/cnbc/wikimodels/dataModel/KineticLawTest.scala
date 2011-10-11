/*
 * ParameterTest.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.dataModel

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert._
import pt.cnbc.wikimodels.dataVisitors.SBML2BeanConverter

class KineticLawTest{
  val kineticLaw =
  <kineticLaw>
   <math xmlns="http://www.w3.org/1998/Math/MathML">
     <apply>
       <times/>
       <ci>CaMKII</ci>
       <ci>kcat</ci>
       <apply>
         <divide/>
         <ci>NMDAR</ci>
         <apply>
           <times/>
           <ci>NMDAR</ci>
           <ci>Km</ci>
         </apply>
       </apply>
     </apply>
   </math>
   <listOfParameters>
     <parameter id="kcat" value="1"/>
     <parameter id="Km" value="5e-10"/>
   </listOfParameters>
 </kineticLaw>


  val mathml =  <math><apply>
       <times/>
       <ci>CaMKII</ci>
       <ci>kcat</ci>
       <apply>
         <divide/>
         <ci>NMDAR</ci>
         <apply>
           <times/>
           <ci>NMDAR</ci>
           <ci>Km</ci>
         </apply>
       </apply>
     </apply>         </math>

  @Before
  def setUp: Unit = {
  }

  @After
  def tearDown: Unit = {
  }



  @Test
  def roundTripParameter1 = {

  }

  @Test
  def createKineticLaw = {
    val kl = new KineticLaw( """metaid""" ,
         <p>dasdfs</p> ,
         mathml  )
    Console.println("XML representation of the model is "
                    + kl.toXML.toString)
    val kl2 = SBML2BeanConverter.visitKineticLaw(kl.toXML)
    Console.println("XML representation of the reound tripped model is "
                    + kl2.toXML.toString)
    assertTrue(true )
  }
  @Test
  def createKineticLawWithoutNotes = {
      val kl = new KineticLaw( """metaid""" ,
           Nil ,
           mathml  )
      Console.println("XML representation of the model is "
                      + kl.toXML.toString)
      val kl2 = SBML2BeanConverter.visitKineticLaw(kl.toXML)
      Console.println("XML representation of the reound tripped model is "
                      + kl2.toXML.toString)
      assertTrue(true )
  }
}

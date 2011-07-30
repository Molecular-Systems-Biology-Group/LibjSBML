package pt.cnbc.wikimodels.dataModel

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 4/Jun/2010
 * Time: 16:17:31
 * To change this template use File | Settings | File Templates.
 */

import org.junit._
import Assert._

class ReactionTest {

  val reaction1 =
    <reaction id="J1">
      <listOfReactants>
          <speciesReference id="RefX0" species="X0"/>
      </listOfReactants>
      <listOfProducts>
          <speciesReference id="RefS1" species="S1"/>
      </listOfProducts>
      <listOfModifiers>
          <modifierSpeciesReference id="RefS2" species="S2"/>
      </listOfModifiers>
      <kineticLaw>
        <math xmlns="http://www.w3.org/1998/Math/MathML">
          <apply>
              <times/> <ci>k</ci> <ci>S2</ci> <ci>X0</ci> <ci>c1</ci>
          </apply>
        </math>
        <listOfParameters>
            <parameter id="k" value="0.1" units="per_concent_per_time"/>
            <parameter id="k2" value="0.1" units="per_concent_per_time"/>
        </listOfParameters>
      </kineticLaw>
    </reaction>
  
  @Before    
  def setUp: Unit = {
    }

  @After
  def tearDown: Unit = {
    }


  @Test
  def roundTripKineticLaw = {
    val react = new Reaction(reaction1)
    val reactXML = react.toXML

    //check local parameter list
    assertEquals(reaction1 \ "kineticLaw" length,
                 reactXML \ "kineticLaw" length )
    }
  @Test
  def roundTripPArametersInKineticLaw = {
    val react = new Reaction(reaction1)
    val reactXML = react.toXML

    //check local parameter list
    assertEquals(reaction1 \ "kineticLaw" \ "listOfParamenters" \ "Parameter" length,
                 reactXML \ "kineticLaw" \ "listOfParamenters" \ "Parameter" length )
    }

  @Test
  def roundTripMathInKineticLaw = {
    val react = new Reaction(reaction1)
    val reactXML = react.toXML

    //check local math
    assertEquals(reaction1 \ "kineticLaw" \ "math" \ "apply" \ "ci" length,
                 4)
  }
}

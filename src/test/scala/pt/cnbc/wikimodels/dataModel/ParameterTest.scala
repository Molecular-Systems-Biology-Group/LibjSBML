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
import pt.cnbc.wikimodels.exceptions.BadFormatException
import pt.cnbc.wikimodels.sbmlVisitors.SBML2BeanConverter

class ParameterTest{

    val parameters =
    <listOfParameters>
        <parameter metaid="metaid_0000004" id="a" value="0.02"/>
        <parameter metaid="metaid_0000005" id="b" value="-0.1"/>
        <parameter metaid="metaid_0000006" id="c" value="-55"/>
        <parameter metaid="metaid_0000007" id="d" value="6"/>
        <parameter metaid="metaid_0000011" id="Vthresh" value="30"/>
        <parameter metaid="metaid_0000016" id="i" value="0" constant="false"/>
        <parameter metaid="metaid_0000017" id="flag" value="0" constant="false"/>
        <parameter metaid="metaid_0000020" id="v" value="-60" constant="false"/>
        <parameter metaid="metaid_0000023" id="u" constant="false"/>
    </listOfParameters>

    val paramResults = 
    <listOfParameters>
        <parameter metaid="metaid_0000004" id="a" value="0.02" constant="true"/>
        <parameter metaid="metaid_0000005" id="b" value="-0.1" constant="true"/>
        <parameter metaid="metaid_0000006" id="c" value="-55" constant="true"/>
        <parameter metaid="metaid_0000007" id="d" value="6" constant="true"/>
        <parameter metaid="metaid_0000011" id="Vthresh" value="30" constant="true"/>
        <parameter metaid="metaid_0000016" id="i" value="0" constant="false"/>
        <parameter metaid="metaid_0000017" id="flag" value="0" constant="false"/>
        <parameter metaid="metaid_0000020" id="v" value="-60" constant="false"/>
        <parameter metaid="metaid_0000023" id="u" constant="false"/>
    </listOfParameters>


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
    def createParameterWithoutNotes = {
        val param = new SBMLModel("any_metaid", Nil,
                                  "any_id", "any_name")
        Console.println("XML representation of the model is "
                        + param.toXML.toString)
        val param2 = SBML2BeanConverter.visitModel(param.toXML)
        Console.println("XML representation of the reound tripped model is "
                        + param2.toXML.toString)
        assertTrue(param == param2 )
    }


    @Test
    def createParameterWithoutMetaId = {
        val param = new SBMLModel(null, <p>dasdfs</p>,
                                  "any_id", "any_name")
        Console.println("XML representation of the model is "
                        + param.toXML.toString)
        val param2 = SBML2BeanConverter.visitModel(param.toXML)
        Console.println("XML representation of the reound tripped model is "
                        + param2.toXML.toString)
        assertTrue(param == param2 )
    }

    @Test( expected = classOf[BadFormatException] )
    def createParameterWithoutId =  {
        val param = new Parameter("any_metaid", <p>dasdfs</p>,
                                 null, "any_name", 0.2, null, true)
        Console.println("XML representation of the model is "
                        + param.toXML.toString)
        val param2 = SBML2BeanConverter.visitParameter(param.toXML)
        Console.println("XML representation of the reound tripped model is "
                        + param2.toXML.toString)
    }

    @Test
    def createParameterWithoutName = {
        val param = new SBMLModel("any_metaid", <p>dasdfs</p>,
                                 "any_id", null)
        Console.println("XML representation of the model is "
                        + param.toXML.toString)
        val param2 = SBML2BeanConverter.visitModel(param.toXML)
        Console.println("XML representation of the reound tripped model is "
                        + param2.toXML.toString)
        assertTrue(param == param2 )
    }
}

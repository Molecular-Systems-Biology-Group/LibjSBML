/*
 * SBMLModelTest.scala
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
import pt.cnbc.wikimodels.dataVisitors.SBML2BeanConverter
//import alexmsmartins.log.LoggerWrapper
import pt.cnbc.wikimodels.util.SBMLHandler

class SBMLModelTest {
  val model1 =
    <model metaid="metaid_0000002" id="model_0000001" name="Izhikevich2004_SpikingNeurons_Class1Excitable">
      <annotation>
        <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:vCard="http://www.w3.org/2001/vcard-rdf/3.0#" xmlns:bqbiol="http://biomodels.net/biology-qualifiers/" xmlns:bqmodel="http://biomodels.net/model-qualifiers/">
          <rdf:Description rdf:about="#metaid_0000002">
            <dc:creator>
              <rdf:Bag>
                <rdf:li rdf:parseType="Resource">
                  <vCard:N rdf:parseType="Resource">
                    <vCard:Family>He</vCard:Family>
                    <vCard:Given>Enuo</vCard:Given>
                  </vCard:N>
                  <vCard:EMAIL>enuo@caltech.edu</vCard:EMAIL>
                  <vCard:ORG rdf:parseType="Resource">
                    <vCard:Orgname>BNMC</vCard:Orgname>
                  </vCard:ORG>
                </rdf:li>
              </rdf:Bag>
            </dc:creator>
            <dcterms:created rdf:parseType="Resource">
              <dcterms:W3CDTF>2007-07-16T09:41:14Z</dcterms:W3CDTF>
            </dcterms:created>
            <dcterms:modified rdf:parseType="Resource">
              <dcterms:W3CDTF>2007-09-25T10:29:37Z</dcterms:W3CDTF>
            </dcterms:modified>
            <bqmodel:is>
              <rdf:Bag>
                  <rdf:li rdf:resource="urn:miriam:biomodels.db:BIOMD0000000141"/>
              </rdf:Bag>
            </bqmodel:is>
            <bqmodel:isDescribedBy>
              <rdf:Bag>
                  <rdf:li rdf:resource="urn:miriam:pubmed:15484883"/>
              </rdf:Bag>
            </bqmodel:isDescribedBy>
            <bqbiol:isVersionOf>
              <rdf:Bag>
                  <rdf:li rdf:resource="urn:miriam:obo.go:GO%3A0042391"/>
                  <rdf:li rdf:resource="urn:miriam:obo.go:GO%3A0001508"/>
                  <rdf:li rdf:resource="urn:miriam:obo.go:GO%3A0019228"/>
              </rdf:Bag>
            </bqbiol:isVersionOf>
            <bqbiol:is>
              <rdf:Bag>
                  <rdf:li rdf:resource="urn:miriam:taxonomy:40674"/>
              </rdf:Bag>
            </bqbiol:is>
          </rdf:Description>
        </rdf:RDF>
      </annotation>
      <!-- <listOfCompartments>
    <compartment metaid="metaid_0000003" id="cell" size="1">
      <annotation>
        <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:vCard="http://www.w3.org/2001/vcard-rdf/3.0#" xmlns:bqbiol="http://biomodels.net/biology-qualifiers/" xmlns:bqmodel="http://biomodels.net/model-qualifiers/">
          <rdf:Description rdf:about="#metaid_0000003">
            <bqbiol:isVersionOf>
              <rdf:Bag>
                <rdf:li rdf:resource="urn:miriam:obo.go:GO%3A0005623"/>
              </rdf:Bag>
            </bqbiol:isVersionOf>
          </rdf:Description>
        </rdf:RDF>
      </annotation>
    </compartment>
  </listOfCompartments> -->
      <listOfParameters>
          <parameter metaid="metaid_0000004" id="a" value="0.02"/>
          <parameter metaid="metaid_0000005" id="b" value="-0.1"/>
          <parameter metaid="metaid_0000006" id="c" value="-55"/>
          <parameter metaid="metaid_0000007" id="d" value="6"/>
          <parameter metaid="metaid_0000011" id="Vthresh" value="30"/>
          <parameter metaid="metaid_0000016" id="i" value="0" constant="false"/>
          <parameter metaid="metaid_0000017" id="flag" value="0" constant="false"/>
          <parameter metaid="metaid_0000020" id="v" value="-60" constant="false"/>
          <parameter metaid="metaid_0000023" id="u" value="6" constant="false"/>
      </listOfParameters>
      <listOfRules>
        <assignmentRule metaid="metaid_0000021" variable="i">
          <math xmlns="http://www.w3.org/1998/Math/MathML">
            <apply>
                <times/>
              <ci>flag</ci>
              <cn>0.075</cn>
              <apply>
                  <minus/>
                <csymbol encoding="text" definitionURL="http://www.sbml.org/sbml/symbols/time">time</csymbol>
                <cn type="integer">30</cn>
              </apply>
            </apply>
          </math>
        </assignmentRule>
        <rateRule metaid="metaid_0000024" variable="v">
          <math xmlns="http://www.w3.org/1998/Math/MathML">
            <apply>
                <plus/>
              <apply>
                  <minus/>
                <apply>
                    <plus/>
                  <apply>
                      <times/>
                    <cn>0.04</cn>
                    <apply>
                        <power/>
                      <ci>v</ci>
                      <cn type="integer">2</cn>
                    </apply>
                  </apply>
                  <apply>
                      <times/>
                    <cn>4.1</cn>
                    <ci>v</ci>
                  </apply>
                  <cn type="integer">108</cn>
                </apply>
                <ci>u</ci>
              </apply>
              <ci>i</ci>
            </apply>
          </math>
        </rateRule>
        <rateRule metaid="metaid_0000025" variable="u">
          <math xmlns="http://www.w3.org/1998/Math/MathML">
            <apply>
                <times/>
              <ci>a</ci>
              <apply>
                  <minus/>
                <apply>
                    <times/>
                  <ci>b</ci>
                  <ci>v</ci>
                </apply>
                <ci>u</ci>
              </apply>
            </apply>
          </math>
        </rateRule>
      </listOfRules>
      <listOfEvents>
        <event metaid="metaid_0000012" id="event_0000001">
          <trigger>
            <math xmlns="http://www.w3.org/1998/Math/MathML">
              <apply>
                  <gt/>
                <ci>v</ci>
                <ci>Vthresh</ci>
              </apply>
            </math>
          </trigger>
          <listOfEventAssignments>
            <eventAssignment variable="v">
              <math xmlns="http://www.w3.org/1998/Math/MathML">
                <ci>c</ci>
              </math>
            </eventAssignment>
            <eventAssignment variable="u">
              <math xmlns="http://www.w3.org/1998/Math/MathML">
                <apply>
                    <plus/>
                  <ci>u</ci>
                  <ci>d</ci>
                </apply>
              </math>
            </eventAssignment>
          </listOfEventAssignments>
        </event>
        <event metaid="metaid_0000018" id="event_0000002">
          <trigger>
            <math xmlns="http://www.w3.org/1998/Math/MathML">
              <apply>
                  <gt/>
                <csymbol encoding="text" definitionURL="http://www.sbml.org/sbml/symbols/time">time</csymbol>
                <cn type="integer">30</cn>
              </apply>
            </math>
          </trigger>
          <listOfEventAssignments>
            <eventAssignment variable="flag">
              <math xmlns="http://www.w3.org/1998/Math/MathML">
                <cn type="integer">1</cn>
              </math>
            </eventAssignment>
          </listOfEventAssignments>
        </event>
      </listOfEvents>
    </model>


  @Before
  def setUp: Unit = {
  }

  @After
  def tearDown: Unit = {
  }

  @Test
  def createModelWithoutComponents = {
    Console.println("Running test SBMLModelTest.createModelWithoutComponents")
    val sbml = new SBMLModel("any_metaid", <p>dasdfs</p>,
      "any_id", "any_name")
    Console.println("XML representation of the model is "
   + sbml.toXML.toString)
    val sbml2 = SBML2BeanConverter.visitModel(sbml.toXML)
    Console.println("XML representation of the reound tripped model is "
   + sbml2.toXML.toString)


    assertTrue(sbml == sbml2)
  }


  @Test
  def createModelWithoutNotes = {
    Console.println("Running test SBMLModelTest.createModelWithoutNotes")
    val sbml = new SBMLModel("any_metaid", Nil,
      "any_id", "any_name")
    Console.println("XML representation of the model is "
            + sbml.toXML.toString)
    val sbml2 = SBML2BeanConverter.visitModel(sbml.toXML)
    Console.println("XML representation of the reound tripped model is "
            + sbml2.toXML.toString)
    assertTrue(sbml == sbml2)
  }


  @Test
  def xmlInModelWithoutMetaId = {
    Console.println("Running test SBMLModelTest.xmlInModelWithoutMetaId")
      try{
          val sbml = new SBMLModel(null, <p>dasdfs</p>,
                                   "any_id", "any_name")
          Console.println("XML representation of the model is "
                          + sbml.toXML.toString)
          assertTrue(true)   //test passes since metaid's in SBML are not mandatory
      } catch {
          case ex:pt.cnbc.wikimodels.exceptions.BadFormatException =>
              assertTrue(true) //Test passes
          case ex => throw ex
      }
  }

  @Test(expected = classOf[BadFormatException])
  def createModelWithoutId = {
    Console.println("Running test SBMLModelTest.createModelWithoutId")
    val sbml = new SBMLModel("any_metaid", <p>dasdfs</p>,
      null, "any_name")
    Console.println("XML representation of the model is "
            + sbml.toXML.toString)
    val sbml2 = SBML2BeanConverter.visitModel(sbml.toXML)
    Console.println("XML representation of the reound tripped model is "
            + sbml2.toXML.toString)
    assertTrue(sbml == sbml2)
  }

  @Test
  def createModelWithoutName = {
    Console.println("Running test SBMLModelTest.createModelWithoutName")
   val sbml = new SBMLModel("any_metaid", <p>dasdfs</p>,
      "any_id", null)
    Console.println("XML representation of the model is "
            + sbml.toXML.toString)
    val sbml2 = SBML2BeanConverter.visitModel(sbml.toXML)
    Console.println("XML representation of the reound tripped model is "
            + sbml2.toXML.toString)
    assertTrue(sbml == sbml2)
  }

  @Test
  def createModelWithParameters = {
    Console.println("Running test SBMLModelTest.createModelWithParameters")
    val xmlModelWithParameters =
      <model id="transcription">
        <listOfParameters>
            <parameter id="transcriptionDelay" value="10" units="time"/>
            <parameter id="transcriptionDelay2" value="102" units="time"/>
        </listOfParameters>
      </model>
    val modelWParamenters = SBML2BeanConverter.visitModel(xmlModelWithParameters)
    val model = new SBMLModel()
    val lParamSize = (modelWParamenters.toXML \ "listOfParameters" \ "parameter")
            .map(i => SBML2BeanConverter.visitParameter(i.asInstanceOf[scala.xml.Elem])).size

    assertEquals(lParamSize, 2)
  }

  @Test
  def setNotesFromXML = {
    Console.println("Running test SBMLModelTest.setNotesFromXML")
    val note1= <p>THIS AS TO APPEAR</p>
    val note2 = <p><h3>Olá</h3><br/>Corpo.<!-- Commentario--></p>.child
    val sbml = new SBMLModel
    Console.println("SBMLHandler.addNamespaceToXHTMbL call has returned " + SBMLHandler.addNamespaceToXHTML(note1))
    Console.println("SBMLHandler.addNamespaceToXHTML call has returned " + SBMLHandler.addNamespaceToXHTML(note2  ))
    sbml.setNotesFromXML(note1)
    Console.println("Notes after setNotesFromXML is {}", sbml.notes)
    assert(sbml.notes.contains("APPEAR"))
  }
}

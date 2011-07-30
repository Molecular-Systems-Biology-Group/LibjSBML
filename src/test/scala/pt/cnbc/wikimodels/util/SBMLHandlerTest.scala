/*
 * SBMLHandler.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.util

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert._
import scala.xml.Node

class SBMLHandlerTest {
    var example1:String =
"""<?xml version="1.0" encoding="UTF-8"?>
<sbml xmlns="http://www.sbml.org/sbml/level2/version4" metaid="metaid_0000001" level="2" version="4">
  <model metaid="metaid_0000002" id="model_0000001" name="Izhikevich2004_SpikingNeurons_Class1Excitable">
    <notes>
      <body xmlns="http://www.w3.org/1999/xhtml">
        <p>This model is automatically generated from the Model BIOMD0000000141 by using <a href="http://sbml.org/Software/libSBML" target="_blank">libsbml</a>. According to the <a href="http://www.ebi.ac.uk/biomodels//legal.html">terms of use</a>, this generated model is not related with Model BIOMD0000000141 any more. <br/>To retrieve the curated model, please visit <a href="http://www.ebi.ac.uk/biomodels/">BioModels Database</a>.</p>
      </body>
    </notes>
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
    <listOfCompartments>
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
    </listOfCompartments>
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
            <ci> flag </ci>
            <cn> 0.075 </cn>
            <apply>
              <minus/>
              <csymbol encoding="text" definitionURL="http://www.sbml.org/sbml/symbols/time"> time </csymbol>
              <cn type="integer"> 30 </cn>
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
                  <cn> 0.04 </cn>
                  <apply>
                    <power/>
                    <ci> v </ci>
                    <cn type="integer"> 2 </cn>
                  </apply>
                </apply>
                <apply>
                  <times/>
                  <cn> 4.1 </cn>
                  <ci> v </ci>
                </apply>
                <cn type="integer"> 108 </cn>
              </apply>
              <ci> u </ci>
            </apply>
            <ci> i </ci>
          </apply>
        </math>
      </rateRule>
      <rateRule metaid="metaid_0000025" variable="u">
        <math xmlns="http://www.w3.org/1998/Math/MathML">
          <apply>
            <times/>
            <ci> a </ci>
            <apply>
              <minus/>
              <apply>
                <times/>
                <ci> b </ci>
                <ci> v </ci>
              </apply>
              <ci> u </ci>
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
              <ci> v </ci>
              <ci> Vthresh </ci>
            </apply>
          </math>
        </trigger>
        <listOfEventAssignments>
          <eventAssignment variable="v">
            <math xmlns="http://www.w3.org/1998/Math/MathML">
              <ci> c </ci>
            </math>
          </eventAssignment>
          <eventAssignment variable="u">
            <math xmlns="http://www.w3.org/1998/Math/MathML">
              <apply>
                <plus/>
                <ci> u </ci>
                <ci> d </ci>
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
              <csymbol encoding="text" definitionURL="http://www.sbml.org/sbml/symbols/time"> time </csymbol>
              <cn type="integer"> 30 </cn>
            </apply>
          </math>
        </trigger>
        <listOfEventAssignments>
          <eventAssignment variable="flag">
            <math xmlns="http://www.w3.org/1998/Math/MathML">
              <cn type="integer"> 1 </cn>
            </math>
          </eventAssignment>
        </listOfEventAssignments>
      </event>
    </listOfEvents>
  </model>
</sbml>
"""



    @Before
    def setUp: Unit = {
    }

    @After
    def tearDown: Unit = {
    }

    @Test
    def libSBMLLoadingTest = {
        //this test is not implementation independent but it is imporant for the correct functioning
        //of WikiModels.
        //This is the reason such an implementation dependen
        assertTrue( new SBMLHandler().validateSBML(example1, 2, 4))
    }


    
    @Test
    def genNotesFromHTMLTest = {
        val inNotes =
        <html>
            <body>
                <h1>Title</h1>
            </body>
        </html>
        val outNotes =
        <notes>
            <html>
                <body>
                    <h1>Title</h1>
                </body>
            </html>
                </notes>

        val spit = (new SBMLHandler().genNotesFromHTML(inNotes.toString))


        Console.println("Predicted notes = " + spit )
        Console.println("Predicted h1 = " + (outNotes \ "html" \ "body" \ "h1").text)
        Console.println("Obtained h1 = " + (spit \ "html" \ "body" \ "h1").text)

        assertEquals( (spit \ "html" \ "body" \ "h1").text,
                      (outNotes \ "html" \ "body" \ "h1").text)
        assertTrue(spit.label == "notes")
    }

    @Test
    def spitNullNotesTest =
        assertEquals(new SBMLHandler().genNotesFromHTML(null), null)

    @Test
    def spitEmptyStringNotesTest =
        assertEquals(new SBMLHandler().genNotesFromHTML("  "), null)

}


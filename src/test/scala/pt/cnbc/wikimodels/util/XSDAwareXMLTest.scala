/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.util

import javax.xml.validation.SchemaFactory
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert._
import scala.xml._
import pt.cnbc.wikimodels.sbmlVisitors.SBMLValidator
import java.io.{Reader, FileInputStream, File}
import xml.Source._

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 13-09-2011
 * Time: 4:55
 * To change this template use File | Settings | File Templates.
 */

class XSDAwareXMLTest {

  @Before
  def setUp: Unit = {
  }

  @After
  def tearDown: Unit = {
  }

  @Test
  def loadSchemaWithOriginalCode = {
    //from http://sean8223.blogspot.com/2009/09/xsd-validation-in-scala.html
    import javax.xml.parsers.SAXParser
    import javax.xml.parsers.SAXParserFactory
    import javax.xml.validation.Schema
    import javax.xml.validation.ValidatorHandler
    import org.xml.sax.XMLReader
    import scala.xml.parsing.NoBindingFactoryAdapter

    class SchemaAwareFactoryAdapter(schema: Schema) extends NoBindingFactoryAdapter {

      def loadXML(source: InputSource): Elem = {
        // create parser
        val parser: SAXParser = try {
          val f = SAXParserFactory.newInstance()
          f.setNamespaceAware(true)
          f.setFeature("http://xml.org/sax/features/namespace-prefixes", true)
          f.newSAXParser()
        } catch {
          case e: Exception =>
            Console.err.println("error: Unable to instantiate parser")
            throw e
        }

        val xr = parser.getXMLReader()
        val vh = schema.newValidatorHandler()
        vh.setContentHandler(this)
        xr.setContentHandler(vh)

        // parse file
        scopeStack.push(TopScope)
        xr.parse(source)
        scopeStack.pop
        return rootElem.asInstanceOf[Elem]
      }
    }

    // A schema can be loaded in like ...

    val sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val s = sf.newSchema(this.getClass.getClassLoader.getResource("sbml-l2v3.xsd"))


    // and whenever we would want to do something like:
    //val is = new InputSource(new File("foo.xml"))
    //val xml = XML.load(is)

    // instead we'll use our class:

    val is:InputSource = new InputSource(this.getClass.getClassLoader.getResourceAsStream("BIOMD0000000055_corrected.xml"))
    val xml = new SchemaAwareFactoryAdapter(s).loadXML(is)
  }

  @Test
  def validateCorrectSBMLModelL2V4 = {
    // A schema can be loaded in like ...
    val sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val s = sf.newSchema(this.getClass.getClassLoader.getResource("sbml-l2v3.xsd"))
    //Use our class:
    try{
      val xmlElem = XSDAwareXML(s).load(this.getClass.getClassLoader().getResource("BIOMD0000000055_corrected.xml"))
      assertNotNull(xmlElem)
    } catch {
      case e: org.xml.sax.SAXParseException =>{
        Console.println("XML_SCHMA_VALID ERROR at [" + e.getLineNumber + ","+ e.getColumnNumber + "]: " + e.getMessage);
        assertTrue(true)
        throw e
      }
    }
  }

  @Test
  def obviouslyNotSBML = {
    // A schema can be loaded in like ...
    val xmlStr = <stoopidxml>of course this isn't a model</stoopidxml>.toString
    val sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val s = sf.newSchema(new StreamSource(
      this.getClass.getClassLoader().getResourceAsStream("sbml-l2v4.xsd")))
    //Use our class:
    try {
      val xmlElem = XSDAwareXML(s).loadString(xmlStr)
      fail()
    } catch {
      case e: org.xml.sax.SAXParseException => {
        Console.println("XML_SCHMA_VALID ERROR at [" + e.getLineNumber + ","+ e.getColumnNumber + "]: " + e.getMessage);
        assertTrue(true)
      }
    }
  }

  @Test
  def checkgeneralXMLValidation = {
    val is =       this.getClass.getClassLoader.getResourceAsStream("BIOMD0000000070.xml")
    val sbmlString:String = scala.io.Source.fromInputStream(is).getLines().mkString("\n")
    assertEquals(Nil, SBMLValidator.sbmlSchemaValidation( 2 ,4,sbmlString) )
  }
}
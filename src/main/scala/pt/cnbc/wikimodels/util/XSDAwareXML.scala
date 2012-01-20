/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.util

import org.xml.sax.InputSource
import xml._
import factory.XMLLoader
import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory
import javax.xml.validation.Schema
import javax.xml.validation.ValidatorHandler
import org.xml.sax.XMLReader
import parsing.{FactoryAdapter, NoBindingFactoryAdapter}

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12-09-2011
 * Time: 22:05
 * Adapted from from http://sean8223.blogspot.com/2009/09/xsd-validation-in-scala.html
 */
class XSDAwareXML(schema:Schema) extends XMLLoader[scala.xml.Elem]{
  override def adapter: FactoryAdapter = new SchemaAwareFactoryAdapter(schema)

  override def parser: SAXParser = try {
    val f = SAXParserFactory.newInstance()
    f.setNamespaceAware(true)
    f.setFeature("http://xml.org/sax/features/namespace-prefixes", true)
    f.newSAXParser()
  } catch {
    case e: Exception =>
      Console.err.println("JAVA ERROR: Unable to instantiate parser")
      Console.err.println("Localized message: " + e.getLocalizedMessage)
      throw e
  }


  /**
   *
   */
  override def loadXML(source: InputSource, parse:SAXParser): Elem = {
    val newAdapter = adapter
    // create parser

    val xr = parser.getXMLReader()
    val vh = schema.newValidatorHandler()
    vh.setContentHandler(newAdapter)
    xr.setContentHandler(vh)

    // parse file
    newAdapter.scopeStack.push(TopScope)
    xr.parse(source)
    newAdapter.scopeStack.pop
    return newAdapter.rootElem.asInstanceOf[Elem]
  }
}

object XSDAwareXML{
  def apply(schema:Schema) = new XSDAwareXML(schema)
}


class SchemaAwareFactoryAdapter(schema:Schema) extends NoBindingFactoryAdapter{
}



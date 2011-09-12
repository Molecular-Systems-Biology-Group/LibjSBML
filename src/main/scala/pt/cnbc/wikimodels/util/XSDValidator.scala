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
import parsing.NoBindingFactoryAdapter


/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12-09-2011
 * Time: 22:05
 * Adapted from from http://sean8223.blogspot.com/2009/09/xsd-validation-in-scala.html
 */

class SchemaAwareFactoryAdapter(schema:Schema) extends NoBindingFactoryAdapter{
  def loadXML(source: InputSource): Elem = {
    // create parser
    val parser: SAXParser = try {
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



/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.sbmlVisitors

/*
* SBMLElemValidator.scala
*
* To change this template, choose Tools | Template Manager
* and open the template in the editor.
*  @author Alexandre Martins
*/

import dataVisitors.SBMLBeanVisitor
import javax.xml.validation.SchemaFactory
import javax.xml.XMLConstants
import javax.xml._
import org.sbml.libsbml.SBMLReader
import pt.cnbc.wikimodels.dataModel._
import javax.xml.transform.stream.StreamSource
import org.xml.sax.InputSource
import java.io.{FileInputStream, File}
import pt.cnbc.wikimodels.util.XSDAwareXML
import pt.cnbc.wikimodels.exceptions.ValidationDefaultCase._


object SBMLValidator {
  def LibSBMLValidation(level:Int, version:Int, sbml:String):List[String] = {
    //FIXME: This code could be simpler if avoiding calling toList was possible
    val reader = new SBMLReader()
    val sbmlDoc = reader.readSBMLFromString(sbml)

    val indexSeq = for (errorNum <- Range(0, sbmlDoc.getNumErrors().toInt) )
      yield {
        val error = sbmlDoc.getError(errorNum.toLong)
        error.getCategoryAsString + " " + error.getSeverityAsString + " at [" + error.getLine +
          ","+ error.getColumn + "]: " + error.getMessage
      }
    indexSeq.toList
  }

  //### General XML validation  ###

  /**
   * Checks errors related to general XML syntax
   * Michael Hucka et al.
   * Systems Biology Markup Language (SBML) Level 2:
   * Structures and Facilities for Model Definitions. (2008).
   * at <http://www.sbml.org/specifications/sbml-level-2/version-4/release-1/sbml-level-2-version-4-rel-1.pdf>
   * on page 141
   *
   * NOTE: it is assumed that <?xml version="1.0" encoding="UTF-8"?> was removed.
   * This must be valid UTF-8
   */
  def xmlSyntaxValidation(level:Int, version:Int, xmlStr:String):List[String] = {
    //XML syntax

    try{
      scala.xml.XML.loadString(xmlStr)
      Nil
    } catch {
      case e:  org.xml.sax.SAXParseException =>
        "XML ERROR at [" + e.getLineNumber + ","+ e.getColumnNumber + "]: " + e.getMessage :: Nil
      case e => exceptionHandling(e)
    }
  }

  def sbmlSchemaValidation(level:Int, version:Int, xmlStr:String):List[String] = {


    try{     //XML Schmea validation
      // A schema can be loaded in like ...

      val sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
      val s = sf.newSchema(this.getClass.getClassLoader.getResource("sbml-l"+level+"v"+version+".xsd"))
      //Use our class:

      val xmlElem = XSDAwareXML(s).loadString(xmlStr)
      Nil
    } catch {
      case e:org.xml.sax.SAXParseException =>{
        "SBML_SCHEMA ERROR:" + e.getLocalizedMessage + "\n" + e.printStackTrace :: Nil
      }
      case e => exceptionHandling(e)
    }
  }
}

/**
 * Validates according to SBML rules. If this methods accepts a model that
 */
object SBMLStrictValidator extends SBMLBeanVisitor[List[String]]{
  import pt.cnbc.wikimodels.exceptions.BadFormatException


  def visitModel(m: SBMLModel): List[String] = "Stub error" :: Nil

  def visitCompartment(c: Compartment): List[String] = "Stub error" :: Nil

  def visitConstraint(ct: Constraint): List[String] = "Stub error" :: Nil

  def visitFunctionDefinition(fd: FunctionDefinition): List[String] = "Stub error" :: Nil

  def visitKineticLaw(kl: KineticLaw): List[String] = "Stub error" :: Nil

  def visitModifierSpeciesReference(msr: ModifierSpeciesReference): List[String] = "Stub error" :: Nil

  def visitParameter(p: Parameter): List[String] = "Stub error" :: Nil

  def visitReaction(r: Reaction): List[String] = "Stub error" :: Nil

  def visitSpecies(s: Species): List[String] = "Stub error" :: Nil

  def visitSpeciesReference(sr: SpeciesReference): List[String] = "Stub error" :: Nil
}

/**
 * Detects obious errors in SBML that are not acceptable even in a half finished model.
 * This validator is very tolerant to inconsistencies and is meant to be used in SBML editor
 */
object SBMLLooseValidator extends SBMLBeanVisitor[List[String]]{
  def visitModel(m: SBMLModel): List[String] = "Stub error" :: Nil

  def visitCompartment(c: Compartment): List[String] = "Stub error" :: Nil

  def visitConstraint(ct: Constraint): List[String] = "Stub error" :: Nil

  def visitFunctionDefinition(fd: FunctionDefinition): List[String] = "Stub error" :: Nil

  def visitKineticLaw(kl: KineticLaw): List[String] = "Stub error" :: Nil

  def visitModifierSpeciesReference(msr: ModifierSpeciesReference): List[String] = "Stub error" :: Nil

  def visitParameter(p: Parameter): List[String] = "Stub error" :: Nil

  def visitReaction(r: Reaction): List[String] = "Stub error" :: Nil

  def visitSpecies(s: Species): List[String] = "Stub error" :: Nil

  def visitSpeciesReference(sr: SpeciesReference): List[String] = "Stub error" :: Nil
}

object SBMLValidatorForSimulation extends SBMLBeanVisitor[List[String]]{
  def visitModel(m: SBMLModel): List[String] = "Stub error" :: Nil

  def visitCompartment(c: Compartment): List[String] = "Stub error" :: Nil

  def visitConstraint(ct: Constraint): List[String] = "Stub error" :: Nil

  def visitFunctionDefinition(fd: FunctionDefinition): List[String] = "Stub error" :: Nil

  def visitKineticLaw(kl: KineticLaw): List[String] = "Stub error" :: Nil

  def visitModifierSpeciesReference(msr: ModifierSpeciesReference): List[String] = "Stub error" :: Nil

  def visitParameter(p: Parameter): List[String] = "Stub error" :: Nil

  def visitReaction(r: Reaction): List[String] = "Stub error" :: Nil

  def visitSpecies(s: Species): List[String] = "Stub error" :: Nil

  def visitSpeciesReference(sr: SpeciesReference): List[String] = "Stub error" :: Nil
}



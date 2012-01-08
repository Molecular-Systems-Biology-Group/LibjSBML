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

import java.lang.String
import java.io.{FileInputStream, File}
import javax.xml.validation.SchemaFactory
import javax.xml.XMLConstants
import javax.xml._
import org.sbml.libsbml.SBMLReader
import javax.xml.transform.stream.StreamSource
import org.xml.sax.InputSource
import scala.collection.JavaConversions._
import pt.cnbc.wikimodels.exceptions.ValidationDefaultCase._
import pt.cnbc.wikimodels.util.{SBMLParser, XMLParser, XSDAwareXML}
import dataVisitors.SBMLBeanVisitor
import pt.cnbc.wikimodels.dataModel._


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
 * Detects obvious errors in SBML that are not acceptable even in a half finished model.
 * This validator is very tolerant to inconsistencies and is meant to be used during SBML model editing
 */
object SBMLLooseValidator extends SBMLBeanVisitor[List[String]]{
  
  private var metaIdTab = Map.empty[String, Int]
  private var idTab = Map.empty[String, Element{def id:String}]


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

  protected def checkMetaId(e:Element) = {
    val ret = if(e.metaid == null || e.metaid.trim() == ""){
      "A " + e.sbmlType + " has no metaid" :: Nil
    } else if( metaIdTab contains e.metaid ){
      "There is already " + (metaIdTab get e.metaid).get + " elements with the same metaId in the model." :: Nil
      metaIdTab += (e.metaid -> (metaIdTab.getOrElse(e.metaid,0) + 1))
    } else Nil
  }

}

object SBMLValidatorForSimulation extends SBMLBeanVisitor[List[String]]{
  def visitModel(m: SBMLModel): List[String] = {
    XMLChecks.isValidIDType(m.metaid)  :::
    SBMLL2V4Checks.isValidSIdType(m.id) :::
    m.listOfFunctionDefinitions.map(visitFunctionDefinition(_) ).flatMap(i => i).toList :::
    m.listOfCompartments.map(visitCompartment(_) ).flatMap(i => i).toList :::
    m.listOfSpecies.map(visitSpecies(_) ).flatMap(i => i).toList :::
    m.listOfParameters.map(visitParameter(_) ).flatMap(i => i).toList :::
    m.listOfConstraints.map(visitConstraint(_) ).flatMap(i => i).toList :::
    m.listOfReactions.map(visitReaction(_) ).flatMap(i => i).toList ::: Nil
  }

  def visitCompartment(c: Compartment): List[String] = {
    XMLChecks.isValidIDType(c.metaid)  :::
    SBMLL2V4Checks.isValidSIdType(c.id) ;;;  Nil
  }

  def visitConstraint(ct: Constraint): List[String] = {
    XMLChecks.isValidIDType(ct.metaid)  :::
    SBMLL2V4Checks.isValidSIdType(ct.id) ;;;  Nil
  }

  def visitFunctionDefinition(fd: FunctionDefinition): List[String] = {
    XMLChecks.isValidIDType(fd.metaid)  :::
    SBMLL2V4Checks.isValidSIdType(fd.id) ;;;  Nil
  }

  def visitKineticLaw(kl: KineticLaw): List[String] = {
    XMLChecks.isValidIDType(kl.metaid)  :::  Nil
  }

  def visitModifierSpeciesReference(msr: ModifierSpeciesReference): List[String] = {
    XMLChecks.isValidIDType(msr.metaid)  :::
      SBMLL2V4Checks.isValidSIdType(msr.id) ;;;  Nil
  }

  def visitParameter(p: Parameter): List[String] = {
    XMLChecks.isValidIDType(p.metaid)  :::
    SBMLL2V4Checks.isValidSIdType(p.id) ;;;  Nil
  }

  def visitReaction(r: Reaction): List[String] = {
    XMLChecks.isValidIDType(r.metaid)  :::
    SBMLL2V4Checks.isValidSIdType(r.id) ;;;  Nil
  }

  def visitSpecies(s: Species): List[String] = {
    XMLChecks.isValidIDType(s.metaid)  :::
    SBMLL2V4Checks.isValidSIdType(s.id) ;;;  Nil
  }

  def visitSpeciesReference(sr: SpeciesReference): List[String] = {
    XMLChecks.isValidIDType(sr.metaid)  :::
    SBMLL2V4Checks.isValidSIdType(sr.id) ;;;  Nil
  }
}

object SBMLWMValidator extends SBMLBeanVisitor[List[String]] {
  def visitModel(m: SBMLModel): List[String] = null

  def visitCompartment(c: Compartment): List[String] = null

  def visitConstraint(ct: Constraint): List[String] = null

  def visitFunctionDefinition(fd: FunctionDefinition): List[String] = null

  def visitKineticLaw(kl: KineticLaw): List[String] = null

  def visitModifierSpeciesReference(msr: ModifierSpeciesReference): List[String] = null

  def visitParameter(p: Parameter): List[String] = null

  def visitReaction(r: Reaction): List[String] = null

  def visitSpecies(s: Species): List[String] = null

  def visitSpeciesReference(sr: SpeciesReference): List[String] = null
}

object XMLChecks extends XMLParser{

  /**
   * Checks if the string respects XML 1.0 ID type specification
   * This is the ID type of section 3.1.6 of the SBML specification
   */
  def isValidIDType(metaid:String) = {
    parseAll(Name, metaid) match {
      case Success(_,_) => Nil
      case Failure(_,_) => "" + metaid + " is not of type Id has defined in XML 1.0 specification" :: Nil
      case _ => {
        "Something is wrong with XML ID type validation " :: Nil
      }
    }
  }
}

object SBMLL2V4Checks extends SBMLParser{

  /**
  * Checks if the string respects the SID type of section 3.1.6 of the SBML specification
   */
  def isValidSIdType(id:String) = {
    parseAll( SId, id ) match {
      case Success(_,_) => Nil
      case Failure(_,_) => "" + id + " is not of type SId has defined in section 3.1.6 of the SBML specification" :: Nil
      case _ => {
        "Something is wrong with SBML SID type validation" :: Nil
      }
    }
  }
}



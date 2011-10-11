/*
 * SBMLElemValidator.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataVisitors

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

class SBMLStrictValidator(val elem:Element)  {
  import pt.cnbc.wikimodels.exceptions.BadFormatException

  /**
   * Validates according to SBML rules. If this methods accepts a model that
   */
  def validateSBMLStrictly():Boolean = elem match {
    case cp:Compartment => false
    case ct:Constraint => false
    case fd:FunctionDefinition => false
    case kl:KineticLaw => false
    case msr:ModifierSpeciesReference => false
    case p:Parameter => false
    case r:Reaction => false
    case m:SBMLModel => false
    case s:Species => false
    case sr:SpeciesReference => false
    case _ => throw new BadFormatException("Unknow element inside SBMLModel when generating SBML Lvel 2 Version 4")
  }

  /**
   * Detects obious errors in SBML that are not acceptable even in a half finished model.
   * This validator is very tolerant to inconsistencies and is meant to be used in SBML editor
   */
  def validateSBMLloosely():Boolean ={
          val o:Option[Int] = Some(1)
    false
  }

  def validateSBMLForSimulation:Boolean = elem match {
    case cp:Compartment => false
    case ct:Constraint => false
    case fd:FunctionDefinition => false
    case kl:KineticLaw => false
    case msr:ModifierSpeciesReference => false
    case p:Parameter => false
    case r:Reaction => false
    case m:SBMLModel => false
    case s:Species => false
    case sr:SpeciesReference => false
    case _ => throw new BadFormatException("Unknow element inside SBMLModel when generating SBML Lvel 2 Version 4")
  }
}

/**
 * class that checks if all metaids of a an element are valid.
 * The rules are:
 *  - no metaId should be repeated across the entire knowledgebase
 *  - if metaId does not exist for a certain element it should be created from the id
 */
class SBMLElemCheckMetaId(val elem: Element) extends SBMLBeanVisitor[Boolean] {
  import pt.cnbc.wikimodels.exceptions.BadFormatException

  def visit(e:Element):Boolean = e match {
    case cp:Compartment => visitCompartment(cp)
    case ct:Constraint => visitConstraint(ct)
    case fd:FunctionDefinition => visitFunctionDefinition(fd)
    case kl:KineticLaw => visitKineticLaw(kl)
    case msr:ModifierSpeciesReference => visitModifierSpeciesReference(msr)
    case p:Parameter => visitParameter(p)
    case r:Reaction => visitReaction(r)
    case m:SBMLModel => visitModel(m)
    case s:Species => visitSpecies(s)
    case sr:SpeciesReference => visitSpeciesReference(sr)
    case _ => throw new BadFormatException("Unknow element inside SBMLModel when checking for metaids")
  }

  def visitModel(m: SBMLModel): Boolean = false

  def visitCompartment(c: Compartment): Boolean = false

  def visitConstraint(ct: Constraint): Boolean = false

  def visitFunctionDefinition(fd: FunctionDefinition): Boolean = false

  def visitKineticLaw(kl: KineticLaw): Boolean = false

  def visitModifierSpeciesReference(msr: ModifierSpeciesReference): Boolean = false

  def visitParameter(p: Parameter): Boolean = false

  def visitReaction(r: Reaction): Boolean = false

  def visitSpecies(s: Species): Boolean = false

  def visitSpeciesReference(sr: SpeciesReference): Boolean = false
}

/**
 * class that checks if all metaids of a an element are valid.
 * The rules are:
 *  - no metaId should be repeated across the entire knowledgebase
 *  - if metaId does not exist for a certain element it should be created from the id
 */
class SBMLElemCheckId(val elem:Element) extends SBMLBeanVisitor[Boolean] {
  import pt.cnbc.wikimodels.exceptions.BadFormatException

  def visit(e:Element):Boolean = e match {
    case cp:Compartment => visitCompartment(cp)
    case ct:Constraint => visitConstraint(ct)
    case fd:FunctionDefinition => visitFunctionDefinition(fd)
    case kl:KineticLaw => visitKineticLaw(kl)
    case msr:ModifierSpeciesReference => visitModifierSpeciesReference(msr)
    case p:Parameter => visitParameter(p)
    case r:Reaction => visitReaction(r)
    case m:SBMLModel => visitModel(m)
    case s:Species => visitSpecies(s)
    case sr:SpeciesReference => visitSpeciesReference(sr)
    case _ => throw new BadFormatException("Unknow element inside SBMLModel when checking for metaids")
  }


  def visitModel(m: SBMLModel): Boolean = false

  def visitCompartment(m: Compartment): Boolean = false

  def visitConstraint(m: Constraint): Boolean = false

  def visitFunctionDefinition(m: FunctionDefinition): Boolean = false

  def visitKineticLaw(m: KineticLaw): Boolean = false

  def visitModifierSpeciesReference(m: ModifierSpeciesReference): Boolean = false

  def visitParameter(m: Parameter): Boolean = false

  def visitReaction(m: Reaction): Boolean = false

  def visitSpecies(m: Species): Boolean = false

  def visitSpeciesReference(m: SpeciesReference): Boolean = false
}

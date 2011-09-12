/*
 * SBMLElemValidator.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataVisitors

import pt.cnbc.wikimodels.dataModel._
import org.sbml.libsbml.SBMLReader
import collection._


class SBMLL2V4Validator {

  /**
   * Checks errors related to general XML syntax
   */
  def generalXMLValidation(xml:String):List[String] = {
    try{
      scala.xml.XML.loadString(xml)
      Nil
    } catch {
      case e:  org.xml.sax.SAXParseException =>
        "XML ERROR at [" + e.getLineNumber + ","+ e.getColumnNumber + "]: " + e.getMessage :: Nil
      case e =>
        """An unexpected error occured. Please report this as a bug with the following stchtrace:" +
        """ + e.printStackTrace() :: Nil
    }
  }

  def LibSBMLValidation(level:String, version:String, sbml:String):List[String] = {
    //FIXME: This code could be simpler if avoiding calling toList was possible
    val reader = new SBMLReader()
    val sbmlDoc = reader.readSBMLFromString(sbml)
    val indexSeqWithErrors:Traversable[String] =
      for (errorNum <- Range(0, sbmlDoc.getNumErrors().toInt) )
        yield {
          val error = sbmlDoc.getError(errorNum.toLong)
          error.getCategoryAsString + " " + error.getSeverityAsString + " at [" + error.getLine +
            ","+ error.getColumn + "]: " + error.getMessage
        }
    indexSeqWithErrors.toList
  }
}




class SBMLElemValidator(val elem:Element) {
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


object SBMLElemValidator{

  implicit def SBMLToSBMLValidator(elem:Element ) = new SBMLElemValidator(elem)
}


/**
 * class that checks if all metaids of a an element are valid.
 * The rules are:
 *  - no metaId should be repeated across the entire knowledgebase
 *  - if metaId does not exist for a certain element it should be created from the id
 */
class SBMLElemCheckMetaId(val elem: Element) {
  import pt.cnbc.wikimodels.exceptions.BadFormatException

  def checkMetaId = {
    elem match {
      case cp: Compartment => cp
      case ct: Constraint => true
      case fd: FunctionDefinition => true
      case kl: KineticLaw => true
      case msr: ModifierSpeciesReference => true
      case p: Parameter => true
      case r: Reaction => true
      case m: SBMLModel => true
      case s: Species => true
      case sr: SpeciesReference => true
      case _ => {
        throw new BadFormatException("Unknow element inside SBMLModel when checking for metaids")
        elem
      }
    }
  }
}

/**
 * class that checks if all metaids of a an element are valid.
 * The rules are:
 *  - no metaId should be repeated across the entire knowledgebase
 *  - if metaId does not exist for a certain element it should be created from the id
 */
class SBMLElemCheckId(val elem:Element) {
  import pt.cnbc.wikimodels.exceptions.BadFormatException

  def checkId = {
    elem match  {
      case cp:Compartment => cp
      case ct:Constraint => true
      case fd:FunctionDefinition => true
      case kl:KineticLaw => true
      case msr:ModifierSpeciesReference => true
      case p:Parameter => true
      case r:Reaction => true
      case m:SBMLModel => true
      case s:Species => true
      case sr:SpeciesReference => true
      case _ => {
          throw new BadFormatException("Unknow element inside SBMLModel when checking for metaids")
          elem
        }
    }


  }
}

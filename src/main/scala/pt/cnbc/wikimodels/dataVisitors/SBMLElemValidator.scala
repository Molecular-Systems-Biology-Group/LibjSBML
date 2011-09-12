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
import pt.cnbc.wikimodels.util.SchemaAwareFactoryAdapter
import java.io.{FileInputStream, File}


class SBMLL2V4Validator {
  def LibSBMLValidation(level:String, version:String, sbml:String):List[String] = {
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
  def generalXMLValidation(level:String, version:String, xml:String):List[String] = {
    //XML syntax

    val xmlSyntaxErrors:Traversable[String] =try{
      scala.xml.XML.loadString(xml)
      Nil
    } catch {
      case e:  org.xml.sax.SAXParseException =>
        "XML ERROR at [" + e.getLineNumber + ","+ e.getColumnNumber + "]: " + e.getMessage :: Nil
      case e =>
        """UNEXPECTED ERROR: Please report this as a bug with the following stacktrace:" +
        """ + e.printStackTrace() :: Nil
    }


    val schemaErrors = try{     //XML Schmea validation
      // A schema can be loaded in like ...

      val sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
      val s = sf.newSchema(new StreamSource(new File("foo.xsd")))
      //Use our class:

      val is = new InputSource(new FileInputStream("foo.xml"))
      val xml = new SchemaAwareFactoryAdapter(s).loadXML(is)
      Nil
    } catch {
      case e => "SBML_SCHEMA ERROR:" + e.getLocalizedMessage + "\n" + e.printStackTrace :: Nil
    }

    if(xmlSyntaxErrors.size > 0)
      xmlSyntaxErrors.toList
    else
      schemaErrors
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

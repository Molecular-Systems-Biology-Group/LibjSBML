/*
 * SBMLElemValidator.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataVisitors

import pt.cnbc.wikimodels.dataModel._


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

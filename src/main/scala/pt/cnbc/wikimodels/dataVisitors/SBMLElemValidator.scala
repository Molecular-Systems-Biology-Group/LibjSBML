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
   * Validates according to SBML rules and without
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

  def validateSBMLloosely():Boolean ={
          val o:Option[Int] = Some(1)
    false
  }
}


object SBMLElemValidator{

  implicit def SBMLToSBMLValidator(elem:Element ) = new SBMLElemValidator(elem)
}

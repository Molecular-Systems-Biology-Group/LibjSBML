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

  def validate():Boolean = elem match {
    case cp:Compartment => true
    case ct:Constraint => true
    case fd:FunctionDefinition => true
    case kl:KineticLaw => true
    case msr:ModifierSpeciesReference => true
    case p:Parameter => true
    case r:Reaction => true
    case m:SBMLModel => true
    case s:Species => true
    case sr:SpeciesReference => true
    case _ => throw new BadFormatException("Unknow element inside SBMLModel when generating SBML Lvel 2 Version 4")
  }
}

object SBMLElemValidator{
  implicit def SBMLToSBMLValidator(elem:Element ) = new SBMLElemValidator(elem)
}

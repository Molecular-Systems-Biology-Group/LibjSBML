/*
 * __NAME__.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataVisitors

import pt.cnbc.wikimodels.dataModel._
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
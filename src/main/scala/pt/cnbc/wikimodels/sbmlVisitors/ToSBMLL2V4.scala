
package pt.cnbc.wikimodels

import scala.xml.Elem

import pt.cnbc.wikimodels.dataModel._

/*
 * ToSBMLL2V4.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */
class ToSBMLL2V4(val elem:Element) {
  def convertToSBMLL2V4():Elem = {
    elem match {
      case cp:Compartment => cp.toXML
      case ct:Constraint => ct.toXML
      case fd:FunctionDefinition => fd.toXML
      case kl:KineticLaw => kl.toXML
      case msr:ModifierSpeciesReference => msr.toXML
      case p:Parameter => p.toXML
      case r:Reaction => r.toXML
      case m:SBMLModel => m.toXML
      case s:Species => s.toXML
      case sr:SpeciesReference => sr.toXML
      case _ => {null}
    }
  }
}

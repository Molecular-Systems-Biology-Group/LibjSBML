/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.sbmlVisitors

import pt.cnbc.wikimodels.dataModel._
import pt.cnbc.wikimodels.exceptions.BadFormatException

/**
 * TODO: Please document.
 * @author: alex
 * Date: 22-12-2011
 * Time: 19:33
 */
trait SBMLBeanChanger {
  //TODO replace _.toXML with this changeor in the code
  def change(e: Element): Element with Product = e match {
    case m:SBMLModel => changeModel(m)
    case cp:Compartment => changeCompartment(cp)
    case ct:Constraint => changeConstraint(ct)
    case fd:FunctionDefinition => changeFunctionDefinition(fd)
    case kl:KineticLaw => changeKineticLaw(kl)
    case msr:ModifierSpeciesReference => changeModifierSpeciesReference(msr)
    case p:Parameter => changeParameter(p)
    case r:Reaction => changeReaction(r)
    case s:Species => changeSpecies(s)
    case sr:SpeciesReference => changeSpeciesReference(sr)
    case _ => throwNewBadFormatException
  }

  /**
   * this
   */
  protected def throwNewBadFormatException = throw new BadFormatException("Unknow element inside SBMLModel when generating SBML Lvel 2 Version 4")

  def changeModel(m: SBMLModel): SBMLModel

  def changeCompartment(c: Compartment): Compartment

  def changeConstraint(ct: Constraint): Constraint

  def changeFunctionDefinition(fd: FunctionDefinition): FunctionDefinition

  def changeKineticLaw(kl: KineticLaw): KineticLaw

  def changeModifierSpeciesReference(msr: ModifierSpeciesReference): ModifierSpeciesReference

  def changeParameter(p: Parameter): Parameter

  def changeReaction(r: Reaction): Reaction

  def changeSpecies(s: Species): Species

  def changeSpeciesReference(sr: SpeciesReference): SpeciesReference
}
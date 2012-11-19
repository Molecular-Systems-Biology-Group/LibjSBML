/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.sbmlVisitors.dataVisitors

/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

import pt.cnbc.wikimodels.dataModel._
import xml.Elem
import pt.cnbc.wikimodels.exceptions.BadFormatException

/**
 * Visitor for SBML instances.
 * This visitor will not make any changes to the instances that it visits. Check SBMLBeanChanger if that is what you want.
 * User: Alexandre Martins
 * Date: 07-10-2011
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
trait SBMLBeanVisitor[T]{
  //TODO replace _.toXML with this visitor in the code

  def visit(e: Element) = e match {
    case m:SBMLModel => visitModel(m)
    case cp:Compartment => visitCompartment(cp)
    case ct:Constraint => visitConstraint(ct)
    case fd:FunctionDefinition => visitFunctionDefinition(fd)
    case kl:KineticLaw => visitKineticLaw(kl)
    case msr:ModifierSpeciesReference => visitModifierSpeciesReference(msr)
    case p:Parameter => visitParameter(p)
    case r:Reaction => visitReaction(r)
    case s:Species => visitSpecies(s)
    case sr:SpeciesReference => visitSpeciesReference(sr)
    case _ => throw new BadFormatException("Unknow element inside SBMLModel when generating SBML Lvel 2 Version 4")
  }

  protected def visitModel(m: SBMLModel): T

  protected def visitCompartment(c: Compartment): T

  protected def visitConstraint(ct: Constraint): T

  protected def visitFunctionDefinition(fd: FunctionDefinition): T

  protected def visitKineticLaw(kl: KineticLaw): T

  protected def visitModifierSpeciesReference(msr: ModifierSpeciesReference): T

  protected def visitParameter(p: Parameter): T

  protected def visitReaction(r: Reaction): T

  protected def visitSpecies(s: Species): T

  protected def visitSpeciesReference(sr: SpeciesReference): T
}
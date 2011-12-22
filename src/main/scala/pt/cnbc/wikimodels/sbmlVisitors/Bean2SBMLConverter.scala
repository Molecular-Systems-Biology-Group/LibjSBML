package pt.cnbc.wikimodels.sbmlVisitors.dataVisitors

/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

import pt.cnbc.wikimodels.dataModel._
import xml.Elem

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 07-10-2011
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
class Bean2SBMLConverter{
  //TODO replace _.toXML with this visitor in the code

  def visit(e: Element): Elem = null

  def visitModel(m: SBMLModel): Elem = null

  def visitCompartment(c: Compartment): Elem = null

  def visitConstraint(ct: Constraint): Elem = null

  def visitFunctionDefinition(fd: FunctionDefinition): Elem = null

  def visitKineticLaw(kl: KineticLaw): Elem = null

  def visitModifierSpeciesReference(msr: ModifierSpeciesReference): Elem = null

  def visitParameter(p: Parameter): Elem = null

  def visitReaction(r: Reaction): Elem = null

  def visitSpecies(s: Species): Elem = null

  def visitSpeciesReference(sr: SpeciesReference): Elem = null
}
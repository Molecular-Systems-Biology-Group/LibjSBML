/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.dataVisitors

import pt.cnbc.wikimodels.dataModel._

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 07-10-2011
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */

trait SBMLBeanVisitor[T] {
  def visit(e:Element):T
  def visitModel(m:SBMLModel):T
  def visitCompartment(c:Compartment):T
  def visitConstraint(ct:Constraint):T
  def visitFunctionDefinition(fd:FunctionDefinition):T
  def visitKineticLaw(kl:KineticLaw) :T
  def visitModifierSpeciesReference(msr:ModifierSpeciesReference):T
  def visitParameter(p:Parameter):T
  def visitReaction(r:Reaction):T
  def visitSpecies(s:Species):T
  def visitSpeciesReference(sr:SpeciesReference):T
}
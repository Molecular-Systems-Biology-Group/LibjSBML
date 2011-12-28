/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.wmVisitors

/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

import pt.cnbc.wikimodels.sbmlVisitors.SBMLBeanChanger
import pt.cnbc.wikimodels.dataModel._
import pt.cnbc.wikimodels.sbmlVisitors.dataVisitors.SBMLBeanVisitor

/**
 * class that checks if all metaids of a an element are valid.
 * The rules are:
 *  - no metaId should be repeated across the entire knowledgebase
 *  - if metaId does not exist for a certain element it should be created from the id
 * @author: Alexandre Martins
 * Date: 22-12-2011
 * Time: 19:44
 */
object SBMLElemCheckMetaId extends SBMLBeanChanger {
  def changeModel(m: SBMLModel): SBMLModel = null

  def changeCompartment(c: Compartment): Compartment = null

  def changeConstraint(ct: Constraint): Constraint = null

  def changeFunctionDefinition(fd: FunctionDefinition): FunctionDefinition = null

  def changeKineticLaw(kl: KineticLaw): KineticLaw = null

  def changeModifierSpeciesReference(msr: ModifierSpeciesReference): ModifierSpeciesReference = null

  def changeParameter(p: Parameter): Parameter = null

  def changeReaction(r: Reaction): Reaction = null

  def changeSpecies(s: Species): Species = null

  def changeSpeciesReference(sr: SpeciesReference): SpeciesReference = null
}

object TempValidations extends SBMLBeanVisitor[List[String]]{

  val validations = List(
    (e:Element) => {
      if( e.metaid.contains(" ")) "Metaid in " + e.sbmlType + " can't contain spaces" :: Nil else Nil
    }
  )

  def visitModel(m: SBMLModel): List[String] = {
     validations.map(f => f(m)).fold(:::)

  }

  def visitCompartment(c: Compartment): List[String] = null

  def visitConstraint(ct: Constraint): List[String] = null

  def visitFunctionDefinition(fd: FunctionDefinition): List[String] = null

  def visitKineticLaw(kl: KineticLaw): List[String] = null

  def visitModifierSpeciesReference(msr: ModifierSpeciesReference): List[String] = null

  def visitParameter(p: Parameter): List[String] = null

  def visitReaction(r: Reaction): List[String] = null

  def visitSpecies(s: Species): List[String] = null

  def visitSpeciesReference(sr: SpeciesReference): List[String] = null
}
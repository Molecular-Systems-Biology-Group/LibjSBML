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

  var validations:List[(Element) => List[String]] = {
    //Checks if the metaid contains spaces
    ((e:Element) => {
      if( e.metaid.contains(" ")) "Metaid in " + e.sbmlType + " can't contain spaces" :: Nil else Nil
    }) :: Nil
  }


  def visitModel(m: SBMLModel): List[String] = {
     validations.map(f => f(m)).fold(Nil)( (x,y) => x ::: y)
  }

  def visitCompartment(c: Compartment): List[String] =  {
    validations.map(f => f(c)).fold(Nil)( (x,y) => x ::: y)
  }

  def visitConstraint(ct: Constraint): List[String] =  {
    validations.map(f => f(ct)).fold(Nil)( (x,y) => x ::: y)
  }

  def visitFunctionDefinition(fd: FunctionDefinition): List[String] = {
    validations.map(f => f(fd)).fold(Nil)( (x,y) => x ::: y)
  }

  def visitKineticLaw(kl: KineticLaw): List[String] =  {
    validations.map(f => f(kl)).fold(Nil)( (x,y) => x ::: y)
  }

  def visitModifierSpeciesReference(msr: ModifierSpeciesReference): List[String] = {
    validations.map(f => f(msr)).fold(Nil)( (x,y) => x ::: y)
  }

  def visitParameter(p: Parameter): List[String] = {
    validations.map(f => f(p)).fold(Nil)( (x,y) => x ::: y)
  }
  def visitReaction(r: Reaction): List[String] = {
    validations.map(f => f(r)).fold(Nil)( (x,y) => x ::: y)
  }

  def visitSpecies(s: Species): List[String] = {
    validations.map(f => f(s)).fold(Nil)( (x,y) => x ::: y)
  }

  def visitSpeciesReference(sr: SpeciesReference): List[String] = {
    validations.map(f => f(sr)).fold(Nil)( (x,y) => x ::: y)
  }
}
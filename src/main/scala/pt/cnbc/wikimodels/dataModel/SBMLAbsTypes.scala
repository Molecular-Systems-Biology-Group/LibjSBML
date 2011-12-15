/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.dataModel

/**
 * TODO: Please document.
 * @author: Alexandre Martins
 * Date: 12-12-2011
 * Time: 1:00
 */

trait SBMLAbsTypes {
  // TODO changing the concrete types to abstract ones makes the library more extendable
  //yet,it is a diffucult
  // Thse abstract types can be overriden in subclasses of SBMLModel
  type BASE <: Element
  type MOD <: SBMLModel
  type FUN <: FunctionDefinition
  type COMP <: Compartment
  type SP <: Species
  type PAR <: Parameter
  type CONST <: Constraint
  type REACT <: Reaction
}
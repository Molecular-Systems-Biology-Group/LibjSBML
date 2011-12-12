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
  type Mod <: SBMLModel
  type FuncDef <: FunctionDefinition
  type Comp <: Compartment
  type Spec <: Species
  type Param <: Parameter
  type Constr <: Constraint
  type React <: Reaction
}
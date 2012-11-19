/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.util

import util.parsing.combinator.{PackratParsers, RegexParsers}


/** Defines types specific to SBML Level 2 version 4
 *  @author Alexandre Martins
 *  Date: 08-01-2012
 *  Time: 5:50 */
class SBMLParser extends RegexParsers with PackratParsers {
  lazy val  letter = "[a-zA-Z]".r

  lazy val digit   = "[0-9]".r
  
  lazy val idChar  = letter | digit | "_"
  
  lazy val SId  = ( letter | "_" )~rep(idChar)

  // lazy val UnitSId =
}
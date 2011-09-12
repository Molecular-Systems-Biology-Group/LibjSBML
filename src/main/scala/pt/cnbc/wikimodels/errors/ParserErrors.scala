/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.errors

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12-09-2011
 * Time: 23:52
 * To change this template use File | Settings | File Templates.
 */

object ParserErrorCategory extends Enumeration {
  type ParserErrorCategory = Value
  val UNEXPECTED, JAVA, XML, SBML_SCHEMA  = Value
}
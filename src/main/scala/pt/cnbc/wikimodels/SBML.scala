/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels

package sbml

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 28-08-2011
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */

package object constants {
  val x =2
}

package namesspaces{

  /**
   * SBML's own namespace. This is the format used as the default namespace in an S
   */
  package object versions{
    val l2v1 = "http://www.sbml.org/sbml/level2/version1"
    val l2v2 = "http://www.sbml.org/sbml/level2/version2"
    val l2v3 = "http://www.sbml.org/sbml/level2/version3"
    val l2v4 = "http://www.sbml.org/sbml/level2/version4"
  }

  /**
   * Namespace for Math representation as MathML
   */
  package object xmlns{
    val MathML = "http://www.w3.org/1998/Math/MathML"
    val XHTML = "http://www.w3.org/1999/xhtml"
  }

  package object annotation {
    //namespaces for annotations
    val dc = "http://purl.org/dc/elements/1.1/"
    val vcard = "http://www.w3.org/2001/vcard-rdf/3.0#"
    val rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    val bqbiol = "http://biomodels.net/biology-qualifiers/"
    val dcterms = "http://purl.org/dc/terms/"
    val bqmodel =  "http://biomodels.net/model-qualifiers/"
  }
}
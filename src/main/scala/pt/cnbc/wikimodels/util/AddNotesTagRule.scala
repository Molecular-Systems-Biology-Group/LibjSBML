/*
 * AddNotesTag.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.util

import scala.xml.transform.RewriteRule
import pt.cnbc.wikimodels.exceptions.BadFormatException
import xml._


/**
 * Class based upon scala.xml.transform.RewriteRule which is meant to
 * add <notes></notes> tag in the notes section of an SBML model
 * and other transformations that turn it into valid xhtnl for thet
 * section according to SBML Specification l3vel 2 version 4
 */
class AddNotesTagRule extends RewriteRule{
  //TODO make this class usable and use it. It should replace methods in SBMLHandler

  /** a name for this rewrite rule */
  override val name = this.toString()
  override def transform(ns: Seq[Node]): Seq[Node] = {
    <notes>{ns.map(i => {
            new Elem(null,
                     i.label,
                     scala.xml.Null,
                     NamespaceBinding(null, "http://www.w3c.org/1999/xhtml",TopScope),
                     i.child:_*)
          } ) }</notes>;
  }
}

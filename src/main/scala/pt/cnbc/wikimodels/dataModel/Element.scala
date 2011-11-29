/*
 * Element.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import scala.xml.NodeSeq
import scala.xml.Elem
import java.util.Date

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty

import scala.reflect.BeanInfo
import scala.xml.Group

import pt.cnbc.wikimodels.util.SBMLHandler
import pt.cnbc.wikimodels.exceptions.BadFormatException

/**
 * This can be considered the equivalent of SBase in SBML 2.4 specification
 * Yet there is no sboTerm as up now
 */
@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
abstract class Element extends DataModel{
  //TODO refactor from "Element" to "SBase". This takes a bit of time since the sbml ontology must also be modified and the KB rebuilt and tested.
  val sbmlType = "SBase"

  @Id
  @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#metaid")
  var metaid:String = null

  //TODO: check if these two lines have to be repeated in any descendant of this class. Annotations might not be inherited
  @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#notes")
  var notes:String = null

  def setNotesFromXML(notes:NodeSeq) = {
      this.notes = Group(SBMLHandler.addNamespaceToXHTML(notes)).toString()
  }

  override def toXML:Elem =
  throw new pt.cnbc.wikimodels.exceptions
  .NotImplementedException("toXML in class " + this.getClass +
                           "is not implemente")
  def theId:String = null
  def theName:String = null
}

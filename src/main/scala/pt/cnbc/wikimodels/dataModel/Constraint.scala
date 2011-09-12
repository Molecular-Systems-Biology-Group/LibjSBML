/*
 * Constraint.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import scala.reflect.BeanInfo
import scala.xml.Elem
import scala.xml.NodeSeq
import scala.xml.XML

import thewebsemantic.Namespace
import pt.cnbc.wikimodels.util.SBMLHandler
import pt.cnbc.wikimodels.exceptions.BadFormatException


@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class Constraint() extends Element {
  override final val sbmlType = "Constraint"
  var id: String = null
  var name: String = null
  var math: String = null
  var message: String = null

  def this(metaid: String,
           notes: NodeSeq,
           id: String,
           name: String,
           math: NodeSeq,
           message: NodeSeq) = {
    this ()
    this.metaid = metaid
    this.setNotesFromXML(notes)
    this.id = id
    this.name = name
    this.math = math.toString
    this.message = message.toString
    SBMLHandler.idExistsAndIsValid(this.id)
  }

  /**
   * math element is mandatory
   */
  def this(xmlConstraint: Elem) = {
    this (SBMLHandler.toStringOrNull((xmlConstraint \ "@metaid").text),
      SBMLHandler.checkCurrentLabelForNotes(xmlConstraint),
      SBMLHandler.toStringOrNull((xmlConstraint \ "@id").text),
      SBMLHandler.toStringOrNull((xmlConstraint \ "@name").text),
      (xmlConstraint \ "math"),
      SBMLHandler.checkCurrentLabelForMessage(xmlConstraint))
  }

  override def toXML: Elem = {
    <constraint metaid={metaid} id={id} name={name}>
      <!--order is important according to SBML Specifications-->
      {SBMLHandler.genNotesFromHTML(notes)}
      {XML.loadString(this.math)}
      {SBMLHandler.genMessageFromHTML(message)}
    </constraint>
  }

  override def theId = this.id

  override def theName = this.name
}

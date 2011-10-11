/*
 * ModifierSpeciesReference.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import scala.xml.Group
import scala.xml.Node
import scala.xml.NodeSeq

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty
import scala.reflect.BeanInfo
import scala.xml.Elem

import pt.cnbc.wikimodels.util.SBMLHandler
import pt.cnbc.wikimodels.exceptions.BadFormatException

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class ModifierSpeciesReference() extends Element {
  override final val sbmlType = "ModifierSpeciesReference"
  var id: String = null
  var name: String = null

  var species:String = null

  def this(metaid: String,
           notes: NodeSeq,
           id: String,
           name: String,
           species: String) = {
    this ()
    this.metaid = metaid
    this.setNotesFromXML(notes)
    this.id = id
    this.name = name
    this.species = species
    SBMLHandler.idExistsAndIsValid(this.id)
    if(this.species == null||this.species.trim == "")
      throw new BadFormatException("A Species id must be included in the creation of a new Speciesreference")
  }

  override def toXML: Elem = {
    <modifierSpeciesReference metaid={metaid} id={id} name={name}
                              species={species}>
      {SBMLHandler.genNotesFromHTML(notes)}
    </modifierSpeciesReference>
  }

  override def theId = this.id

  override def theName = this.name
}

/*
 * Reaction.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import scala.collection.JavaConversions._
import scala.xml.NodeSeq

import thewebsemantic.Namespace
import thewebsemantic.RdfProperty
import scala.reflect.BeanInfo
import scala.xml.Elem

import pt.cnbc.wikimodels.util.SBMLHandler

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class Reaction() extends Element{
  override final val sbmlType = "Reaction"
  var id:String = null
  var name:String = null
  var reversible:Boolean = true
  var fast:Boolean = false

  //listOf definitions
  @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#hasReactant")
  var listOfReactants:java.util.Collection[SpeciesReference] = Set.empty[SpeciesReference]
  @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#hasProduct")
  var listOfProducts:java.util.Collection[SpeciesReference] = Set.empty[SpeciesReference]
  @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#hasModifier")
  var listOfModifiers:java.util.Collection[ModifierSpeciesReference] = Set.empty[ModifierSpeciesReference]
  @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#hasOneKineticLaw")
  var kineticLaw:KineticLaw = null //optional

  def this(metaid:String,
           notes:NodeSeq,
           id:String,
           name:String,
           reversible:Boolean,
           fast:Boolean) = {
    this()
    this.metaid = metaid
    this.setNotesFromXML(notes)
    this.id = id
    this.name = name
    this.reversible = reversible
    this.fast = fast
    SBMLHandler.idExistsAndIsValid(this.id)
  }

  override def toXML:Elem = {
    <reaction metaid={metaid} id={id} name={name} >
      {SBMLHandler.genNotesFromHTML(notes)}
      {if(listOfReactants != null && listOfReactants.size != 0)
        <listOfReactants>
          {listOfReactants.map(i => i.toXML)}
        </listOfReactants> else scala.xml.Null
      }
      {if(listOfProducts != null && listOfProducts.size != 0)
        <listOfProducts>
          {listOfProducts.map(i => i.toXML)}
        </listOfProducts> else scala.xml.Null
      }
      {if(listOfModifiers != null && listOfModifiers.size != 0)
        <listOfModifiers>
          {listOfModifiers.map(i => i.toXML)}
        </listOfModifiers> else scala.xml.Null
      }
      {if(this.kineticLaw != null)
          this.kineticLaw.toXML
      else
        null.asInstanceOf[Elem]}
    </reaction>
  }
  override def theId = this.id
  override def theName = this.name
}

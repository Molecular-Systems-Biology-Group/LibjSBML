/*
 * Compartment.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import scala.reflect.BeanInfo
import scala.xml.Elem
import scala.xml.Group
import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.XML

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty

import pt.cnbc.wikimodels.exceptions.BadFormatException
import pt.cnbc.wikimodels.util.SBMLHandler

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class Compartment() extends Element {
  override final val sbmlType = "Compartment"
  var id: String = null
  var name: String = null
  var compartmentType: String = null //not implemented yet
  var spatialDimensions: Int = 0
  var size: java.lang.Double = 0 //this can be null if spacialDimen
  var units: String = null //not implemented yet
  var outside: String = null
  var constant: Boolean = false


  def this(metaid: String,
           notes: NodeSeq,
           id: String,
           name: String,
           compartmentType: String, //not implemented yet
           spatialDimensions: Int,
           size: java.lang.Double,
           units: String, //not implemented yet
           outside: String,
           constant: Boolean) = {
    this ()
    this.metaid = metaid
    this.setNotesFromXML(notes)
    this.id = id
    this.name = name
    this.compartmentType = compartmentType
    this.spatialDimensions = spatialDimensions
    this.size = size
    this.units = units
    this.outside = outside
    this.constant = constant
    (new SBMLHandler).idExistsAndIsValid(this.id)
    if (this.spatialDimensions < 0 || spatialDimensions > 3)
      throw new BadFormatException("" + spatialDimensions + " is an invalid value for spatialDimensions");
    if (spatialDimensions == 0 && size != null)
      throw new BadFormatException("size should not exist when spacialDimensions is 0");
    if (spatialDimensions > 0 && size == null)
      throw new BadFormatException("size should exist when spacialDimensions is positive");
    if (size != null && size.compareTo(0) < 0)
      throw new BadFormatException("size should have a positive value");
  }

  def this(xmlCompartment: Elem) = {
    this ((new SBMLHandler).toStringOrNull((xmlCompartment \ "@metaid").text),
      (new SBMLHandler).checkCurrentLabelForNotes(xmlCompartment),
      (new SBMLHandler).toStringOrNull((xmlCompartment \ "@id").text),
      (new SBMLHandler).toStringOrNull((xmlCompartment \ "@name").text),
      try {
        (xmlCompartment \ "@compartmentType").text
      } catch {
        case _ => null
      },
      try {
        (xmlCompartment \ "@spatialDimensions").text.toInt
      } catch {
        case _ => 3
      },
      try {
        (xmlCompartment \ "@size").text.toDouble
      } catch {
        case _ => null
      },
      (new SBMLHandler).toStringOrNull((xmlCompartment \ "@units").text),
      (new SBMLHandler).toStringOrNull((xmlCompartment \ "@outside").text),
      try {
        (xmlCompartment \ "@constant").text.toBoolean
      } catch {
        case _ => true
      })
  }

  override def toXML: Elem = {
    <compartment metaid={metaid} id={id} name={name}
                 compartmentType={compartmentType}
                 spatialDimensions={spatialDimensions.toString}
                 size={size.toString} units={units} outside={outside}
                 constant={constant.toString}>
      {new SBMLHandler().genNotesFromHTML(notes)}
    </compartment>
  }

  override def theId = this.id

  override def theName = this.name
}

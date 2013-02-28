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
  var spatialDimensions: Int = Compartment.defaultSpatialDimensions.id
  var size: java.lang.Double = null //this can be null if spacialDimen
  var units: String = null //not implemented yet
  var outside: String = null
  var constant: Boolean = Compartment.defaultConstant


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
    SBMLHandler.idExistsAndIsValid(this.id)
    if (this.spatialDimensions < 0 || spatialDimensions > 3)
      throw new BadFormatException("" + spatialDimensions + " is an invalid value for spatialDimensions");
    if (size != null && size.compareTo(0) < 0)
      throw new BadFormatException("size should have a positive value");
  }

  override def toXML: Elem = {
    <compartment metaid={metaid} id={id} name={name}
                 spatialDimensions={spatialDimensions.toString}
                 size={
                  if(size == null) null else size.toString
                 }
                 units={units}
                 outside={outside}
                 constant={constant.toString}>
                 {SBMLHandler.genNotesFromHTML(notes)}
    </compartment>
  }

  override def theId = this.id

  override def theName = this.name
}

object Compartment{
  val defaultSpatialDimensions = ValidSpatialDimensions.`3D`
  val defaultConstant: Boolean = true
}
object ValidSpatialDimensions extends Enumeration(0) {
  type ValidSpatialDimensions = Value
  val `0D` = Value(0)
  val `1D` = Value(1)
  val `2D` = Value(2)
  val `3D` = Value(3)
  def contains(x:Int) = this.values.exists(_.id == x)
}


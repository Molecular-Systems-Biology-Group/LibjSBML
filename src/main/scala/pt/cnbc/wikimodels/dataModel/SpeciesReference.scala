/*
 * SpeciesReference.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty
import scala.reflect.BeanInfo
import pt.cnbc.wikimodels.util.SBMLHandler
import pt.cnbc.wikimodels.exceptions.BadFormatException
import xml._

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class SpeciesReference() extends Element{
  override final val sbmlType = "Reaction"
  var id:String = null
  var name:String = null

  //stoichiometry and
  var stoichiometry:java.lang.Double = 1 //{ use="optional" default="1" }

  /**
   * In SBML this is a class
   * Yet that class is only a container for MathML.
   * taking this in account it was decided to turn this into a property
   * both in the WikiModels class hierarchy as weel as in the ontology
   */
  var stoichiometryMath:String = null //{ use="optional" }

  var species:String = null //mandatory

  def this(metaid:String,
           notes:NodeSeq,
           id:String,
           name:String,
           species:String,
           stoichiometry:Double,
           stoichiometryMath:NodeSeq) = {
    this()
    this.metaid = metaid
    this.setNotesFromXML(notes)
    this.id = id
    this.name = name
    this.species = species
    this.stoichiometry = stoichiometry
    this.stoichiometryMath = stoichiometryMath.toString
    if(this.stoichiometry != null)
      if(this.stoichiometry.compareTo(0) <= 0 ) throw new BadFormatException("Stoichiometry in SpeciesReference should be greater than 0.")
    if(this.stoichiometry == null && (this.stoichiometryMath == null || this.stoichiometryMath.trim == ""))
      throw new BadFormatException("A value should be issued to Stoichiometry or stoichiometryMath. Neither has it")
  }

  def this(xmlSpeciesRef:Elem) = {
    this(SBMLHandler.toStringOrNull((xmlSpeciesRef \ "@metaid").text),
         SBMLHandler.checkCurrentLabelForNotes(xmlSpeciesRef),
         SBMLHandler.toStringOrNull((xmlSpeciesRef \ "@id").text),
         SBMLHandler.toStringOrNull((xmlSpeciesRef \ "@name").text),
         (xmlSpeciesRef \ "@species").text,
         try{
          (xmlSpeciesRef \ "@stoichiometry").text.toDouble
         } catch {case _ => 1 },
         (xmlSpeciesRef \ "stoichiometryMath" \ "math"))
    //if there is a stoichiometryMath 
    if( (xmlSpeciesRef \ "@speciesReference" \ "stoichiometryMath" ).size == 0 ){
      if(this.stoichiometry == null)
        this.stoichiometry = 1
    } else {
      this.stoichiometry = null
    }
  }

  override def toXML:Elem = {
    val mathExists = this.stoichiometryMath.size > 0
    
    <speciesReference metaid={metaid} id={id} name={name}
      species={species} stoichiometry=
      {
        if(mathExists) null.asInstanceOf[String] else {this.stoichiometry.toString}
      }>
      <!--order is important according to SBML Specifications-->
      {SBMLHandler.genNotesFromHTML(notes)}
      {if(mathExists){
          <stoichiometryMath>
            {XML.loadString(this.stoichiometryMath)}
          </stoichiometryMath>
        } else {
          scala.xml.Null          
        }
      }
    </speciesReference>
  }
  override def theId = this.id
  override def theName = this.name
}

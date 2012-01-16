/*
 * Species.scala
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

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty

import pt.cnbc.wikimodels.util.SBMLHandler
import pt.cnbc.wikimodels.exceptions.BadFormatException

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class Species() extends Element{
    override final val sbmlType = "Species"
    var id:String = null
    var name:String = null
    var speciesType:String = null  //it is not included yet.
    var compartment:String = null
    var initialAmount:java.lang.Double = null
    var initialConcentration:java.lang.Double = null
    var substanceUnits:String = null   //it is not included yet.
    var hasOnlySubstanceUnits:Boolean = Species.defaultHasOnlySubstanceUnits   //it is not included yet.
    var boundaryCondition:Boolean = Species.defaultBoundaryCondition
    var constant:Boolean = Species.defaultConstant

    var reactantOf:java.util.Collection[Reaction] = null
    var productOf:java.util.Collection[Reaction] = null

    def this(metaid:String,
             notes:NodeSeq,
             id:String,
             name:String,
             speciesType:String,  //it is not included yet.
             compartment:String,
             initialAmount:java.lang.Double,
             initialConcentration:java.lang.Double,
             substanceUnits:String,   //it is not included yet.
             hasOnlySubstanceUnits:Boolean,   //it is not included yet.
             boundaryCondition:Boolean,
             constant:Boolean) = {
        this()
        this.metaid = metaid
        this.setNotesFromXML(notes)
        this.id = id
        this.name = name
        this.speciesType = speciesType
        this.compartment = compartment
        this.initialAmount = initialAmount
        this.initialConcentration = initialConcentration
        this.substanceUnits = substanceUnits
        this.hasOnlySubstanceUnits = hasOnlySubstanceUnits
        this.boundaryCondition = boundaryCondition
        this.constant = constant
      SBMLHandler.idExistsAndIsValid(this.id)
    }

    override def toXML:Elem = {
        <species metaid={metaid} id={id} name={name} 
            speciesType={speciesType} compartment={compartment}
            initialAmount={
                if(initialAmount == null) null else {initialAmount.toString}
            }
            initialConcentration={
                if(initialConcentration == null) null else {initialConcentration.toString}
            }
            substanceUnits={substanceUnits}
            hasOnlySubstanceUnits={hasOnlySubstanceUnits.toString}
            boundaryCondition={boundaryCondition.toString}
            constant={constant.toString} >
            <!--order is important according to SBML Specifications-->
            {SBMLHandler.genNotesFromHTML(notes)}
        </species>
    }
    override def theId = this.id
    override def theName = this.name
}

object Species {
  val defaultHasOnlySubstanceUnits = false
  val defaultBoundaryCondition:Boolean = false
  val defaultConstant:Boolean = false
}

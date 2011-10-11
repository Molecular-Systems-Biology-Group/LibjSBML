/*
 * KineticLaw.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import scala.collection.JavaConversions._
import scala.reflect.BeanInfo
import xml.{NodeSeq, Elem, XML}
import thewebsemantic._
import pt.cnbc.wikimodels.util.SBMLHandler


@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class KineticLaw() extends Element{
  override final val sbmlType = "KineticLaw"
  var math:String = null

  @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#hasParameter")
  var listOfParameters:java.util.Collection[Parameter] = null

  def this(metaid:String,
           notes:NodeSeq,
           math:NodeSeq) = {
      this()
      this.metaid = metaid
      this.setNotesFromXML(notes)
      this.math = SBMLHandler.addNamespaceToMathML(math).toString
  }

  override def toXML:Elem = {
    Console.println("«KineticLaw math element is " + this.math)
    <kineticLaw metaid={metaid}>
      <!--order is important according to SBML Specifications-->
      {SBMLHandler.genNotesFromHTML(notes)}
      {XML.loadString(this.math)}
      {if(listOfParameters != null && listOfParameters.size != 0)
        <listOfParameters>
          {listOfParameters.map(i => i.toXML)}
        </listOfParameters> else scala.xml.Null }
    </kineticLaw>
  }
}

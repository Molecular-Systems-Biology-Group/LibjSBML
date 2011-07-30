/*
 * __NAME__.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import scala.xml.Elem

class SBMLModels extends DataModel {

  var listOfModels:Iterable[SBMLModel] = null

  def toXML:Elem =
    <models>
      {if(listOfModels != null && listOfModels.size != 0 )
      <listOfModels>
        {listOfModels.map(i => i.toXML) }
      </listOfModels>
      }
    </models>
}

/*
 * NotImplementedException.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.exceptions

class NotImplementedException(val motive:String) extends RuntimeException(motive) {
}

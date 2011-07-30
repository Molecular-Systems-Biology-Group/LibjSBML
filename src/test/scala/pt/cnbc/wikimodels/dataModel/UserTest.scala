    /*
 * UserTest.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/*
TODO - UNCOMMENT THIS TEST AND MAKE IT WORK

package pt.cnbc.wikimodels.dataModel

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert._
import scala.xml.Node

class UserTest {

    @Before
    def setUp: Unit = {
    }

    @After
    def tearDown: Unit = {
    }

    @Test
    def xmlUserRoundTripTest = {
        var users:List[User] = Nil
        users ::= User("alex","alexp","Alexandre", "Martins","alexmsmartins@gmail.com")
        users ::= User("Alexandre","Alexandrep","Alexandre Mesquita", "Martins","alexmsmartins@gmail.com")
        users ::= User("Armindo","Armindop","Armindo", "Salvador","armindo.salvador@gmail.com")
        users ::= User("Gonçalo", "goncalop", "Gonçalo", "Neto", "gonneto@gmail.com")
        users ::= User("admin", "adminp", "Admin", "Root", "admin@gmail.com")

        assertTrue("XML reamin equal after round trip conversion",
                   users.map( i => {
                    i.toXML == (new User(i.toXML)).toXML
                   } ).reduceLeft(_ && _) )
        assertTrue(true)
    }
}*/

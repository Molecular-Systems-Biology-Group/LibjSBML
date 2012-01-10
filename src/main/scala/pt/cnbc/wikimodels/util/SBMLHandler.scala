/*
 * SBMLHandler.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.util

import org.sbml.libsbml.SBMLDocument
import org.sbml.libsbml.SBMLReader

import pt.cnbc.wikimodels.exceptions.BadFormatException
import xml._
import pt.cnbc.wikimodels.sbml.namesspaces._

class LibSBMLHandler {

  if (LibSBMLHandler.LibSBMLLoaded == false) {
    LibSBMLLoader()
    LibSBMLHandler.LibSBMLLoaded = true
  }
  /**
   * This method should be run before any other method in this object
   * It olads the libSBML bindings for java.
   */
  def validateSBML(sbml: Node, sbmlLevel: Int, sbmlVersion: Int): Boolean = {
    validateSBML(sbml.toString, sbmlLevel, sbmlVersion)
  }

  def validateSBML(sbml: String, sbmlLevel: Int, sbmlVersion: Int) = {
    val reader = new SBMLReader()
    reader.readSBMLFromString(
      """<?xml version="1.0" encoding="UTF-8"?>\n\
      """ + sbml.toString)

    val sbmlDoc = new SBMLDocument(sbmlLevel, sbmlVersion)


    Console.println("checkL2v4Compatibility errors:" + sbmlDoc.checkL2v4Compatibility)
    Console.println("checkConsistency errors:" + sbmlDoc.checkConsistency)
    Console.println("checkInternalConsistency errors:" + sbmlDoc.checkInternalConsistency)
    if (sbmlDoc.checkInternalConsistency > 0) {
      Console.println("Error: " + sbmlDoc.getError(0).getErrorId + "\n" +
                      "Category:" + sbmlDoc.getError(0).getCategoryAsString + "\n" +
                      "Line:" + sbmlDoc.getError(0).getLine + "\n" +
                      "Column:" + sbmlDoc.getError(0).getColumn + "\n" +
                      "Message:" + sbmlDoc.getError(0).getMessage + "\n" +
                      "Sevegory:" + sbmlDoc.getError(0).getSeverityAsString + "\n")
    }

    val checkCompat = (sbmlLevel, sbmlVersion) match {
      case (2, 1) => (sbmlDoc.checkL2v1Compatibility == 0)
      case (2, 2) => (sbmlDoc.checkL2v4Compatibility == 0)
      case (2, 3) => (sbmlDoc.checkL2v4Compatibility == 0)
      case (2, 4) => (sbmlDoc.checkL2v4Compatibility == 0)
      case _ => false //more options in the future
    }
    //TODO INCLUDE checkInternalConsistency when bug in BIOMD0000000141 is pinpionted
    (checkCompat &&
        (sbmlDoc.checkL2v4Compatibility == 0) &&
        (sbmlDoc.checkConsistency == 0)) /* &&
                                           (sbmlDoc.checkInternalConsistency == 0)*/
  }
}
object LibSBMLHandler {
  var LibSBMLLoaded = false
}

object SBMLHandler {

  /**
   * Produces the XML of the <notes section of any SBase
   * @returns a <notes> sections with the content of notesContent
   * embebed inside or null if there is no content to embeb
   */
  def genNotesFromHTML(notesContent: String): Elem = {
    Console.println("HTML to generate notes = " + notesContent)
    wrapHTML(notesContent, "notes")
  }

  /**
   * Produces the XML of the <message section of a Constraint
   * @returns a <message> sections with the content of messageContent
   * embebed inside or null if there is no content to embeb
   */
  def genMessageFromHTML(messageContent: String): Elem = {
    Console.println("HTML to generate message = " + messageContent)
    wrapHTML(messageContent, "message")
  }

  /**
   * Produces the XML of the <notes section of any SBase
   * @returns a <notes> sections with the content of notesContent
   * embebed inside or null if there is no content to embeb
   */
  private def wrapHTML(content: String, labelWrapper: String) = {
    Console.println("Calling LibSBMLHandler.wrapHTML")
    if (content != null && content.trim != "") {
      XML.loadString("<" + labelWrapper + ">" +
          content +
          "</" + labelWrapper + ">")
    } else {
      null
    }
  }

  private def addNamespaceToXML(ns: NodeSeq, namespace: String): NodeSeq ={
    Console.println("Calling SBMLHandler.addNamespaceToXML")
    if (ns != Nil)
      ns.map(i => {
        Console.println("Node " + i + " is type " + i.getClass)
        i match {
          case x:Elem =>
            new Elem(i.prefix,
              x.label,
              x.attributes,
              NamespaceBinding(null, namespace, TopScope ),
              addTopScopeToXMLRecurs(x.child, namespace): _*)
           case x => x}
      }).filter(i  => !  i.isInstanceOf[Elem] || i.label != "#PCDATA") //hack to make <#PCDATA go away
    else Nil
  }

  def addTopScopeToXMLRecurs(ns: NodeSeq, namespace: String): NodeSeq ={
    Console.println("Calling SBMLHandler.addTopScopeToXMLRecurs")
      if (ns != Nil)
        ns.map(i =>{
          Console.println("Node " + i + " is type " + i.getClass)
          i match {
            case x:Elem =>
              new Elem(x.prefix,
                x.label,
                x.attributes,
                TopScope,
                addTopScopeToXMLRecurs(x.child, namespace): _*)
            case x => x
          }
        }).filter(i  => (!  i.isInstanceOf[Elem]) || i.label != "#PCDATA") //hack to make <#PCDATA go away
      else Nil
  }

  def addNamespaceToXHTML(nodeseq: NodeSeq): NodeSeq =
    this.addNamespaceToXML(nodeseq, xmlns.XHTML)

  def addNamespaceToMathML(math: NodeSeq): NodeSeq =
    this.addNamespaceToXML(math, xmlns.XHTML)


  /**
   * Checks the current XML label for the presence of a Notes label.
   * The input must be in the form:
   * <currentlabel metaid="123" id="456" name"name789">
   *  <notes>
   *  </notes>
   * </currentlabel>
   * @return NodeSeq of the xhtml contained inside the notes or null
   * if there are not notes
   */
  def checkCurrentLabelForNotes(xmlLabel: Elem): NodeSeq = {
    Console.println("Calling SBMLHandler.checkCurrentLabelForNotes")
    val notes: NodeSeq = (xmlLabel \ "notes")
    if (notes.size == 0) {
      Nil
    } else {
      val noteContent = notes.iterator.next.child
      Console.println("SBMLHandler.checkCurrentLabelForNotes() received the following note: \n" + noteContent)
      noteContent
    }
  }


  /**
   * Checks the current XML labe for the presence of a message label.
   * The input must be in the form:
   * <currentlabel metaid="123" id="456" name"name789">
   *  <message>
   *  </message>
   * </currentlabel>
   * @return NodeSeq of the xhtml contained inside the notes or null
   * if there are not notes
   */
  def checkCurrentLabelForMessage(xmlLabel: Elem): NodeSeq = {
    val message: NodeSeq = (xmlLabel \ "message")
    if (message.size == 0) {
      Nil
    } else message.iterator.next.child
  }

  def idExistsAndIsValid(id: String): Boolean = {
    //if metaId does not exist it will be generated
    if (id == null || id == "")
      throw new BadFormatException("Id is mandatory")
    else true
  }

  /**
   * Gives back the supplied string or null if that string if empty
   * or composed of spaces.
   * This function is used to insure that XPath searches that turn out nothing
   * actually return nothing and not an empty string
   */
  def toStringOrNull(str: String) = if (str.trim == "") null else str.trim

  def convertStringToBool(strBol: String, defaultVal: Boolean): Boolean = {
    strBol match {
      case "true" => true
      case "" => defaultVal
      case "false" => false
      case _ => throw new BadFormatException(strBol + " not a boolean value!")
    }
  }
}


/**
 * Responsible for the loading of libsbml to the java classloader
 * It avoids the <code>java.lang.UnsatisfiedLinkError: Native Library
 * xxx  already loaded in another classloader</code> described in
 * http://java.sun.com/docs/books/jni/html/design.html#8628 by ensuring
 * that library is loaded only once in the apply method
 */
object LibSBMLLoader {
  var alreadyLoaded = false

  def apply() = {
    import javax.resource.spi.SecurityException
    //TODO Remove javax.resource.connector-api dependency from wm_libjsbml if this is not in wm_libjsbml anymore ;)
    if (alreadyLoaded == false) {
      var varname:String = null
      var shlibname:String = null

      if (System.getProperty("os.name").startsWith("Mac OS"))
      {
        varname = "DYLD_LIBRARY_PATH";    // We're on a Mac.
        shlibname = "libsbmlj.jnilib and/or libsbml.dylib";
      }
      else
      {
        varname = "LD_LIBRARY_PATH";      // We're not on a Mac.
        shlibname = "libsbmlj.so and/or libsbml.so";
      }

      try {
        //            System.setProperty("java.library.path", "/usr/local/lib:/usr/local:" + System.getProperty("java.library.path"));
        //System.out.println(System.getProperty("java.library.path"));

        System.loadLibrary("sbmlj");
        //System.load("/usr/lib64/libsbmlj.so")
        /* Extra check to be sure we have access to libSBML: */
        if( Class.forName("org.sbml.libsbml.libsbml") == null){
          alreadyLoaded = false
          throw new ClassNotFoundException("While loading org.sbml.libsbml.libsbml")
        } else alreadyLoaded = true
      } catch {
        case e: SecurityException => {
          Console.err.println("A security manager exists but it's <code>checkLink</code> method didn't allow " +
                             "libSBML to be loaded.\n"+
                             "Error encountered while attempting to load libSBML:");
          e.printStackTrace();
          Console.err.println("Could not load the libSBML library files due to a"+
                             " security exception.\n");

          Console.err.println("A security manager exists and its" +
                             "<code>checkLink</code> method doesn't" +
                             "allow loading of the specified dynamic " +
                             "library: ")
          e.printStackTrace
        }
        case e: UnsatisfiedLinkError => {
          Console.err.println("Error encountered while attempting to load libSBML:");
          e.printStackTrace();
          Console.err.println("Please check the value of your " + varname +
           " environment variable and/or" +
                             " your 'java.library.path' system property" +
                             " (depending on which one you are using) to" +
                             " make sure it lists all the directories needed to" +
                             " find the " + shlibname + " library file and the" +
                             " libraries it depends upon (e.g., the XML parser).");
        }
        case e:ClassNotFoundException => {
          Console.err.println("Error: unable to load the file 'libsbmlj.jar'." +
                             " It is likely that your -classpath command line " +
                             " setting or your CLASSPATH environment variable " +
                             " do not include the file 'libsbmlj.jar'.");
        }
        case e: NullPointerException => {
          Console.println("<code>filename</code> is " +
                          "<code>null</code>")
          e.printStackTrace
        }
      } finally {
        //FIXME It would be useful to handle the exceptional cases in a more elegant way. WITHOUT EXITING!!!!
        System.exit(1)
      }
    }
  }
}

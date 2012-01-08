package pt.cnbc.wikimodels.util

/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

import org.junit._
import Assert._

/**TODO: Please document.
 *  @author: Alexandre Martins
 *  Date: 08-01-2012
 *  Time: 1:51 */
class XMLParserTest extends XMLParser {

  @Before
  def setUp: Unit = {
    Console.println(this.getClass+".setUp() is running ")
  }

  @After
  def tearDown: Unit = {


    Console.println(this.getClass+".tearDown() is running ")
  }

  def parsingWasSuccessful(parseResult:ParseResult[Any]):Boolean = {
    parseResult match {
      case Success(_,_) => true
      case Failure(_,_) => false
      case _ => {
        fail("Something is wrong with this test")
        false
      }
    }
  }

  @Test
  def validMetaIds = {
    var result = parseAll(Name, "_abc")
    assertTrue(parsingWasSuccessful(result))
    println(result)
    result = parseAll(Name, "abc")
    assertTrue(parsingWasSuccessful(result))
    println(result)
    result = parseAll(Name, ":abc2-")
    assertTrue(parsingWasSuccessful(result))
    println(result)
    result = parseAll(Name, "_abc.1")
    assertTrue(parsingWasSuccessful(result))
    println(result)
    result = parseAll(Name, "::")
    assertTrue(parsingWasSuccessful(result))
    println(result)

  }



  @Test
  def invalidMetaIds = {
    var result = parseAll(Name, ".abc")
    assertFalse(parsingWasSuccessful(result))
    println(result)
    result = parseAll(Name, "-abc")
    assertFalse(parsingWasSuccessful(result))
    println(result)
    result = parseAll(Name, "_?abc")
    assertFalse(parsingWasSuccessful(result))
    println(result)
    result = parseAll(Name, "1")
    assertFalse(parsingWasSuccessful(result))
    println(result)
  }

}


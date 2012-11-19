/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.exceptions

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 14-09-2011
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */

object ValidationDefaultCase {
  def exceptionHandling(e:Throwable):List[String] = {
    //TODO: maybe some log here
    """UNEXPECTED ERROR: Please report this as a bug with the following stacktrace:
    """.stripMargin + e.printStackTrace() :: Nil
  }
}
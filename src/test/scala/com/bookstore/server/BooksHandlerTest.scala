package com.bookstore.server

import com.bookstore.server.httphandlerfactory.{BookHandler, BooksHandler}
import net.liftweb.json
import net.liftweb.json.JsonAST.JValue
import org.scalatest.{BeforeAndAfter, FunSuite}

class BooksHandlerTest extends FunSuite with BeforeAndAfter {

  before {
    BookController.addBook("B01", "Birds", "Novel")
    BookController.addBook("B02", "Venus", "Fiction")
    BookController.addBook("B03", "Sea", "Novel")
  }

  test("BookController.getBooks") {
    var jvalue: JValue = json.parse(BookController.getBooks())
    val result = jvalue.children
    // checking the book count returned is correct
    assert(result.size == 3)
    // checking the books returned are correct
    // result(0).values.asInstanceOf[Map.Map1].get("_2")
  }

}

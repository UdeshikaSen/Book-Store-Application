package com.bookstore.server

import java.io.OutputStream

import com.sun.net.httpserver.{HttpExchange, HttpHandler}
import net.liftweb.json.JsonAST._
import net.liftweb.json.Extraction._
import scala.collection.JavaConverters._


class BooksHandler extends HttpHandler {

  override def handle(httpExchange: HttpExchange): Unit = {

    var httpMethod: String = httpExchange.getRequestMethod().toLowerCase()
    var response: String = ""

    httpMethod match {
      // example endpoint url - http://localhost:9090/bookstore/books
      case "get" => response = getBooks()
    }

    httpExchange.sendResponseHeaders(200, response.length)
    var out: OutputStream = httpExchange.getResponseBody()
    out.write(response.getBytes())
    out.close()

  }

  // get all books
  def getBooks(): String = {
    implicit val formats = net.liftweb.json.DefaultFormats
    var response = compactRender(decompose(BookStoreServer.bookList.asScala))

    return response
  }

}

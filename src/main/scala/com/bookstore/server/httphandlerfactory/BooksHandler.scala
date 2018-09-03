package com.bookstore.server.httphandlerfactory

import java.io.OutputStream

import com.bookstore.server.{BookController}
import com.sun.net.httpserver.{HttpExchange, HttpHandler}

class BooksHandler extends HttpHandler {

  override def handle(httpExchange: HttpExchange): Unit = {

    var httpMethod: String = httpExchange.getRequestMethod().toLowerCase()
    var response: String = ""

    httpMethod match {
      // example endpoint url - http://localhost:9090/bookstore/books
      case "get" => response = BookController.getBooks()
    }

    httpExchange.sendResponseHeaders(200, response.length)
    var out: OutputStream = httpExchange.getResponseBody()
    out.write(response.getBytes())
    out.close()

  }


}

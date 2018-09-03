package com.bookstore.server.httphandlerfactory

import java.io.OutputStream
import java.net.URI

import com.bookstore.server.{Book, BookController}
import com.sun.net.httpserver.{HttpExchange, HttpHandler}

class BookHandler extends HttpHandler {

  override def handle(httpExchange: HttpExchange): Unit = {

    var httpMethod: String = httpExchange.getRequestMethod().toLowerCase()
    var uri: URI = httpExchange.getRequestURI()
    var query = uri.getRawQuery
    var parameters = parseQuery(query)
    var response: String = ""

    httpMethod match {
      // example endpoint url - http://localhost:9090/bookstore/books/book?bookId=B01
      case "get" => val retrievedBook: Book = BookController.getBookDetails(parameters("bookId"))
        response = "Book Id : " + retrievedBook.bookId + "\nName : " + retrievedBook.bookName + "\nType : " + retrievedBook.bookType

      // example  endpoint url - http://localhost:9090/bookstore/books/book?bookId=B01&bookName=Birds&bookType=Novel
      case "post" => val addedBook: Book = BookController.addBook(parameters("bookId"), parameters("bookName"), parameters("bookType"))
        if (addedBook != null) {
          response = "Book " + addedBook.bookId + ":" + addedBook.bookName + " added to the store"
        }
        else {
          response = "Error in adding the book " + parameters("bookId") + ":" + parameters("bookName")
        }
    }

    httpExchange.sendResponseHeaders(200, response.length)
    var out: OutputStream = httpExchange.getResponseBody()
    out.write(response.getBytes())
    out.close()
  }

  // parse the query to get query parameters
  def parseQuery(query: String): Map[String, String] = {
    var pairs: Array[String] = query.split("[&]")
    pairs.map(pair => pair.split("[=]")(0) -> pair.split("[=]")(1)).toMap
  }

}

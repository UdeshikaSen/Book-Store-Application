package com.bookstore.server

import java.io.OutputStream
import java.net.URI
import java.util

import com.sun.net.httpserver.{HttpExchange, HttpHandler}

class BookHandler extends HttpHandler {

  if (BookStoreServer.bookList == null) {
    BookStoreServer.bookList = new util.HashMap[String, Book]()
  }

  override def handle(httpExchange: HttpExchange): Unit = {

    var httpMethod: String = httpExchange.getRequestMethod().toLowerCase()
    var uri: URI = httpExchange.getRequestURI()
    var query = uri.getRawQuery
    var parameters = parseQuery(query)
    var response: String = ""

    httpMethod match {
      // example endpoint url - http://localhost:9090/bookstore/books/book?bookId=B01
      case "get" => response = getBookDetails(parameters("bookId"))
      // example  endpoint url - http://localhost:9090/bookstore/books/book?bookId=B01&bookName=Birds&bookType=Novel
      case "post" => response = addBook(parameters("bookId"), parameters("bookName"), parameters("bookType"))
    }

    httpExchange.sendResponseHeaders(200, response.length)
    var out: OutputStream = httpExchange.getResponseBody()
    out.write(response.getBytes())
    out.close()

  }

  // get details of a particular book
  def getBookDetails(bookId: String): String = {
    val retrievedBook: Book = BookStoreServer.bookList.get(bookId)
    var response: String = ""
    if (retrievedBook != null) {
      response = "Book Id : " + retrievedBook.bookId + "\n" +
        "Name : " + retrievedBook.bookName + "\n" +
        "Type : " + retrievedBook.bookType
    }
    else {
      response = "Invalid Book Id"
    }
    return response
  }

  // add a new book to the store
  def addBook(bookId: String, bookName: String, bookType: String): String = {
    var response: String = ""

    try {
      val newBook = new Book(bookId, bookName, bookType)
      BookStoreServer.bookList.put(bookId, newBook)
      response = "Book " + bookId + ":" + bookName + " added to the store"
    }
    catch {
      case ex: Exception => response = "Error in adding the book" + bookId + ":" + bookName
    }

    return response
  }

  // parse the query to get query parameters
  def parseQuery(query: String): Map[String, String] = {
    var pairs: Array[String] = query.split("[&]")
    pairs.map(pair => pair.split("[=]")(0) -> pair.split("[=]")(1)).toMap
  }
}

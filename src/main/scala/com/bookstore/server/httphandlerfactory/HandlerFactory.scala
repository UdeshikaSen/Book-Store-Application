package com.bookstore.server.httphandlerfactory

import com.sun.net.httpserver.HttpHandler

object HandlerFactory {

  def apply(handlerType: String): HttpHandler = {
    handlerType.toLowerCase.replaceAll("\\s", "") match {
      case "bookhandler" => new BookHandler
      case "bookshandler" => new BooksHandler
      case default => null
    }
  }
}

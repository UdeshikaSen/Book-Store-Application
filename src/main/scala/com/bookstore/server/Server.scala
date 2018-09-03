package com.bookstore.server

import java.io.IOException
import java.net.InetSocketAddress

import com.bookstore.server.httphandlerfactory.HandlerFactory
import com.sun.net.httpserver.HttpServer

object Server {

  @throws(classOf[IOException])
  def main(args: Array[String]): Unit = {
    val server: HttpServer = HttpServer.create(new InetSocketAddress(9090), 0)
    server.createContext("/bookstore/books", HandlerFactory("Books Handler"))
    server.createContext("/bookstore/books/book", HandlerFactory("Book Handler"))
    server.setExecutor(null)
    server.start()

    // example endpoint urls
    // add book  - http://localhost:9090/bookstore/books/book?bookId=B01&bookName=Birds&bookType=Novel
    // get book  - http://localhost:9090/bookstore/books/book?bookId=B01
    // get books - http://localhost:9090/bookstore/books
  }
}

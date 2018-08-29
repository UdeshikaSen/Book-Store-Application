package com.bookstore.server

class Book(val bookId: String, val bookName: String, val bookType: String) {

  def printDetails: Unit = {
    println("Book Id : " + bookId)
    println("Name : " + bookName)
    println("Type : " + bookType)

  }
}

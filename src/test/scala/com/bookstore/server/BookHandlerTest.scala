package com.bookstore.server

import org.scalatest.{BeforeAndAfter, FunSuite}

class BookHandlerTest extends FunSuite with BeforeAndAfter {

  var bookId = "B01"
  var name = "Birds"
  var bookType = "Novel"

  before {
    BookController.addBook(bookId, name, bookType)
  }

  test("BookController.addBook") {
    bookId = "B02"
    name = "Jungle"

    BookController.addBook(bookId, name, bookType)
    assert(BookStore.bookList.size == 2)

    val bookRetrievedFromStore: Book = BookStore.bookList("B02")
    assert(bookRetrievedFromStore != null)
    assert(bookRetrievedFromStore.bookId == bookId)
    assert(bookRetrievedFromStore.bookName == name)
    assert(bookRetrievedFromStore.bookType == bookType)
  }

  test("BookController.getBookDetails") {
    val retrievedBook: Book = BookController.getBookDetails(bookId)
    assert(retrievedBook != null)
    assert(retrievedBook.bookId == bookId)
    assert(retrievedBook.bookName == name)
    assert(retrievedBook.bookType == bookType)
  }


}

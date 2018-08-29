package com.bookstore.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class BookStoreServer {

    static Map<String, Book> bookList;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(9090), 0);
        server.createContext("/bookstore/books", new BooksHandler());
        server.createContext("/bookstore/books/book", new BookHandler());
        server.setExecutor(null);
        server.start();
    }
}

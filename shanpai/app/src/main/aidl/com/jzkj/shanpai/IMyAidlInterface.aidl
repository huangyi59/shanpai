// IMyAidlInterface.aidl
package com.jzkj.shanpai;

// Declare any non-default types here with import statements
import com.jzkj.shanpai.Book;

interface IMyAidlInterface {
    List<Book> getBookList();
    void addBook(in Book book);
}

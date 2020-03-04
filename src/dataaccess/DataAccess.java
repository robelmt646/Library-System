package dataaccess;

import java.util.HashMap;


import business.Book;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess {
    HashMap<String,Book> readBooksMap();
    HashMap<String,User> readUserMap();
    HashMap<String, LibraryMember> readMemberMap();
    void saveNewMember(LibraryMember member);
    HashMap<String,Book> findBooksMap(String isbn);
    void saveBook(Book book);
    void saveNewCopy(Book book);
}

package com.library.Library.models;

import org.springframework.data.repository.CrudRepository;

public interface BookDao extends CrudRepository<Book, Integer> {
    public Book searchBookById(int id);

}

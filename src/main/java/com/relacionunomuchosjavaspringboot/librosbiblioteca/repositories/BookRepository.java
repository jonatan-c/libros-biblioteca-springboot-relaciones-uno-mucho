package com.relacionunomuchosjavaspringboot.librosbiblioteca.repositories;

import com.relacionunomuchosjavaspringboot.librosbiblioteca.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}

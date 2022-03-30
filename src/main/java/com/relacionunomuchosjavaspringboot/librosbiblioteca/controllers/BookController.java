package com.relacionunomuchosjavaspringboot.librosbiblioteca.controllers;

import com.relacionunomuchosjavaspringboot.librosbiblioteca.entities.Book;
import com.relacionunomuchosjavaspringboot.librosbiblioteca.entities.Library;
import com.relacionunomuchosjavaspringboot.librosbiblioteca.repositories.BookRepository;
import com.relacionunomuchosjavaspringboot.librosbiblioteca.repositories.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @GetMapping
    public ResponseEntity<Page<Book>> listarLibros(Pageable pageable){
        return ResponseEntity.ok(bookRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Book> guardarLibro(@Valid @RequestBody Book book){
        Optional<Library> libraryOptional = libraryRepository.findById((long) book.getLibrary().getId());

        if(!libraryOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        book.setLibrary(libraryOptional.get());
        Book bookSave = bookRepository.save(book);
        URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(bookSave.getId()).toUri();

        return ResponseEntity.created(ubication).body(bookSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> actualizarLibro(@Valid @RequestBody Book book,@PathVariable Integer id){
        Optional<Library> libraryOptional = libraryRepository.findById((long) book.getLibrary().getId());

        if(!libraryOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Book> bookOptional = bookRepository.findById(Long.valueOf(id));
        if(!bookOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        book.setLibrary(libraryOptional.get());
        book.setId(bookOptional.get().getId());
        bookRepository.save(book);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> eliminarBook(@PathVariable Integer id){
        Optional<Book> bookOptional = bookRepository.findById(Long.valueOf(id));

        if(!bookOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        bookRepository.delete(bookOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id){
        Optional<Book> bookOptional = bookRepository.findById(Long.valueOf(id));

        if(!bookOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(bookOptional.get());
    }
}

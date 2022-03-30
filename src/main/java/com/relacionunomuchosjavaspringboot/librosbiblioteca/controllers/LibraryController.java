package com.relacionunomuchosjavaspringboot.librosbiblioteca.controllers;

import com.relacionunomuchosjavaspringboot.librosbiblioteca.entities.Library;
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
@RequestMapping("api/library")
public class LibraryController {

    @Autowired
    private LibraryRepository libraryRepository;

    @GetMapping
    public ResponseEntity<Page<Library>> getAllLibraries(Pageable pageable){
        return ResponseEntity.ok(libraryRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Library> createLibrary(@Valid @RequestBody Library library) {
        Library librarySave = libraryRepository.save(library);
        URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(librarySave.getId()).toUri();
        return ResponseEntity.created(ubication).body(librarySave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> editLibrary(@PathVariable Long id,@Valid @RequestBody Library library) {

        Optional<Library> libraryOptional = libraryRepository.findById(id);

        if(!libraryOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        library.setId(libraryOptional.get().getId());
        libraryRepository.save(library);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Library> deleteLibrary(@PathVariable Long id){
        Optional<Library> libraryOptional = libraryRepository.findById(id);

        if(!libraryOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        libraryRepository.delete(libraryOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> obtenerBibliotecaPorId(@PathVariable Long id){
        Optional<Library> libraryOptional = libraryRepository.findById(id);

        if(!libraryOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(libraryOptional.get());
    }

}
package com.relacionunomuchosjavaspringboot.librosbiblioteca.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book" , uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}) })
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String title;

    // Lazy = trae un dato cuando lo indiquemos, no cada vez que consultemos
    // Eager = trae todos los datos al momento de crear la entidad, no cada vez que consultemos, consume mucho.


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="library_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Library library;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}

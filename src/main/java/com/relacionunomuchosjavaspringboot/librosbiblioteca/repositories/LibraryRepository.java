package com.relacionunomuchosjavaspringboot.librosbiblioteca.repositories;

import com.relacionunomuchosjavaspringboot.librosbiblioteca.entities.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {


}

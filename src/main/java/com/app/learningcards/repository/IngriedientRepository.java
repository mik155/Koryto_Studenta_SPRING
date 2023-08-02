package com.app.learningcards.repository;

import com.app.learningcards.models.Ingriedient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IngriedientRepository extends JpaRepository<Ingriedient, Integer>
{
    @Query("SELECT i FROM Ingriedient i WHERE i.name = :name")
    Optional<Ingriedient> getIngriedientByName(@Param("name" )String name);
}

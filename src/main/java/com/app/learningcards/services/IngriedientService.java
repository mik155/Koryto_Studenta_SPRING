package com.app.learningcards.services;

import com.app.learningcards.models.Ingriedient;
import com.app.learningcards.repository.IngriedientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class IngriedientService
{
    private IngriedientRepository  ingredientRepository;

    public Ingriedient getIngredientByName(String name)
    {
        Optional<Ingriedient> ingriedient = ingredientRepository.getIngriedientByName(name);
        return ingriedient.orElse(null);
    }
}

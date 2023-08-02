package com.app.learningcards.services;

import com.app.learningcards.models.User;
import com.app.learningcards.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService
{
    private UserRepository userRepository;

    public User getUser(String login)
    {
        return userRepository.findByLogin(login).orElse(null);
    }
    public void registerUser(User user)
    {
        user.setFavRecipes(new LinkedList<>());
        userRepository.save(user);
    }

    public boolean loginOccupied(User user)
    {
        Optional<User> userOptional = userRepository.findByLogin(user.getLogin());
        return userOptional.isPresent();
    }

    public boolean emailOccupied(User user)
    {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        return userOptional.isPresent();
    }

    public void saveUserData(User user)
    {
        userRepository.save(user);
    }
}

package com.app.learningcards.repository;

import com.app.learningcards.models.User;
import com.app.learningcards.models.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Users u WHERE u.login = :login")
    Optional<User> findByLogin(@Param("login") String login);
}

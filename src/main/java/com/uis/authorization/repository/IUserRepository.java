package com.uis.authorization.repository;

import com.uis.authorization.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Daniel Adrian Gonzalez Buendia
 **/
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findTopByUsername(String username);
}

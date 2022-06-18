package com.uis.authorization.repository;

import com.uis.authorization.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Daniel Adrian Gonzalez Buendia
 **/
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    @Query("FROM User u WHERE u.username = :username and u.password = :password")
    User findTopByusernameAndPassword(String username, String password);
}

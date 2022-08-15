package com.uis.authorization.repository;

import com.uis.authorization.model.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface IFriendsRepository extends JpaRepository<Friends, Long> {

    @Query(value = "FROM Friends where idUser1 = :idUser or idUser2 = :idUser and confirmed = true")
    Optional<ArrayList<Friends>> findAllByIdUser1OrIdUser2(Long idUser);

    // check if the user is already a friend
    @Query(value = "FROM Friends where (idUser1 = :idUser1 and idUser2 = :idUser2) or (idUser1 = :idUser2 and idUser2 = :idUser1)" +
            "and confirmed = true")
    Optional<Friends> findByIdUser1AndIdUser2(Long idUser1, Long idUser2);
}

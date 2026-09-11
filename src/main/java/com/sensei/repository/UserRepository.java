package com.sensei.repository;

import com.sensei.entity.User;
import com.sensei.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findAllByUserRoleInOrderById(Iterable<UserRole> roles);
    Optional<User> findByEmailIgnoreCase(String email);


    @Modifying
    @Query("UPDATE User u SET u.userRole = :role WHERE u.id = :id")
    void updateRole(@Param("id") int id,@Param("role") UserRole newRole);

}

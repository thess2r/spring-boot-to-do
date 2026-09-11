package com.sensei.service;

import com.sensei.entity.User;
import com.sensei.entity.UserRole;
import com.sensei.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }


    public void save(User user) {
        userRepository.save(user);
    }
    public List<User> findAllByRoleIn(Iterable<UserRole> roles){
        return userRepository.findAllByUserRoleInOrderById(roles);
    }

    public void deleteById(int id){
        userRepository.deleteById(id);
    }

    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow((()-> new IllegalArgumentException("User with email = " + email + " not found")));

    }

    public void updateRole(int id,UserRole newRole){
        userRepository.updateRole(id,newRole);
    }
}

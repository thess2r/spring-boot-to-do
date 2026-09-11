package com.sensei.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  int id;

    @Column(name = "name",nullable = false,length = 20)
    private String name;

    @Column(name = "email",nullable = false,unique = true, length = 30)
    private String email;

    @Column(name = "password",nullable = false,length = 60)
    private String password;


    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @OneToMany(mappedBy = "user")
    private List<Record> records;


    public User() {
    }

    public User(String name, String email, String password, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean isSimpleUser(){
        return this.userRole == UserRole.USER;
    }

    public boolean isAdmin(){
        return this.userRole == UserRole.ADMIN;
    }

    public boolean isSuperAdmin(){
        return this.userRole == UserRole.SUPER_ADMIN;
    }





    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}

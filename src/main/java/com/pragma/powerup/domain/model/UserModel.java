package com.pragma.powerup.domain.model;

import java.time.LocalDate;

public class UserModel {
    private int id;
    private String name;
    private String lastname;
    private int identityDocument;
    private String phone;
    private LocalDate birthdate;
    private String email;
    private String password;
    private RoleModel role;

    public UserModel() {}

    public UserModel(int id, String name, String lastname, int identityDocument, String phone, LocalDate birthdate, String email, String password, RoleModel role) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.identityDocument = identityDocument;
        this.phone = phone;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(int identityDocument) {
        this.identityDocument = identityDocument;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
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

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }
}

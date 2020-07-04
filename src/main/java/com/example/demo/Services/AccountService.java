package com.example.demo.Services;

import com.example.demo.Entities.Role;

import com.example.demo.Entities.Utilisateur;

public interface AccountService {
    public Utilisateur addUser(Utilisateur user);
    public Role addRole(Role roles);
    public Utilisateur findUserByUser(String codeUser);
    public  void  AddRoles(String username,String rolename);
    public  void AddAgenceUser(String agencename,String username);
}

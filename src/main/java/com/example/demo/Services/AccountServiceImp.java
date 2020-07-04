package com.example.demo.Services;

import com.example.demo.DAO.AgenceRepository;
import com.example.demo.DAO.RolesRepository;
import com.example.demo.DAO.UtilisateurRepository;
import com.example.demo.Entities.Agence;
import com.example.demo.Entities.Role;
import com.example.demo.Entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImp implements AccountService{
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private AgenceRepository agenceRepository;
    @Override
    public Utilisateur addUser(Utilisateur user) {
        String passwdBrpt=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwdBrpt);
        return utilisateurRepository.save(user);
    }

    @Override
    public Role addRole(Role roles) {
        return rolesRepository.save(roles);
    }

    @Override
    public Utilisateur findUserByUser(String codeUser) {
        return utilisateurRepository.findByUsername(codeUser);
    }

    @Override
    public void AddRoles(String username, String rolename) {
        Role role=rolesRepository.findByRoleName(rolename);
        Utilisateur utilisateur=utilisateurRepository.findByUsername(username);
        utilisateur.getRoles().add(role);

    }

    @Override
    public void AddAgenceUser(String agencename, String username) {
        Agence agence=agenceRepository.findByNomAgence(agencename);
        Utilisateur utilisateur=utilisateurRepository.findByUsername(username);
        agence.getUtilisateurs().add(utilisateur);


    }
}

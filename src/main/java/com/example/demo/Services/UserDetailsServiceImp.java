package com.example.demo.Services;

import com.example.demo.Entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
@Service

public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private  AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur=accountService.findUserByUser(username);
        if (utilisateur==null) throw  new UsernameNotFoundException(username);
        Collection<GrantedAuthority>authorities=new ArrayList<>();
        utilisateur.getRoles().forEach(r->{
            authorities.add( new SimpleGrantedAuthority(r.getRoleName()));
        });
        return new User(utilisateur.getUsername(),utilisateur.getPassword(),authorities);
    }
}

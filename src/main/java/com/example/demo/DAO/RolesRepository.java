package com.example.demo.DAO;


import com.example.demo.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role,String> {
    public  Role  findByRoleName(String code);
}

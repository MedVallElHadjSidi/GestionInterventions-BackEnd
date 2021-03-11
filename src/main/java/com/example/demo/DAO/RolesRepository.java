package com.example.demo.DAO;


import com.example.demo.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolesRepository extends JpaRepository<Role,Long> {
    public  Role  findByRoleName(String code);
    @Query("select r.roleName from Role r ")
    public List<String> RolesName();
}

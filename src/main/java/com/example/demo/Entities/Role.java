package com.example.demo.Entities;


import lombok.*;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Role;
    @Column(unique = true)
    private  String roleName;

}

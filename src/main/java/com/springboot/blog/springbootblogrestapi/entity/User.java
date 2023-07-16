package com.springboot.blog.springbootblogrestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )  // id tăng tự động
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // load user thì load roles luon
    @JoinTable(name="users_roles", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id")
    , inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id")
    )
    private Set<Role> roles;
}

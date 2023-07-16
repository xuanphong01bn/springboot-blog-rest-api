package com.springboot.blog.springbootblogrestapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})} // ten khong duoc giong nhau
)
public class Post {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )  // id tăng tự động
    private Long id;

    @Column(name="title", nullable = false)
    private String title;
    @Column(name="description", nullable = false)
    private String description;
    @Column(name="content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) // cascade all : thay doi o post se anh huong toi comemnt tuong ung
    //orphanRemoval: comment nao ko lien ket voi bai dang thi tu dong xoa
    private Set<Comment> comments = new HashSet<>();

}

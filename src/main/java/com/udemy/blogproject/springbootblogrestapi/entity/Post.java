package com.udemy.blogproject.springbootblogrestapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Posts", uniqueConstraints = {@UniqueConstraint(columnNames = "title")})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;
    @Column(nullable = false, length = 100)
    private String description;
    @Column(nullable = false, length = 250)
    private String content;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) // mapped by post, all operations are affected by parent into child because of CascadeType.ALL
   private Set<Comment>comments= new HashSet<>();  // no duplicate values in Set interface

}

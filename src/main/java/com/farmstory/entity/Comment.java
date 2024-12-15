package com.farmstory.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    private int parent;
    private String content;
    //private String writer;
    private String regip;

    @CreationTimestamp
    private LocalDateTime date;
    private String csParent;



    //추가필드
    @Transient
    private String nick;

    @Transient
    private LocalDateTime subStringRdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="writer")
    @OnDelete(action= OnDeleteAction.SET_NULL)
    private User user;







}

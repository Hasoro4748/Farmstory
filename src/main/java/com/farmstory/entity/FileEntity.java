package com.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="file")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fno;

    private int ano;
    private String oName;
    private String sName;
    private int download;
    private String csNo;
    @CreationTimestamp
    private LocalDateTime rdate;

    public int setDownload() {

        return download++;
    }
}

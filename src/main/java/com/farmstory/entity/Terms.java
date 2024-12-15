package com.farmstory.entity;

import com.farmstory.dto.TermsDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="terms")
public class Terms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "LongText")
    private String terms;
    @Column(columnDefinition = "LongText")
    private String privacy;


    public TermsDTO toDTO(){
        return TermsDTO.builder()
                .terms(terms)
                .privacy(privacy)
                .build();
    }
}

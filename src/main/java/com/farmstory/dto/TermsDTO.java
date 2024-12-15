package com.farmstory.dto;

import com.farmstory.entity.Terms;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermsDTO {

    private String terms;
    private String privacy;

    public Terms toEntity() {
        return Terms.builder()
                .terms(terms)
                .privacy(privacy)
                .build();
    }
}

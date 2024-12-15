package com.farmstory.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PassResetDTO {
    private String newpass;
}
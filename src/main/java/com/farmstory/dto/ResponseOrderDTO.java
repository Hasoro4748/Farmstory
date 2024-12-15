package com.farmstory.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrderDTO {

    private UserDTO userDTO;
    private List<CartDTO> cartDTOList;

}
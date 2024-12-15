package com.farmstory.dto;


import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {

    private int fno;
    private int ano;
    private String oName;
    private String sName;
    private int download;
    private String rdate;
    private String csNo;


}

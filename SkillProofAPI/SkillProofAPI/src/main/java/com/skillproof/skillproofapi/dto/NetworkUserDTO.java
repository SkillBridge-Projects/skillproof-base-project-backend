package com.skillproof.skillproofapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NetworkUserDTO {
    private Long id;
    private String name;
    private String surname;
    private String position;
    private String company;

    // IMAGE MISSING
    // private Picture picture;
}

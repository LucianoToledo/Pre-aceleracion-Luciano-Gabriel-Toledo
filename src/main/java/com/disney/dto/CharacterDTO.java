package com.disney.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterDTO {
    private String id;
    private String name;
    private Integer age;
    private Float weight;
    private String history;
    private String image;
}

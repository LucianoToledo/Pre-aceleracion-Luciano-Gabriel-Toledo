package com.disney.dto.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterRequest {
    private String id;
    @NotNull
    private String name;
    @NotNull
    private Integer age;
    @NotNull
    private Float weight;
    //@NotNull
    private String history;
    //@NotNull
    private String image;
}

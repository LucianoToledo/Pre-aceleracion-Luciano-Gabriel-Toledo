package com.disney.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResponse {
    private String id;
    private String tittle;
    private Integer ranking;
    private String image;
}

package com.disney.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRequest {
    private String id;
    private String title;
    private Integer ranking;
    private String image;
}

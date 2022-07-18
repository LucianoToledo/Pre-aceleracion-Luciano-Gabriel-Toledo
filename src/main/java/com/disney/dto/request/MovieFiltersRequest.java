package com.disney.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class MovieFiltersRequest {
    private String title;
    private String date;
    private Set<String> characters;
    private String order;

    public boolean isASC(){
        return this.order.compareToIgnoreCase("ASC") == 0;
    }
    public boolean isDESC(){
        return this.order.compareToIgnoreCase("DESC") == 0;
    }
}

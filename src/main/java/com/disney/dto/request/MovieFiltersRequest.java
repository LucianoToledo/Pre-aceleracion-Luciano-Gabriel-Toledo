package com.disney.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieFiltersRequest {
    private String title;
    private Set<String> characters;
    private String order;
    public boolean isASC(){return this.order.compareToIgnoreCase("ASC") == 0; }
    public boolean isDESC(){
        return this.order.compareToIgnoreCase("DESC") == 0;
    }
}

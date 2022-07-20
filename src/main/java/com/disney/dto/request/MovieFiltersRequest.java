package com.disney.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class MovieFiltersRequest {
    private String title;
    private String date;
    private List<String> characters;
    private String order;

    public MovieFiltersRequest(String title, String date, List<String> characters, String order) {
        this.title = title;
        this.date = date;
        this.characters = characters;
        this.order = order;
    }

    public boolean isASC(){return this.order.compareToIgnoreCase("ASC") == 0; }
    public boolean isDESC(){
        return this.order.compareToIgnoreCase("DESC") == 0;
    }
}

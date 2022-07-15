package com.disney.mapper;

import com.disney.dto.request.MovieRequest;
import com.disney.dto.response.MovieResponse;
import com.disney.entity.MovieEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieMapper {

    public MovieEntity map(MovieRequest request) {
        MovieEntity entity = new MovieEntity();
        entity.setTitle(request.getTittle());
        entity.setRanking(request.getRanking());
        entity.setImage(request.getImage());
        return entity;
    }

    public MovieResponse map(MovieEntity entity) {
        MovieResponse response = new MovieResponse();
        response.setId(entity.getId());
        response.setTittle(entity.getTitle());
        response.setRanking(entity.getRanking());
        response.setImage(response.getImage());
        return response;
    }

    public List<MovieResponse> map(List<MovieEntity> entities) {
        List<MovieResponse> responseList = new ArrayList<>();
        for (MovieEntity entity : entities) {
            responseList.add(map(entity));
        }
        return responseList;
    }

    public MovieEntity map(MovieRequest request , MovieEntity entity){
        return map(request);
    }
}

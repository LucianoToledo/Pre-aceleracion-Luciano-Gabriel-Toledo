package com.disney.controller;

import com.disney.dto.request.MovieRequest;
import com.disney.dto.response.MovieBasicResponse;
import com.disney.dto.response.MovieResponse;
import com.disney.exception.EntityNotFound;
import com.disney.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    IMovieService iMovieService;

    @PostMapping("/save")
    public ResponseEntity<MovieResponse> save(@RequestBody MovieRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iMovieService.save(request));
    }

    @PutMapping("/update")
    public ResponseEntity<MovieResponse> update(@RequestBody MovieRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iMovieService.update(request));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<Void> enable(@PathVariable String id) {
        try {
            iMovieService.enableMovie(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<Void> disable(@PathVariable String id) {
        try {
            iMovieService.disableMovie(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/allDetails")
    public ResponseEntity<List<MovieResponse>> getAll() {
        try {
            return ResponseEntity.ok().body(iMovieService.getAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addCharacter/{idVideo}/character/{idCharacter}")
    public ResponseEntity<MovieResponse> addCharacters(@PathVariable String idVideo, @PathVariable String idCharacter) {
        try {
            return ResponseEntity.ok().body(iMovieService.addCharacter(idVideo, idCharacter));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/removeCharacter/{idVideo}/character/{idCharacter}")
    public ResponseEntity<MovieResponse> removeCharacter(@PathVariable String idVideo, @PathVariable String idCharacter) {
        try {
            return ResponseEntity.ok().body(iMovieService.removeCharacter(idVideo, idCharacter));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addGenre/{idVideo}/genre/{idGenre}")
    public ResponseEntity<MovieResponse> addGenre(@PathVariable String idVideo, @PathVariable String idGenre) {
        try {
            return ResponseEntity.ok().body(iMovieService.addGenre(idVideo, idGenre));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<List<MovieBasicResponse>> getByQuery(HttpServletRequest query) {
        try {
            Enumeration enumeration = query.getParameterNames();
            Map<String, String> modelMap = new HashMap<>();
            while (enumeration.hasMoreElements()) {
                String parameterName = (String) enumeration.nextElement();
                modelMap.put(parameterName, query.getParameter(parameterName));
            }
            return ResponseEntity.ok().body(iMovieService.getByQuery(modelMap));
        } catch (EntityNotFound e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

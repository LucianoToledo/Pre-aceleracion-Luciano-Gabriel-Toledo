package com.disney.controller;

import com.disney.dto.request.MovieRequest;
import com.disney.dto.response.MovieResponse;
import com.disney.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(iMovieService.update(request));
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<Void> enable(@PathVariable String id) throws Exception {
        iMovieService.enableMovie(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<Void> disable(@PathVariable String id) throws Exception {
        iMovieService.disableMovie(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<MovieResponse>> getAll() {
        return ResponseEntity.ok().body(iMovieService.getAll());
    }

    @PostMapping("/addCharacter/{idVideo}/character/{idCharacter}")
    public ResponseEntity<MovieResponse> addCharacters(@PathVariable String idVideo, @PathVariable String idCharacter) throws Exception {
        return ResponseEntity.ok().body(iMovieService.addCharacter(idVideo, idCharacter));
    }
    @DeleteMapping("/removeCharacter/{idVideo}/character/{idCharacter}")
    public ResponseEntity<MovieResponse> removeCharacter(@PathVariable String idVideo, @PathVariable String idCharacter) throws Exception {
        return ResponseEntity.ok().body(iMovieService.removeCharacter(idVideo, idCharacter));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getByTitle(@RequestParam String title){
        return ResponseEntity.ok().body(iMovieService.findByTitle(title));
    }
//    @GetMapping
//    public ResponseEntity<List<MovieResponse>> getDetailsByFilters(
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) String date,
//            @RequestParam(required = false) List<String> characters,
//            @RequestParam(required = false, defaultValue = "ASC") String order) {
//        List<MovieResponse> responses = iMovieService.getByFilters(title, date, characters, order);
//        return ResponseEntity.ok(responses);
//    }
}

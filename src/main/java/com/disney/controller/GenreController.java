package com.disney.controller;

import com.disney.dto.request.GenreRequest;
import com.disney.dto.response.GenreResponse;
import com.disney.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

    @Autowired
    private IGenreService iGenreService;

    @PostMapping("/save")
    public ResponseEntity<GenreResponse> save(@RequestBody GenreRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iGenreService.save(request));
    }

    @PutMapping("/update")
    public ResponseEntity<GenreResponse> update(@RequestBody GenreRequest request){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iGenreService.update(request));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<GenreResponse> enable(@PathVariable String id)  {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iGenreService.enableGenre(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<GenreResponse> disable(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iGenreService.disableGenre(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<GenreResponse>> getAll() {
        return ResponseEntity.ok().body(iGenreService.getAllGenres());
    }
}

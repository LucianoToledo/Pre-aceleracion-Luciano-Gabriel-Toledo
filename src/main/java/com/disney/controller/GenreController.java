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
    public ResponseEntity<GenreResponse> save(@RequestBody GenreRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(iGenreService.save(request));
    }

    @PutMapping("/update")
    public ResponseEntity<GenreResponse> update(@RequestBody GenreRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(iGenreService.update(request));
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<Void> enable(@PathVariable String id) throws Exception {
        iGenreService.enableGenre(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<Void> disable(@PathVariable String id) throws Exception {
        iGenreService.disableGenre(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<GenreResponse>> getAll(){
        return ResponseEntity.ok().body(iGenreService.getAllGenres());
    }
}

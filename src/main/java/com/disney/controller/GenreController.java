package com.disney.controller;

import com.disney.dto.GenreDTO;
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
    public ResponseEntity<GenreDTO> save(@RequestBody GenreDTO request){
        GenreDTO response = iGenreService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<GenreDTO>> getAll(){
        List<GenreDTO> responseList = iGenreService.getAllGenres();
        return ResponseEntity.ok().body(responseList);
    }
}

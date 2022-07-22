package com.disney.controller;

import com.disney.dto.request.CharacterRequest;
import com.disney.dto.response.CharacterBasicResponse;
import com.disney.dto.response.CharacterResponse;
import com.disney.service.ICharaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    ICharaterService iCharaterService;


    @PostMapping("/save")
    public ResponseEntity<CharacterResponse> save(@RequestBody CharacterRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iCharaterService.save(request));
    }

    @PutMapping("/update")
    public ResponseEntity<CharacterResponse> update(@RequestBody CharacterRequest request) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(iCharaterService.update(request));
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<Void> enable(@PathVariable String id) throws Exception {
        iCharaterService.enableCharacter(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<Void> disable(@PathVariable String id) throws Exception {
        iCharaterService.disableCharacter(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<CharacterResponse>> getAll() {
        return ResponseEntity.ok().body(iCharaterService.getAll());
    }

    @GetMapping("/name")
    public ResponseEntity<List<CharacterBasicResponse>> getByName(@RequestParam String name) {
        return ResponseEntity.ok().body(iCharaterService.getByName(name));
    }

    @GetMapping("/age")
    public ResponseEntity<List<CharacterBasicResponse>> getByAge(@RequestParam String age) {
        return ResponseEntity.ok().body(iCharaterService.getByAge(age));
    }

    @GetMapping("/movieId")
    public ResponseEntity<List<CharacterBasicResponse>> getByMovieId(@RequestParam String movieId) {
        return ResponseEntity.ok().body(iCharaterService.getByMovieId(movieId));
    }


}


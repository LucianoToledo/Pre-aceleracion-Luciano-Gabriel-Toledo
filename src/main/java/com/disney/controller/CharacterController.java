package com.disney.controller;

import com.disney.dto.CharacterBasicDTO;
import com.disney.dto.CharacterDTO;
import com.disney.service.ICharaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    ICharaterService iCharaterService;

    @PostMapping("/save")
    public ResponseEntity<CharacterDTO> save(@RequestBody CharacterBasicDTO request) {
        CharacterDTO response = iCharaterService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<CharacterDTO> update(@RequestBody CharacterDTO request) {
        CharacterDTO response = iCharaterService.update(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
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

}


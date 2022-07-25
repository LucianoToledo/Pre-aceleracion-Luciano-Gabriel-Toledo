package com.disney.controller;

import com.disney.dto.request.CharacterRequest;
import com.disney.dto.response.CharacterBasicResponse;
import com.disney.dto.response.CharacterResponse;
import com.disney.exception.EntityNotFound;
import com.disney.service.ICharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    ICharacterService iCharacterService;


    @PostMapping("/save")
    public ResponseEntity<CharacterResponse> save(@RequestBody CharacterRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(iCharacterService.save(request));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CharacterResponse> update(@RequestBody CharacterRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iCharacterService.update(request));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<Void> enable(@PathVariable String id) {
        try {
            iCharacterService.enableCharacter(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<Void> disable(@PathVariable String id) {
        try {
            iCharacterService.disableCharacter(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/allDetails")
    public ResponseEntity<List<CharacterResponse>> getAll() {
        return ResponseEntity.ok().body(iCharacterService.getAll());
    }

    @GetMapping
    public ResponseEntity<List<CharacterBasicResponse>> getByQuery(HttpServletRequest query) {
        try {
            Enumeration enumeration = query.getParameterNames();
            Map<String, String> modelMap = new HashMap<>();
            while (enumeration.hasMoreElements()) {
                String parameterName = (String) enumeration.nextElement();
                modelMap.put(parameterName, query.getParameter(parameterName));
            }
            return ResponseEntity.ok().body(iCharacterService.getByQuery(modelMap));
        } catch (EntityNotFound e) {
            throw new RuntimeException(e);
        }
    }
}


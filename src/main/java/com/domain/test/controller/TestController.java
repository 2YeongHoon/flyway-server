package com.domain.test.controller;

import com.domain.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TestController {

    private final TestService testService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/run")
    public ResponseEntity<Void> roadTest() {
        testService.run();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @GetMapping(value = "/check")
    public ResponseEntity<Void> check() {
        testService.checkSchema();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

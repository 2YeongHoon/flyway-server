package com.domain.test.controller;

import com.domain.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController(value = "/test")
public class TestController {

    private final TestService testService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Void> roadTest() {
        testService.loadDate();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

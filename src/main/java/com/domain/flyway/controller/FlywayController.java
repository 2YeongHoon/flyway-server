package com.domain.flyway.controller;

import com.domain.flyway.service.FlywayService;
import com.domain.flyway.service.vo.ResultVo;
import java.util.Map;
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
public class FlywayController {

    private final FlywayService flywayService;

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @GetMapping(value = "/migrate")
    public ResponseEntity<Void> migrate() {
        flywayService.migrate();
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping(value = "/road")
    public ResponseEntity<Void> road() {
        flywayService.road();
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @GetMapping(value = "/check")
    public ResponseEntity<Void> check() {
        flywayService.checkSchema();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/check-default")
    public ResponseEntity<Map<String, ResultVo>> checkDefault() {
        return ResponseEntity.ok(
            flywayService.getDefaultSchemaStatus());
    }

}

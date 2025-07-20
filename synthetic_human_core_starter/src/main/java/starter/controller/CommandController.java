package starter.controller;

import starter.model.Command;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/commands")
public class CommandController {
    @PostMapping
    public ResponseEntity<Void> submitCommand(@Valid @RequestBody Command command) {
        return ResponseEntity.ok().build();
    }
}
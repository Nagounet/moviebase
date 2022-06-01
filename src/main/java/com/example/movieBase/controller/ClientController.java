package com.example.movieBase.controller;

import com.example.movieBase.model.Client;
import com.example.movieBase.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public ResponseEntity<List<Client>> getClients() {
        return ResponseEntity.ok(clientService.getClients());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.findClientById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/getByName")
    public ResponseEntity<Client> getClientByName(@RequestParam String name) {
        Client client = clientService.findClientByName(name);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<Client> getClientByEmail(@RequestParam String email) {
        Client client = clientService.findClientByEmail(email);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/getByAge")
    public ResponseEntity<List<Client>> getClientByAge(@RequestParam Integer age) {
        List<Client> clientsByAge = clientService.findClientByAge(age);
        return ResponseEntity.ok(clientsByAge);
    }

    @PostMapping("/addClient")
    ResponseEntity<Client> addClient(@RequestBody @Valid Client client) throws URISyntaxException {
        Client savedClient = clientService.addClient(client);
        return ResponseEntity.created(new URI("/getById/"+savedClient.getId())).body(savedClient);
    }

    @PatchMapping("/updateClient/{id}")
    public ResponseEntity<String> updateClient(@RequestBody Client client, @PathVariable Long id) {
        clientService.updateClient(client, id);
        return ResponseEntity.ok(clientService.findClientById(id).getName()+" updated");
    }

    @DeleteMapping(value="/deleteClient/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        String clientName = clientService.findClientById(id).getName();
        clientService.deleteClient(id);
        return ResponseEntity.ok(clientName+" with id "+id+" deleted");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

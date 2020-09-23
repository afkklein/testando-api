package com.afkklein.testandoapi.controller;

import com.afkklein.testandoapi.model.Cliente;
import com.afkklein.testandoapi.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
@Slf4j
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping
    List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{id}")
    Cliente getCliente(@PathVariable Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

}

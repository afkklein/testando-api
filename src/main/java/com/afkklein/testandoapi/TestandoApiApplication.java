package com.afkklein.testandoapi;

import com.afkklein.testandoapi.model.Cliente;
import com.afkklein.testandoapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TestandoApiApplication {

	@Autowired
	ClienteRepository clienteRepository;

	public static void main(String[] args) {
		SpringApplication.run(TestandoApiApplication.class, args);
	}

	@Bean
	void inicializarDados() {
		clienteRepository.saveAll(Arrays.asList(new Cliente(1L, "João", "Pardal"),
												new Cliente(2L, "Aarão", "Zwinglio")));
	}

}

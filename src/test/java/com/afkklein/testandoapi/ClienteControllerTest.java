package com.afkklein.testandoapi;

import com.afkklein.testandoapi.controller.ClienteController;
import com.afkklein.testandoapi.model.Cliente;
import com.afkklein.testandoapi.repository.ClienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClienteRepository clienteRepository;

    @Autowired
    ObjectMapper objectMapper;

    Cliente joao;
    Cliente aarao;
    String joaoJsonParse;
    String aaraoJsonParse;
    String requestUri = "/api/cliente";

    @Before
    public void setup() throws JsonProcessingException {
        joao = new Cliente(1L, "João", "Pardal");
        aarao = new Cliente(2L, "Aarão", "Zwinglio");
        joaoJsonParse = objectMapper.writeValueAsString(joao);
        aaraoJsonParse = objectMapper.writeValueAsString(aarao);
    }

    @Test
    public void getClientesDeveRetornarTodosOsClientes() throws Exception {
        //cenario
        when(clienteRepository.findAll())
                .thenReturn(Arrays.asList(joao, aarao));

        List<String> array = Arrays.asList(joaoJsonParse, aaraoJsonParse);
        String response = array.toString();

        //acao
        mockMvc.perform(get(requestUri))
                .andDo(print())
                .andExpect(content().json(response))
                .andExpect(status().isOk());

        //verificacao
        verify(clienteRepository).findAll();
    }

    @Test
    public void getClienteDeveRetornarClienteBuscado() throws Exception {
        //cenario
        when(clienteRepository
                .findById(2L))
                .thenReturn(Optional.of(aarao));

        //acao
        mockMvc.perform(get(requestUri + "/{id}", 2))
                .andDo(print())
                .andExpect(content().json(aaraoJsonParse.toString()))
                .andExpect(status().isOk());

        //verificacao
        verify(clienteRepository).findById(2L);
    }
}

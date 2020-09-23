package com.afkklein.testandoapi;

import com.afkklein.testandoapi.controller.ClienteController;
import com.afkklein.testandoapi.model.Cliente;
import com.afkklein.testandoapi.repository.ClienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Mockito.when(clienteRepository.findAll()).thenReturn(Arrays.asList(joao, aarao));

        List<String> array = Arrays.asList(joaoJsonParse, aaraoJsonParse);
        String response = array.toString();

        //acao
        mockMvc.perform(MockMvcRequestBuilders.get(requestUri))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json(response))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //verificacao
        Mockito.verify(clienteRepository).findAll();
    }

    @Test
    public void getClienteDeveRetornarClienteBuscado() throws Exception {
        //cenario
        Mockito.when(clienteRepository.findById(2L)).thenReturn(Optional.of(aarao));

        //acao
        mockMvc.perform(MockMvcRequestBuilders.get(requestUri + "/{id}", 2))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json(aaraoJsonParse.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //verificacao
        Mockito.verify(clienteRepository).findById(2L);
    }
}

package com.sofka.movimientos.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.movimientos.dto.ClientDTO;
import com.sofka.movimientos.entities.Client;
import com.sofka.movimientos.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ClientIntegrationTest {
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    //TEST BAJO DEPENDENCIA DE CREACION
//    @Test
//    void shouldCreateClient() throws Exception {
//
//        ClientDTO.createClient newClient = new ClientDTO.createClient();
//        newClient.setIdentificationCard("1234567890");
//        newClient.setName("John Doe");
//        newClient.setGender("M");
//        newClient.setAge(30L);
//        newClient.setDirection("123 Main St");
//        newClient.setPhone("555-1234");
//        newClient.setPassword("password123");
//
//
//        String jsonClient = objectMapper.writeValueAsString(newClient);
//
//
//        mockMvc.perform(post("/client")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonClient))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().string(org.hamcrest.Matchers.containsString("idClient")));
//    }

    @Test
    void shouldReturnListOfClients() throws Exception {

        ResultActions response = mockMvc.perform(get("/client")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());


        response.andExpect(status().isOk());
    }
}

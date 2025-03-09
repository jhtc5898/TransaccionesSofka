package com.sofka.movimientos;


import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class MovimientosApplicationTests {

//    @Autowired
//    MockMvc mock;
//
//    @Test
//    @Order(0)
//    void testClient() throws Exception {
//        mock.perform(get("/api/client").contentType(MediaType.APPLICATION_JSON_VALUE).content(
//                "{\n" +
//                        "        \"clientidentification\": \"JohnTenesaca\"\n" +
//                        "    }")).andDo(print());
//    }
//
//    @Test
//    @Order(1)
//    void testClient2() throws Exception {
//        mock.perform(get("/api/client").contentType(MediaType.APPLICATION_JSON_VALUE).content(
//                "{\n" +
//                        "        \"clientidentification\": \"SilviaPatricia\"\n" +
//                        "    }")).andDo(print());
//    }


}

package com.example.les15;

import com.example.les15.dto.OrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class OrderControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCreateCorrectOrder() throws Exception {

//        String requestJson = """
//                {
//                    "productname" : "Gibson gitaar",
//                    "unitprice" : 2399.00,
//                    "quantity" :  5
//                }
//                """;

        OrderDto orderDto = new OrderDto();
        orderDto.productname = "Gibson gitaar";
        orderDto.unitprice = 2399.0;
        orderDto.quantity = 5;

        ObjectMapper om = new ObjectMapper();
        String requestJson = om.writeValueAsString(orderDto);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/orders")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String createdId = result.getResponse().getContentAsString();

        // check location field in response header (using Hamcrest regex matcher)
        assertThat(result.getResponse().getHeader("Location"), matchesPattern("^.*/orders/" + createdId));
    }
}

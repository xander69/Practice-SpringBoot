package org.xander.practice.webapp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void main() throws Exception {
        mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div>Hello, World!</div>")));
    }

    @Test
    public void mainWithParam() throws Exception {
        mockMvc.perform(get("/main?name=Alexander"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div>Hello, Alexander!</div>")));
    }
}
package org.xander.practice.webapp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.xander.practice.webapp.service.ScenarioService;

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
    @MockBean
    private ScenarioService scenarioService;

    @Test
    public void main() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World!")));
    }

    @Test
    public void mainWithParam() throws Exception {
        mockMvc.perform(get("/?username=Alexander"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, Alexander!")));
    }

    @Test
    public void sysInfo() throws Exception {
        mockMvc.perform(get("/sysinfo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<td>Process ID:</td>")));
    }
}
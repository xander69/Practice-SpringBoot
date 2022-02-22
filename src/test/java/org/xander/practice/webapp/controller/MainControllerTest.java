package org.xander.practice.webapp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@TestPropertySource("/application-test.properties")
@Sql(
        value = {"/sql/create-user-before.sql", "/sql/scenario-list-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(
        value = {"/sql/scenario-list-after.sql", "/sql/create-user-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mainPageTest() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(xpath("//*[@id='navbarContent']/div/form/div").string("Welcome, admin!"));
    }

    @Test
    public void scenarioListTest() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(xpath("//*[@id='scenarioList']/div").nodeCount(5));
    }

    @Test
    public void filterScenarioTest() throws Exception {
        mockMvc.perform(get("/").param("filter", "3"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='scenarioList']/div").nodeCount(2))
                .andExpect(xpath("//div[@id='scenarioList']/div[@data-id=3]").exists())
                .andExpect(xpath("//div[@id='scenarioList']/div[@data-id=5]").exists());
    }

    @Test
    public void addScenario() throws Exception {
        MockHttpServletRequestBuilder multipart = MockMvcRequestBuilders.multipart("/")
                .file("icon", "123".getBytes(StandardCharsets.UTF_8))
                .param("name", "Scenario 10")
                .param("description", "Description of the scenario 10")
                .with(SecurityMockMvcRequestPostProcessors.csrf());
        mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='scenarioList']/div").nodeCount(6))
                .andExpect(xpath("//div[@id='scenarioList']/div[@data-id=10]").exists())
                .andExpect(xpath("//div[@data-id=10]/div/b").string("Scenario 10"))
                .andExpect(xpath("//div[@data-id=10]/div/i").string("Description of the scenario 10"));
    }
}
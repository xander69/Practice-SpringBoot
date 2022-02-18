package org.xander.practice.webapp.controller;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xander.practice.webapp.service.ScenarioService;

import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;

@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private final ScenarioService scenarioService;

    @Autowired
    public MainController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @GetMapping("/")
    public String main(
            @RequestParam(name = "username", required = false, defaultValue = "World") String username,
            Model model
    ) {
        model.addAttribute("username", username);
        model.addAttribute("scenarios", scenarioService.getAllScenarios());
        return "main";
    }

    @PostMapping("/")
    public String addScenario(@RequestParam(name = "name") String name,
                              @RequestParam(name = "descr") String description,
                              Model model) {
        Long scenarioId = scenarioService.createScenario(name, description);
        log.info("Created scenario with id=[{}]", scenarioId);
        return "redirect:/";
    }

    @GetMapping("/sysinfo")
    public String systemInfo(Model model) {
        model.addAttribute("params", Arrays.asList(
                Pair.of("Current Time", Instant.now()),
                Pair.of("Timestamp", System.currentTimeMillis()),
                Pair.of("Default Charset", Charset.defaultCharset().displayName()),
                Pair.of("Default Locale", Locale.getDefault().getDisplayName()),
                Pair.of("Default Timezone", TimeZone.getDefault().getDisplayName()),
                Pair.of("Process ID", ManagementFactory.getRuntimeMXBean().getName()),
                Pair.of("User Dir", System.getProperty("user.dir"))
        ));
        return "sysinfo";
    }
}

package org.xander.practice.webapp.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xander.practice.webapp.entity.Scenario;
import org.xander.practice.webapp.exception.InvalidValueException;
import org.xander.practice.webapp.security.AuthenticationFacade;
import org.xander.practice.webapp.service.ScenarioService;

import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private final ScenarioService scenarioService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public MainController(ScenarioService scenarioService,
                          AuthenticationFacade authenticationFacade) {
        this.scenarioService = scenarioService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/")
    public String main(Model model) {
        Authentication authentication = authenticationFacade.getAuthentication();
        model.addAttribute("username", authentication.getName());
        model.addAttribute("scenarios", scenarioService.getAllScenarios());
        return "main";
    }

    @PostMapping("/")
    public String addScenario(@RequestParam(name = "name") String name,
                              @RequestParam(name = "descr") String description,
                              Model model) {
        if (StringUtils.isBlank(name)) {
            throw new InvalidValueException("Scenario name cannot be empty");
        }
        if (StringUtils.isBlank(description)) {
            throw new InvalidValueException("Scenario description cannot be empty");
        }
        Long scenarioId = scenarioService.createScenario(name, description);
        log.info("Created scenario with id=[{}]", scenarioId);
        return "redirect:/";
    }

    @PostMapping("/filter")
    public String filterScenarios(@RequestParam(name = "filter") String filter, Model model) {
        Authentication authentication = authenticationFacade.getAuthentication();
        List<Scenario> scenarios;
        if (StringUtils.isNotBlank(filter)) {
            scenarios = scenarioService.filterScenarios(filter);
        } else {
            scenarios = scenarioService.getAllScenarios();
        }
        model.addAttribute("scenarios", scenarios);
        model.addAttribute("username", authentication.getName());
        return "main";
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

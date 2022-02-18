package org.xander.practice.webapp.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xander.practice.webapp.entity.Scenario;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.service.ScenarioService;
import org.xander.practice.webapp.service.UploadService;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private final ScenarioService scenarioService;
    private final UploadService uploadService;

    @Autowired
    public MainController(ScenarioService scenarioService,
                          UploadService uploadService) {
        this.scenarioService = scenarioService;
        this.uploadService = uploadService;
    }

    @GetMapping("/")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model) {
        final List<Scenario> scenarios;
        if (StringUtils.isBlank(filter)) {
            scenarios = scenarioService.getAllScenarios();
        } else {
            scenarios = scenarioService.filterScenarios(filter);
        }
        model.addAttribute("filter", filter);
        model.addAttribute("scenarios", scenarios);
        return "main";
    }

    @PostMapping("/")
    public String addScenario(@AuthenticationPrincipal User user,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "descr") String description,
                              @RequestParam(name = "icon") MultipartFile file) throws IOException {
        String iconFilename = null;
        if (Objects.nonNull(file) && StringUtils.isNotBlank(file.getOriginalFilename())) {
            Path iconFile = uploadService.saveIconToFile(file);
            iconFilename = iconFile.toFile().getName();
        }
        Scenario scenario = scenarioService.createScenario(name, description, user, iconFilename);
        log.info("Created scenario with id=[{}]", scenario.getId());
        return "redirect:/";
    }

    @GetMapping("/sysinfo")
    public String systemInfo(@AuthenticationPrincipal User user,
                             Model model) {
        model.addAttribute("username", user.getUsername());
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

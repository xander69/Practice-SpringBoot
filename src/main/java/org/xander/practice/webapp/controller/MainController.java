package org.xander.practice.webapp.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xander.practice.webapp.entity.Scenario;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.service.ScenarioService;
import org.xander.practice.webapp.util.ValidationHelper;

import javax.validation.Valid;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

@Slf4j
@Controller
public class MainController {

    private final ScenarioService scenarioService;

    @Autowired
    public MainController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @GetMapping("/")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        final Page<Scenario> scenarioPage;
        if (StringUtils.isBlank(filter)) {
            scenarioPage = scenarioService.getAllScenarios(pageable);
        } else {
            scenarioPage = scenarioService.filterScenarios(filter, pageable);
        }
        model.addAttribute("filter", filter);
        model.addAttribute("urlPage", "/");
        model.addAttribute("scenarioPage", scenarioPage);
        return "main";
    }

    @PostMapping("/")
    public String addScenario(@AuthenticationPrincipal User user,
                              @Valid Scenario scenario,
                              BindingResult bindingResult,
                              Model model,
                              @RequestParam("icon") MultipartFile file,
                              @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationHelper.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("scenario", scenario);
        } else {
            Scenario savedScenario = scenarioService.createScenario(scenario, file, user);
            log.info("Created scenario with id=[{}]", savedScenario.getId());
            model.addAttribute("scenario", null);
        }
        val scenarios = scenarioService.getAllScenarios(pageable);
        model.addAttribute("urlPage", "/");
        model.addAttribute("scenarioPage", scenarios);
        return "main";
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

    @GetMapping("/user-scenarios/{user}")
    public String userChannel(
            @RequestParam(required = false, defaultValue = "") String filter,
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @RequestParam(value = "scenario", required = false) Scenario scenario,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Scenario> scenarioPage;
        if (StringUtils.isBlank(filter)) {
            scenarioPage = scenarioService.getScenariosByCreator(user, pageable);
        } else {
            scenarioPage = scenarioService.filterScenariosByCreator(user, filter, pageable);
        }
        model.addAttribute("filter", filter);
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("urlPage", "/user-scenarios/" + user.getId());
        model.addAttribute("scenarioPage", scenarioPage);
        model.addAttribute("scenario", scenario);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        return "userChannel";
    }

    @PostMapping("/user-scenarios/{userId}")
    public String updateMessage(
            @PathVariable Long userId,
            @RequestParam(value = "id") Scenario scenario,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("icon") MultipartFile file) {
        scenarioService.updateScenario(scenario, name, description, file, userId);
        return "redirect:/user-scenarios/" + userId;
    }
}

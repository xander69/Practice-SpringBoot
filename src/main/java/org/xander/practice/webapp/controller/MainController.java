package org.xander.practice.webapp.controller;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Model model
    ) {
        model.addAttribute("name", name);
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

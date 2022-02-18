package org.xander.practice.webapp.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xander.practice.webapp.entity.Scenario;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.exception.InvalidValueException;
import org.xander.practice.webapp.repository.ScenarioRepository;

import java.util.Date;
import java.util.List;

@Service
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;

    @Autowired
    public ScenarioService(ScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }

    public List<Scenario> getAllScenarios() {
        return scenarioRepository.findAll();
    }

    public List<Scenario> filterScenarios(String name) {
        return scenarioRepository.findByNameContainingIgnoreCase(name);
    }

    public Scenario createScenario(String name, String description, User creator, String iconFilename) {
        if (StringUtils.isBlank(name)) {
            throw new InvalidValueException("Scenario name cannot be empty");
        }
        if (StringUtils.isBlank(description)) {
            throw new InvalidValueException("Scenario description cannot be empty");
        }
        Scenario scenario = new Scenario();
        scenario.setName(name);
        scenario.setDescription(description);
        scenario.setCreateDateTime(new Date());
        scenario.setChangeDateTime(new Date());
        scenario.setCreator(creator);
        scenario.setIconFilename(iconFilename);
        return scenarioRepository.save(scenario);
    }
}

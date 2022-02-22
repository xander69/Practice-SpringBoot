package org.xander.practice.webapp.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xander.practice.webapp.entity.Scenario;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.model.ScenarioModel;
import org.xander.practice.webapp.repository.ScenarioRepository;

import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Service
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final UploadService uploadService;

    @Autowired
    public ScenarioService(ScenarioRepository scenarioRepository,
                           UploadService uploadService) {
        this.scenarioRepository = scenarioRepository;
        this.uploadService = uploadService;
    }

    public Page<ScenarioModel> getAllScenarios(User user, Pageable pageable) {
        return scenarioRepository.findAll(user, pageable);
    }

    public Page<ScenarioModel> filterScenarios(String name, User user, Pageable pageable) {
        return scenarioRepository.findByNameContainingIgnoreCase(name, user, pageable);
    }

    public Scenario createScenario(Scenario scenario, MultipartFile file, User user)  {
        scenario.setCreator(user);
        scenario.setCreateDateTime(new Date());
        scenario.setChangeDateTime(new Date());
        scenario.setIconFilename(uploadIcon(file));
        return scenarioRepository.save(scenario);
    }

    public void updateScenario(Scenario scenario, String name, String description,
                               MultipartFile file, Long userId) {
        if (Objects.equals(scenario.getCreator().getId(), userId)) {
            if (StringUtils.isNotBlank(name)) {
                scenario.setName(name);
            }
            if (StringUtils.isNotBlank(description)) {
                scenario.setDescription(description);
            }
            if (StringUtils.isNotBlank(file.getOriginalFilename())) {
                scenario.setIconFilename(uploadIcon(file));
            }
            scenario.setChangeDateTime(new Date());
            scenarioRepository.save(scenario);
        }
    }

    private String uploadIcon(MultipartFile file) {
        if (Objects.nonNull(file) && StringUtils.isNotBlank(file.getOriginalFilename())) {
            Path iconFile = uploadService.saveIconToFile(file);
            return iconFile.toFile().getName();
        }
        return null;
    }

    public Page<ScenarioModel> getScenariosByCreator(User creator, User user, Pageable pageable) {
        return scenarioRepository.findByCreator(creator, user, pageable);
    }

    public Page<ScenarioModel> filterScenariosByCreator(String filter, User creator, User user, Pageable pageable) {
        return scenarioRepository.findByCreatorAndNameContainingIgnoreCase(filter, creator, user, pageable);
    }

    public void likeDislikeScenario(Scenario scenario, User user) {
        Set<User> likes = scenario.getLikes();
        if (likes.contains(user)) {
            likes.remove(user);
        } else {
            likes.add(user);
        }
    }
}

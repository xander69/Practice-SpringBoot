package org.xander.practice.webapp.model;

import lombok.Getter;
import lombok.ToString;
import org.xander.practice.webapp.entity.Scenario;
import org.xander.practice.webapp.entity.User;

import java.util.Date;

@Getter
@ToString(of = {"id", "name", "creator", "likes", "meLiked"})
public class ScenarioModel {
    private final Long id;
    private final String name;
    private final String description;
    private final Date createDateTime;
    private final Date changeDateTime;
    private final User creator;
    private final String iconFilename;
    private final Long likes;
    private final Boolean meLiked;

    public ScenarioModel(Scenario scenario, Long likes, Boolean meLiked) {
        this.id = scenario.getId();
        this.name = scenario.getName();
        this.description = scenario.getDescription();
        this.createDateTime = scenario.getCreateDateTime();
        this.changeDateTime = scenario.getChangeDateTime();
        this.iconFilename = scenario.getIconFilename();
        this.creator = scenario.getCreator();
        this.likes = likes;
        this.meLiked = meLiked;
    }
}

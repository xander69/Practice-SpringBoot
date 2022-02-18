package org.xander.practice.webapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.xander.practice.webapp.entity.Scenario;

import java.util.List;

public interface ScenarioRepository extends CrudRepository<Scenario, Long> {
    @Override
    List<Scenario> findAll();

    List<Scenario> findByNameContainingIgnoreCase(String name);
}

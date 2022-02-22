package org.xander.practice.webapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.xander.practice.webapp.entity.Scenario;
import org.xander.practice.webapp.entity.User;

import java.util.List;

public interface ScenarioRepository extends CrudRepository<Scenario, Long> {
    @Override
    List<Scenario> findAll();

    Page<Scenario> findAll(Pageable pageable);

    Page<Scenario> findByCreator(User user, Pageable pageable);

    List<Scenario> findByNameContainingIgnoreCase(String name);

    Page<Scenario> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Scenario> findByCreatorAndNameContainingIgnoreCase(User user, String name, Pageable pageable);
}

package org.xander.practice.webapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.xander.practice.webapp.entity.Scenario;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.model.ScenarioModel;

import java.util.List;

public interface ScenarioRepository extends CrudRepository<Scenario, Long> {
    @Override
    List<Scenario> findAll();

    @Query("select new org.xander.practice.webapp.model.ScenarioModel(" +
            "  s, count(sl), sum(case when sl = :user then 1 else 0 end) > 0) " +
            "from Scenario s left join s.likes sl " +
            "group by s")
    Page<ScenarioModel> findAll(@Param("user") User user,
                                Pageable pageable);

    @Query("select new org.xander.practice.webapp.model.ScenarioModel(" +
            "  s, count(sl), sum(case when sl = :user then 1 else 0 end) > 0) " +
            "from Scenario s left join s.likes sl " +
            "where s.creator = :creator " +
            "group by s")
    Page<ScenarioModel> findByCreator(@Param("creator") User creator,
                                      @Param("user") User user,
                                      Pageable pageable);

    List<Scenario> findByNameContainingIgnoreCase(String name);

    @Query("select new org.xander.practice.webapp.model.ScenarioModel(" +
            "  s, count(sl), sum(case when sl = :user then 1 else 0 end) > 0) " +
            "from Scenario s left join s.likes sl " +
            "where upper(s.name) like upper(concat('%', :filter, '%')) " +
            "group by s")
    Page<ScenarioModel> findByNameContainingIgnoreCase(@Param("filter") String filter,
                                                       @Param("user") User user,
                                                       Pageable pageable);

    @Query("select new org.xander.practice.webapp.model.ScenarioModel(" +
            "  s, count(sl), sum(case when sl = :user then 1 else 0 end) > 0) " +
            "from Scenario s left join s.likes sl " +
            "where s.creator = :creator " +
            "      and upper(s.name) like upper(concat('%', :filter, '%')) " +
            "group by s")
    Page<ScenarioModel> findByCreatorAndNameContainingIgnoreCase(@Param("filter") String filter,
                                                                 @Param("creator") User creator,
                                                                 @Param("user") User user,
                                                                 Pageable pageable);
}

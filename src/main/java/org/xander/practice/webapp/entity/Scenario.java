package org.xander.practice.webapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "SCENARIO")
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCR")
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DT")
    private Date createDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHANGE_DT")
    private Date changeDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Date getChangeDateTime() {
        return changeDateTime;
    }

    public void setChangeDateTime(Date changeDateTime) {
        this.changeDateTime = changeDateTime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Scenario [");
        if (Objects.nonNull(getId())) {
            builder.append("id=");
            builder.append(getId());
        }
        builder.append("]");
        return builder.toString();
    }
}

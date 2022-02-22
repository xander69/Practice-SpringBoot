package org.xander.practice.webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "SCENARIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scenario_seq")
    @SequenceGenerator(name = "scenario_seq", sequenceName = "scenario_sequence", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    @NonNull
    @NotBlank(message = "Fill the scenario name")
    @Length(max = 255, message = "Scenario name to long (max 255 symbols)")
    private String name;
    @Column(name = "DESCR")
    @NonNull
    @NotBlank(message = "Fill the scenario description")
    @Length(max = 2000, message = "Scenario description to long (max 2000 symbols)")
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DT")
    @NonNull
    private Date createDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHANGE_DT")
    @NonNull
    private Date changeDateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    @NonNull
    private User creator;
    @Column(name = "ICON_FILENAME")
    private String iconFilename;

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

package com.zvm.bug.entities;

import com.zvm.bug.enums.BugState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_bug_generator")
    @GenericGenerator(
            name = "sequence_bug_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "bug_id_seq"),
                    @Parameter(name = "initial_value", value = "1000"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Integer id;
    private Integer taskId;
    private Integer createdByUserId;
    private String name;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private BugState bugState;
}

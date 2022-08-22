package com.zvm.task.entities;

import com.zvm.task.enums.TaskState;
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
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_task_generator")
    @GenericGenerator(
            name = "sequence_task_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "task_id_seq"),
                    @Parameter(name = "initial_value", value = "1000"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Integer id;
    private Integer createdByUserId;
    private Integer devId;
    private Integer testerId;
    private Integer featureId;
    private String name;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private TaskState taskState;
}

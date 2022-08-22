package com.zmv.feature.entities;

import com.zmv.feature.enums.FeatureState;
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
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_feature_generator")
    @GenericGenerator(
            name = "sequence_feature_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "feature_id_seq"),
                    @Parameter(name = "initial_value", value = "1000"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Integer id;

    private Integer createdByUserId;

    private Integer devId;

    @Enumerated(value = EnumType.STRING)
    private FeatureState featureState;

    private String name;

    private String description;
}

package com.zmv.feature.mappers;

import com.zmv.feature.dto.FeatureDto;
import com.zmv.feature.entities.Feature;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-22T14:28:08+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class FeatureMapperImpl implements FeatureMapper {

    @Override
    public FeatureDto toFeatureDto(Feature feature) {
        if ( feature == null ) {
            return null;
        }

        Integer id = null;
        Integer createdByUserId = null;
        Integer devId = null;
        String featureState = null;
        String name = null;
        String description = null;

        id = feature.getId();
        createdByUserId = feature.getCreatedByUserId();
        devId = feature.getDevId();
        if ( feature.getFeatureState() != null ) {
            featureState = feature.getFeatureState().name();
        }
        name = feature.getName();
        description = feature.getDescription();

        FeatureDto featureDto = new FeatureDto( id, createdByUserId, devId, featureState, name, description );

        return featureDto;
    }

    @Override
    public List<FeatureDto> toFeatureDto(List<Feature> features) {
        if ( features == null ) {
            return null;
        }

        List<FeatureDto> list = new ArrayList<FeatureDto>( features.size() );
        for ( Feature feature : features ) {
            list.add( toFeatureDto( feature ) );
        }

        return list;
    }
}

package com.zmv.feature.repsitories;

import com.zmv.feature.entities.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {

    List<Feature> findAllByDevId(Integer devId);

    List<Feature> findAllByCreatedByUserId(Integer userId);
}

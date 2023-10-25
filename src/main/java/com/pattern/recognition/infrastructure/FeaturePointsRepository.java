package com.pattern.recognition.infrastructure;

import com.pattern.recognition.domain.FeaturesPoint;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.CrudRepository;

public interface FeaturePointsRepository extends CrudRepository<FeaturesPoint, Point> {
}

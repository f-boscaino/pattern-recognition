package com.pattern.recognition.domain;

import org.springframework.data.geo.Point;

import java.util.List;

public interface FeaturePointsServicePort {
    List<Point> toPoints(Iterable<FeaturesPoint> all);
}

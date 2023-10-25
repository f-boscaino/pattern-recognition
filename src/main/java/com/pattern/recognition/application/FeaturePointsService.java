package com.pattern.recognition.application;

import com.pattern.recognition.domain.FeaturePointsServicePort;
import com.pattern.recognition.domain.FeaturesPoint;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeaturePointsService implements FeaturePointsServicePort {

    public List<Point> toPoints(Iterable<FeaturesPoint> featurePointsList) {
        List<Point> returnValue = new ArrayList<>();
        featurePointsList.forEach(featurePoint -> returnValue.add(featurePoint.getPoint()));
        return returnValue;
    }
}

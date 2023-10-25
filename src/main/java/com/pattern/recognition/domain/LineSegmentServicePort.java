package com.pattern.recognition.domain;

import org.springframework.data.geo.Point;

import java.util.List;

public interface LineSegmentServicePort {
    List<Segment> findLines(Point[] points, Integer minAmountOfIntersections);
}

package com.pattern.recognition.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.*;

@Data
@NoArgsConstructor
public class Segment {
    private Point left;
    private Point right;
    @JsonIgnore
    private Double slope;
    private Set<Point> intersections = new HashSet<>();

    public Segment(Point p1, Point p2) {
        if(p1.getX() < p2.getX()){
            left = p1;
            right = p2;
        } else {
            left = p2;
            right = p1;
        }
        slope = getSlope(left, right);
        intersections.add(p1);
        intersections.add(p2);
    }

    private Double getSlope(Point p1, Point p2) {
        double slope = Double.POSITIVE_INFINITY;
        if (p1.getX() != p2.getX()) {
            slope = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
        }
        return slope;
    }

    public boolean isAdjacent(Segment otherSegment) {
        return right.equals(otherSegment.getLeft());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return left.equals(segment.left) && right.equals(segment.right);
    }

}

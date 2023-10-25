package com.pattern.recognition.domain;

import lombok.*;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

@Data
public class LineSegment implements Comparable<LineSegment> {
    private Point left;
    private Point right;
    private List<Point> points;

    public LineSegment(Point left, Point right) {
        this.left = left;
        this.right = right;
        this.points = new ArrayList<>();
    }

    public boolean contains(Point p) {
        double minX = Math.min(left.getX(), right.getX());
        double maxX = Math.max(left.getX(), right.getX());
        double minY = Math.min(left.getY(), right.getY());
        double maxY = Math.max(left.getY(), right.getY());
        return p.getX() >= minX && p.getX() <= maxX && p.getY() >= minY && p.getY() <= maxY;
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public int compareTo(LineSegment other) {
        double slope1 = (right.getY() - left.getY()) / (right.getX() - left.getX());
        double slope2 = (other.right.getY() - other.left.getY()) / (other.right.getX() - other.left.getX());
        return Double.compare(slope1, slope2);
    }

}

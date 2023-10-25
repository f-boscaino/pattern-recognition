package com.pattern.recognition.application;

import com.pattern.recognition.domain.LineSegment;
import org.springframework.data.geo.Point;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LineSegmentService {
    public List<LineSegment> findLineSegments(Point[] points, int numberOfPoints) {
        List<LineSegment> result = new ArrayList<>();
        List<LineSegment> segments = new ArrayList<>();

        // Create all possible line segments
        generateAllLineSegmentsCombinations(points, segments);

        // Sort line segments by slope
        Collections.sort(segments);

        TreeSet<LineSegment> activeSegments = new TreeSet<>();

        // Process each point
        for (Point point : points) {
            // Remove inactive segments
            removeInactiveSegments(activeSegments, point);

            // Add new segments
            for (LineSegment segment : segments) {
                if (segment.getLeft().equals(point)) {
                    activeSegments.add(segment);
                }
            }

            // Check for intersections
            for (LineSegment segment : activeSegments) {
                if (segment.contains(point)) {
                    segment.addPoint(point);
                    if (segment.getPoints().size() >= numberOfPoints && !result.contains(segment)) {
                        result.add(segment);
                    }
                }
            }

        }

        return result;
    }

    private void removeInactiveSegments(TreeSet<LineSegment> activeSegments, Point point) {
        Iterator<LineSegment> it = activeSegments.iterator();
        while (it.hasNext()) {
            LineSegment segment = it.next();
            if (segment.getRight().getX() < point.getX()) {
                it.remove();
            } else {
                break;
            }
        }
    }

    private void generateAllLineSegmentsCombinations(Point[] points, List<LineSegment> segments) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                LineSegment segment = new LineSegment(points[i], points[j]);
                segments.add(segment);
            }
        }
    }



    //-------------------------

    public static List<Pair<LineSegment, List<Point>>> findLineSegmentsNew(Point[] points, int M) {
        List<Pair<LineSegment, List<Point>>> segments = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                events.add(new Event(points[i], points[j]));
            }
        }
        events.sort(Comparator.comparing(Event::getX));
        Set<Point> activePoints = new TreeSet<>(Comparator.comparing(Point::getY));
        for (Event event : events) {
            if (event.isLeft()) {
                activePoints.add(event.getLeft());
                checkIntersections(activePoints, event.getLeft(), segments, M);
            } else {
                checkIntersections(activePoints, event.getRight(), segments, M);
                activePoints.remove(event.getLeft());
            }
        }
        return segments;
    }

    private static void checkIntersections(Set<Point> activePoints, Point point, List<Pair<LineSegment, List<Point>>> segments, int M) {
        Point above = activePoints.higher(point);
        Point below = activePoints.lower(point);
        if (above != null && below != null) {
            LineSegment line = new LineSegment(below, above);
            List<Point> involvedPoints = new ArrayList<>();
            for (Point p : activePoints) {
                if (line.contains(p)) {
                    involvedPoints.add(p);
                }
            }
            if (involvedPoints.size() >= M) {
                segments.add(new Pair<>(line, involvedPoints));
            }
        }
    }

    private static class Event {
        private final Point left;
        private final Point right;
        private final double x;

        public Event(Point left, Point right) {
            this.left = left;
            this.right = right;
            this.x = Math.min(left.getX(), right.getX());
        }

        public Point getLeft() {
            return left;
        }

        public Point getRight() {
            return right;
        }

        public double getX() {
            return x;
        }

        public boolean isLeft() {
            return left.getX() < right.getX();
        }
    }

}

package com.pattern.recognition.application;

import com.pattern.recognition.domain.LineSegmentServicePort;
import com.pattern.recognition.domain.Segment;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LineSegmentService implements LineSegmentServicePort {

    /**
     * Given a list of points, it returns the list of line segments that contains at least minAmountOfIntersections points
     *
     * @param points the list of points to process
     * @param minAmountOfIntersections the minimum amount of points to find on a line
     * @return the list of line segments containing at least minAmountOfIntersections points
     */
    public List<Segment> findLines(Point[] points, Integer minAmountOfIntersections) {
        if(minAmountOfIntersections == null || minAmountOfIntersections < 1) {
            throw new IllegalArgumentException();
        }

        Set<Segment> lines = new HashSet<>();
        List<Segment> allSegments = generateAllLineSegmentsCombinations(points);

        Map<Double, List<Segment>> segmentsBySlope = splitSegmentsBySlope(allSegments);
        segmentsBySlope.forEach((slope, segmentList) -> lines.addAll(recursiveListProcessing(segmentList)));
        return lines.stream().filter(line -> line.getIntersections().size() >= minAmountOfIntersections).collect(Collectors.toList());
    }

    /**
     * A recursive method that merges line segments together if they are adjacent
     *
     * @param originalSegmentList the list containing the previous state of the line segments
     * @return a list containing the merged line segments
     */
    private List<Segment> recursiveListProcessing(List<Segment> originalSegmentList) {
        List<Segment> processedSegmentList = new ArrayList<>(originalSegmentList);
        for (int i = 0; i < originalSegmentList.size(); i++) {
            for (int j = i + 1; j < originalSegmentList.size(); j++) {
                Segment leftSegment = originalSegmentList.get(i);
                Segment rightSegment = originalSegmentList.get(j);
                if(leftSegment.isAdjacent(rightSegment)) {
                    //removeExistingMergeSegmentFromList(originalSegmentList, processedSegmentList, leftSegment, rightSegment);
                    removeExistingMergeSegmentFromList(processedSegmentList, leftSegment, rightSegment);
                    mergeSegments(processedSegmentList, leftSegment, rightSegment);
                    processedSegmentList.sort(Comparator.comparingDouble(o -> o.getRight().getX()));
                    return recursiveListProcessing(processedSegmentList);
                }

            }
        }
        return processedSegmentList;
    }

    /**
     * Given two adjacent line segments, it merges them, removing the "orphans" line segments and adding the new merged
     * line segment to the space
     *
     * @param segmentList the list of line segments on the space
     * @param leftSegment the leftmost line segment to merge
     * @param rightSegment the rightmost line segment to merge
     */
    private void mergeSegments(List<Segment> segmentList, Segment leftSegment, Segment rightSegment) {
        Segment mergedSegment = new Segment(leftSegment.getLeft(), rightSegment.getRight());
        mergedSegment.getIntersections().addAll(leftSegment.getIntersections());
        mergedSegment.getIntersections().addAll(rightSegment.getIntersections());
        removeOrphans(segmentList, leftSegment.getRight());
        segmentList.remove(leftSegment);
        segmentList.remove(rightSegment);
        segmentList.add(mergedSegment);
    }

    /**
     * Given a line segment list and two adjacent segments, it deletes the already existing merge segment
     *
     * @param segmentList the list of segments on the space
     * @param leftSegment the leftmost line segment
     * @param rightSegment the rightmost line segment
     */
    private void removeExistingMergeSegmentFromList(List<Segment> segmentList, Segment leftSegment, Segment rightSegment) {
        findSegmentByMargins(segmentList, leftSegment.getLeft(), rightSegment.getRight()).ifPresent(segmentList::remove);
    }


    private void removeOrphans(List<Segment> segmentList, Point deletedPoint) {
        List<Segment> orphans = segmentList.stream().filter(point -> point.getLeft().equals(deletedPoint) || point.getRight().equals(deletedPoint)).collect(Collectors.toList());
        segmentList.removeAll(orphans);
    }

    /**
     * Given a line segment list and two points, it searches for an existing segment delimited by those points
     *
     * @param segmentList the list of segments on the space
     * @param left the left margin
     * @param right the right margin
     * @return a line segment with the input margin, if it already exists on the space
     */
    private Optional<Segment> findSegmentByMargins(List<Segment> segmentList, Point left, Point right) {
        return segmentList.stream().filter(segment ->
            segment.getLeft().equals(left) && segment.getRight().equals(right)
        ).findFirst();
    }

    /**
     * Given a list of segments, it groups them by slope
     *
     * @param allSegments the input list of segments
     * @return an HashMap containing the segment list grouped by their slope
     */
    private Map<Double, List<Segment>> splitSegmentsBySlope(List<Segment> allSegments) {
        Map<Double, List<Segment>> segmentsBySlope = new HashMap<>();
        allSegments.forEach(segment -> {
            double slope = segment.getSlope();
            if (!segmentsBySlope.containsKey(slope)) {
                segmentsBySlope.put(slope, new ArrayList<>());
            }
            segmentsBySlope.get(slope).add(segment);
        });
        return segmentsBySlope;
    }

    /**
     * Given an array of points, it generates every possible segment between them
     *
     * @param points an array of points on the space
     * @return the list of all possible segments
     */
    private List<Segment> generateAllLineSegmentsCombinations(Point[] points) {
        List<Segment> segmentList = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Segment segment = new Segment(points[i], points[j]);
                segmentList.add(segment);
            }
        }
        segmentList.sort(Comparator.comparingDouble(o -> o.getLeft().getX()));
        return segmentList;
    }
}

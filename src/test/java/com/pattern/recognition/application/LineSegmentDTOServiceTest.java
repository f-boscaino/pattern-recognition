package com.pattern.recognition.application;

import com.pattern.recognition.domain.Segment;
import org.junit.jupiter.api.Test;
import org.springframework.data.geo.Point;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LineSegmentDTOServiceTest {

    private LineSegmentService underTest = new LineSegmentService();

    @Test
    public void givenTwoPointsThereIsALineThatContainsBoth() {
        List<Point> pointList = List.of(
                new Point(1,1),
                new Point(2,2)
        );
        List<Segment> result = underTest.findLines(pointList.toArray(new Point[pointList.size()]), 2);
        assertThat(result.size()).isEqualTo(1);
    }


    @Test
    public void givenThreeNotAlignedPointsThereAreNoLinesThatContainAllThreeOfThem() {
        List<Point> pointList = List.of(
                new Point(1,1),
                new Point(2,2),
                new Point(4,3)
        );
        List<Segment> result = underTest.findLines(pointList.toArray(new Point[pointList.size()]), 3);
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void givenSixAlignedPointsThereIsALineConnectingAtLeastThreeOfThem() {
        List<Point> pointList = List.of(
                new Point(0,0),
                new Point(1,1),
                new Point(2,2),
                new Point(3,3),
                new Point(4,4),
                new Point(5,5)
        );
        List<Segment> result = underTest.findLines(pointList.toArray(new Point[pointList.size()]), 3);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getIntersections()).containsAll(pointList);
    }


    @Test
    public void givenSixNotAlignedPointsThereAreTwoParallelLinesWithThreePointsInEach() {
        List<Point> pointList = List.of(
                new Point(0,0),
                new Point(1,0),
                new Point(1,1),
                new Point(2,2),
                new Point(2,1),
                new Point(3,2)
        );
        List<Segment> result = underTest.findLines(pointList.toArray(new Point[pointList.size()]), 3);

        assertThat(result.size()).isEqualTo(2);
    }


}

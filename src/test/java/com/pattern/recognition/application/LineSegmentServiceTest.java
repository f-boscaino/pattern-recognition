package com.pattern.recognition.application;

import com.pattern.recognition.domain.LineSegment;
import org.junit.jupiter.api.Test;
import org.springframework.data.geo.Point;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LineSegmentServiceTest {

    private LineSegmentService underTest = new LineSegmentService();

    @Test
    public void givenTwoPointsThereIsALineSegmentThatContainsBoth() {
        Point[] input = new Point[2];
        input[0] = new Point(1,1);
        input[1] = new Point(1,2);
        List<LineSegment> result = underTest.findLineSegments(input, 2);
        assertThat(result.size()).isEqualTo(1);
    }


    @Test
    public void givenThreeNotAlignedPointsThereIsALineThatContainsAllThreeOfThem() {
        Point[] input = new Point[3];
        input[0] = new Point(1,1);
        input[1] = new Point(2,2);
        input[2] = new Point(4,3);
        List<LineSegment> result = underTest.findLineSegments(input, 3);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getPoints()).containsAll(List.of(input));
    }

    @Test
    public void example() {
        Point[] input = new Point[5];
        input[0] = new Point(0,0);
        input[1] = new Point(1,1);
        input[2] = new Point(2,3);
        input[3] = new Point(3,5);
        input[4] = new Point(4,4);
        List<LineSegment> result = underTest.findLineSegments(input, 1);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getPoints()).containsAll(List.of(input));
    }


}

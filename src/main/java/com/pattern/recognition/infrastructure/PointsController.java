package com.pattern.recognition.infrastructure;

import com.pattern.recognition.domain.FeaturePointsServicePort;
import com.pattern.recognition.domain.FeaturesPoint;
import com.pattern.recognition.domain.LineSegmentServicePort;
import com.pattern.recognition.domain.Segment;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PointsController {

    private final FeaturePointsRepository featurePointsRepository;
    private final FeaturePointsServicePort featurePointsService;
    private final LineSegmentServicePort lineSegmentService;

    public PointsController(FeaturePointsRepository featurePointsRepository,
                            FeaturePointsServicePort featurePointsService, LineSegmentServicePort lineSegmentService) {
        this.featurePointsRepository = featurePointsRepository;
        this.featurePointsService = featurePointsService;
        this.lineSegmentService = lineSegmentService;
    }


    @GetMapping(value = "/space")
    public List<Point> getAllPointsInSpace() {
        return featurePointsService.toPoints(featurePointsRepository.findAll());
    }

    @DeleteMapping(value = "/space")
    public void deleteAllPointsInSpace() {
        featurePointsRepository.deleteAll();
    }

    @PostMapping(value="/point")
    public Point addPointToSpace(@RequestBody Point point) {
        return featurePointsRepository.save(new FeaturesPoint(point)).getPoint();
    }

    @PostMapping(value="/points")
    public void addPointToSpace(@RequestBody List<Point> pointList) {
        List<FeaturesPoint> featurePointList = pointList.stream().map(FeaturesPoint::new).collect(Collectors.toList());
        featurePointsRepository.saveAll(featurePointList);
    }

    @GetMapping(value = "/lines/{numberOfPoints}")
    public List<Segment> getLines(@PathVariable("numberOfPoints") Integer numberOfPoints) {
        List<Point> pointList = featurePointsService.toPoints(featurePointsRepository.findAll());
        Point[] pointArray = pointList.toArray(new Point[pointList.size()]);
        return lineSegmentService.findLines(pointArray, numberOfPoints);
    }


}

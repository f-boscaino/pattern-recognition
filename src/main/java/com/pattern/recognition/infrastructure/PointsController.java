package com.pattern.recognition.infrastructure;

import com.pattern.recognition.application.FeaturePointsService;
import com.pattern.recognition.application.LineSegmentService;
import com.pattern.recognition.domain.FeaturesPoint;
import com.pattern.recognition.domain.LineSegment;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PointsController {

    private FeaturePointsRepository featurePointsRepository;
    private FeaturePointsService featurePointsService;
    private LineSegmentService lineSegmentService;

    public PointsController(FeaturePointsRepository featurePointsRepository,
                            FeaturePointsService featurePointsService, LineSegmentService lineSegmentService) {
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
        //TODO validation!!
        return featurePointsRepository.save(new FeaturesPoint(point)).getPoint();
    }

    @GetMapping(value = "/lines/{numberOfPoints}")
    public List<LineSegment> getLineSegments(@PathVariable("numberOfPoints") Integer numberOfPoints) {
        List<Point> pointList = featurePointsService.toPoints(featurePointsRepository.findAll());
        Point[] pointArray = new Point[pointList.size()];
        return lineSegmentService.findLineSegments(pointList.toArray(pointArray), numberOfPoints);
    }


}

package com.pattern.recognition.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.geo.Point;

@Entity
@NoArgsConstructor
public class FeaturesPoint {

    @Id
    @Setter
    @Getter
    private Point point;

    public FeaturesPoint(Point point) {
        if(point == null) {
            throw new IllegalArgumentException();
        }
        this.point = point;
    }
}

package com.github.ngoanh2n.img;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

class ImageComparisonSource {
    private final BufferedImage image;
    private final Set<Rectangle> rectanglesToIgnore;
    private final Set<Rectangle> rectanglesToCompare;

    ImageComparisonSource(BufferedImage image) {
        this.image = image;
        this.rectanglesToIgnore = new HashSet<>();
        this.rectanglesToCompare = new HashSet<>();
        this.rectanglesToCompare.add(this.getRectangle());
    }

    //-------------------------------------------------------------------------------//

    BufferedImage getImage() {
        return image;
    }

    Rectangle getRectangle() {
        return new Rectangle(image.getWidth(), image.getHeight());
    }

    Set<Rectangle> getRectanglesToIgnore() {
        return rectanglesToIgnore;
    }

    Set<Rectangle> getRectanglesToCompare() {
        return rectanglesToCompare;
    }
}

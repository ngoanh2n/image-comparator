package com.github.ngoanh2n.img;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/image-comparator">ngoanh2n/image-comparator</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/image-comparator">com.github.ngoanh2n:image-comparator</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2021
 */
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

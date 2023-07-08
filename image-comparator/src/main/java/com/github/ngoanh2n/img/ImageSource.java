package com.github.ngoanh2n.img;

import java.awt.*;
import java.util.LinkedHashSet;
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
class ImageSource {
    private final Set<Rectangle> rectangles;
    private final Rectangle minRectangle;

    ImageSource(ImageComparisonSource diffSource) {
        this.rectangles = getRectangles(diffSource);
        this.minRectangle = getMinRectangle(rectangles);
    }

    //-------------------------------------------------------------------------------//

    private static Set<Rectangle> getRectangles(ImageComparisonSource diffSource) {
        Set<Rectangle> rectangles = new LinkedHashSet<>();
        rectangles.add(diffSource.getExpRectangle());
        rectangles.add(diffSource.getActRectangle());
        return rectangles;
    }

    private static Rectangle getMinRectangle(Set<Rectangle> rectangles) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;

        for (Rectangle rectangle : rectangles) {
            minX = Math.min(minX, (int) rectangle.getMinX());
            minY = Math.min(minY, (int) rectangle.getMinY());
            maxX = Math.max(maxX, (int) rectangle.getMaxX());
            maxY = Math.max(maxY, (int) rectangle.getMaxY());
        }
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    //-------------------------------------------------------------------------------//

    boolean contain(Point point) {
        if (minRectangle.contains(point)) {
            if (rectangles.size() == 1) {
                return true;
            }
            for (Rectangle rectangle : rectangles) {
                if (rectangle.contains(point)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}

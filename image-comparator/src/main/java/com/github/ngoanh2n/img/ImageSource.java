package com.github.ngoanh2n.img;

import java.awt.*;
import java.util.HashSet;
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

    ImageSource(Set<Rectangle> rectangles) {
        this.rectangles = rectangles;
        this.minRectangle = getMinRectangle();
    }

    //-------------------------------------------------------------------------------//

    static ImageSource createIgnore(ImageComparisonSource exp, ImageComparisonSource act) {
        Set<Rectangle> rectangles = new HashSet<>();
        Set<Rectangle> expRectangles = exp.getRectanglesToIgnore();
        Set<Rectangle> actRectangles = act.getRectanglesToIgnore();

        for (Rectangle expRectangle : expRectangles) {
            for (Rectangle actRectangle : actRectangles) {
                Rectangle rectangle = expRectangle.intersection(actRectangle);
                if (!rectangle.isEmpty()) {
                    rectangles.add(rectangle);
                }
            }
        }
        return new ImageSource(rectangles);
    }

    static ImageSource createDiff(ImageComparisonSource exp, ImageComparisonSource act) {
        Set<Rectangle> rectangles = new LinkedHashSet<>();
        rectangles.addAll(exp.getRectanglesToCompare());
        rectangles.addAll(act.getRectanglesToCompare());
        return new ImageSource(rectangles);
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

    private Rectangle getMinRectangle() {
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
}

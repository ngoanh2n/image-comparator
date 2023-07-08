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
    private final BufferedImage expImage;
    private final BufferedImage actImage;
    private final Rectangle expRectangle;
    private final Rectangle actRectangle;
    private final ImageComparisonOptions options;
    private final BufferedImage diffImage;
    private final Set<Point> diffPoints;
    private boolean marked;

    ImageComparisonSource(BufferedImage exp, BufferedImage act, ImageComparisonOptions options) {
        this.expImage = exp;
        this.actImage = act;
        this.expRectangle = getRectangle(exp);
        this.actRectangle = getRectangle(act);
        this.options = options;
        this.marked = false;
        this.diffImage = createImage();
        this.diffPoints = new HashSet<>();
    }

    //-------------------------------------------------------------------------------//

    private static Rectangle getRectangle(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        return new Rectangle(w, h);
    }

    //-------------------------------------------------------------------------------//

    Rectangle getExpRectangle() {
        return expRectangle;
    }

    Rectangle getActRectangle() {
        return actRectangle;
    }

    int getMaxWidth() {
        return Math.max(expImage.getWidth(), actImage.getWidth());
    }

    int getMaxHeight() {
        return Math.max(expImage.getHeight(), actImage.getHeight());
    }

    boolean contain(Point point) {
        return expRectangle.contains(point) && actRectangle.contains(point);
    }

    boolean containWithRGB(Point point) {
        int colorDistortion = 15;
        return !equalRGB(point, colorDistortion);
    }

    void addDiffPoint(Point point) {
        diffPoints.add(point);
    }

    int getDiffSize() {
        return diffPoints.size();
    }

    BufferedImage getDiffImage() {
        if (!marked) {
            setRGB(options.diffColor(), diffPoints);
            marked = true;
        }
        return diffImage;
    }

    //-------------------------------------------------------------------------------//

    private BufferedImage createImage() {
        int width = getMaxWidth();
        int height = getMaxHeight();
        int type = actImage.getType();
        BufferedImage image = new BufferedImage(width, height, type);

        redrawImage(image, actImage);
        redrawImage(image, expImage);
        return image;
    }

    private void setRGB(Color color, Set<Point> points) {
        int rgb = color.getRGB();
        for (Point point : points) {
            diffImage.setRGB(point.x, point.y, rgb);
        }
    }

    private boolean equalRGB(Point point, int inaccuracy) {
        int expRGB = expImage.getRGB(point.x, point.y);
        int actRGB = actImage.getRGB(point.x, point.y);

        if (inaccuracy == 0) {
            return expRGB == actRGB;
        } else {
            int expRed = (expRGB & 0x00FF0000) >> 16;
            int expGreen = (expRGB & 0x0000FF00) >> 8;
            int expBlue = (expRGB & 0x000000FF);
            int actRed = (actRGB & 0x00FF0000) >> 16;
            int actGreen = (actRGB & 0x0000FF00) >> 8;
            int actBlue = (actRGB & 0x000000FF);
            return Math.abs(expRed - actRed) <= inaccuracy &&
                    Math.abs(expGreen - actGreen) <= inaccuracy &&
                    Math.abs(expBlue - actBlue) <= inaccuracy;
        }
    }

    private void redrawImage(BufferedImage dstImage, BufferedImage srcImage) {
        Graphics graphics = dstImage.getGraphics();
        graphics.drawImage(srcImage, 0, 0, null);
        graphics.dispose();
    }
}

package com.github.ngoanh2n.img;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ngoanh2n
 */
class ImageComparisonSources {
    private final ImageComparisonSource exp;
    private final ImageComparisonSource act;
    private final ImageComparisonOptions options;
    private final BufferedImage image;
    private final Set<Point> disregardPoints;
    private final Set<Point> differencePoints;
    private boolean marked;

    ImageComparisonSources(ImageComparisonSource exp, ImageComparisonSource act, ImageComparisonOptions options) {
        this.exp = exp;
        this.act = act;
        this.options = options;
        this.marked = false;
        this.image = createImage();
        this.disregardPoints = new HashSet<>();
        this.differencePoints = new HashSet<>();
    }

    //-------------------------------------------------------------------------------//

    void addDisregard(Point point) {
        disregardPoints.add(point);
    }

    void addDifference(Point point) {
        differencePoints.add(point);
    }

    boolean contain(Point point) {
        return exp.getRectangle().contains(point) && act.getRectangle().contains(point);
    }

    boolean containWithRGB(Point point) {
        int colorDistortion = 15;
        return !equalRGB(point, colorDistortion);
    }

    int getSize() {
        return differencePoints.size();
    }

    int getMaxWidth() {
        return Math.max(exp.getImage().getWidth(), act.getImage().getWidth());
    }

    int getMaxHeight() {
        return Math.max(exp.getImage().getHeight(), act.getImage().getHeight());
    }

    BufferedImage getImage() {
        if (!marked) {
            setRGB(options.disregardColor(), disregardPoints);
            setRGB(options.differenceColor(), differencePoints);
            marked = true;
        }
        return image;
    }

    //-------------------------------------------------------------------------------//

    private BufferedImage createImage() {
        int width = getMaxWidth();
        int height = getMaxHeight();
        int type = act.getImage().getType();
        BufferedImage image = new BufferedImage(width, height, type);

        redrawImage(image, act.getImage());
        redrawImage(image, exp.getImage());
        return image;
    }

    private void setRGB(Color color, Set<Point> points) {
        int rgb = color.getRGB();
        for (Point point : points) {
            image.setRGB(point.x, point.y, rgb);
        }
    }

    private boolean equalRGB(Point point, int inaccuracy) {
        int expRGB = exp.getImage().getRGB(point.x, point.y);
        int actRGB = act.getImage().getRGB(point.x, point.y);

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

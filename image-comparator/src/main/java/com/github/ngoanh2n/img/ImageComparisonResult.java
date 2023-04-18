package com.github.ngoanh2n.img;

import java.awt.image.BufferedImage;

public interface ImageComparisonResult {
    boolean isDifferent();

    int getDiffSize();

    BufferedImage getDiffImage();

    double getAllowedDeviation();

    double getCurrentDeviation();
}

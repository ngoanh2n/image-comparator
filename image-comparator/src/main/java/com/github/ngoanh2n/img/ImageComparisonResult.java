package com.github.ngoanh2n.img;

import java.awt.image.BufferedImage;

/**
 * The result of {@link ImageComparator}.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public interface ImageComparisonResult {
    /**
     * Whether there is any difference between expected BufferedImage and actual BufferedImage.
     *
     * @return Indicate expected BufferedImage is different against to actual BufferedImage.
     */
    boolean isDifferent();

    /**
     * Gets the number of difference points after comparison.
     *
     * @return The number of difference points
     */
    int getDiffSize();

    /**
     * Gets diff image after comparison.
     *
     * @return The image was decorated where is different and where is ignored.
     */
    BufferedImage getDiffImage();

    /**
     * Gets the deviation you have set by {@link ImageComparisonOptions.Builder#setDeviation(double)}.
     *
     * @return Allowed deviation.
     */
    double getAllowedDeviation();

    /**
     * Gets the deviation that was detected after comparison.
     *
     * @return Current deviation.
     */
    double getCurrentDeviation();
}

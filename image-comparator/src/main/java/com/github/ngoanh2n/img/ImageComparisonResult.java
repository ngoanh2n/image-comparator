package com.github.ngoanh2n.img;

import java.awt.image.BufferedImage;

/**
 * The result of {@link ImageComparator}.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/image-comparator">ngoanh2n/image-comparator</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/image-comparator">com.github.ngoanh2n:image-comparator</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2021
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
     * Get diff image after comparison.
     *
     * @return The image was decorated where is different and where is ignored.
     */
    BufferedImage getDiffImage();

    /**
     * Get the deviation you have set by {@link ImageComparisonOptions.Builder#setAllowedDeviation(double)}.
     *
     * @return Allowed deviation.
     */
    double getAllowedDeviation();

    /**
     * Get the deviation that was detected after comparison.
     *
     * @return Current deviation.
     */
    double getCurrentDeviation();
}

package com.github.ngoanh2n.img;

import java.awt.image.BufferedImage;

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
class ImageResult implements ImageComparisonResult {
    private final int diffSize;
    private final BufferedImage diffImage;
    private final double allowedDeviation;
    private final double currentDeviation;

    ImageResult(ImageComparisonSources comparisonSources, ImageComparisonOptions options) {
        this.diffSize = comparisonSources.getSize();
        this.diffImage = comparisonSources.getImage();
        this.allowedDeviation = options.allowedDeviation();

        int width = comparisonSources.getMaxWidth();
        int height = comparisonSources.getMaxHeight();
        this.currentDeviation = diffSize / (double) (width * height);
    }

    //-------------------------------------------------------------------------------//

    @Override
    public boolean hasDiff() {
        return !(currentDeviation == 0 || currentDeviation <= allowedDeviation);
    }

    @Override
    public int getDiffSize() {
        return diffSize;
    }

    @Override
    public BufferedImage getDiffImage() {
        return diffImage;
    }

    @Override
    public double getAllowedDeviation() {
        return allowedDeviation;
    }

    @Override
    public double getCurrentDeviation() {
        return currentDeviation;
    }

    @Override
    public String toString() {
        return String.format("deviation=[allowed:%s, current:%s]", allowedDeviation, currentDeviation);
    }
}

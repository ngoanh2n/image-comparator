package com.github.ngoanh2n.img;

import java.awt.image.BufferedImage;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
class ImageResult implements ImageComparisonResult {
    private final int diffSize;
    private final BufferedImage diffImage;
    private final double allowedDeviation;
    private final double currentDeviation;

    ImageResult(ImageComparisonSources comparisonSources, ImageComparisonOptions options) {
        this.diffSize = comparisonSources.getSize();
        this.diffImage = comparisonSources.getImage();
        this.allowedDeviation = options.deviation();

        int width = comparisonSources.getMaxWidth();
        int height = comparisonSources.getMaxHeight();
        this.currentDeviation = diffSize / (double) (width * height);
    }

    //-------------------------------------------------------------------------------//

    @Override
    public boolean isDifferent() {
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

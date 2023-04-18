package com.github.ngoanh2n.img;

import java.awt.image.BufferedImage;

public interface ImageComparisonVisitor {
    default void comparisonStarted(ImageComparisonOptions options, BufferedImage exp, BufferedImage act) {/**/}

    default void comparisonFinished(ImageComparisonOptions options, BufferedImage exp, BufferedImage act, ImageComparisonResult result) {/**/}
}

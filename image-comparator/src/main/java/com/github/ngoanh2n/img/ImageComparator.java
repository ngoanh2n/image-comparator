package com.github.ngoanh2n.img;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;

@ParametersAreNonnullByDefault
public final class ImageComparator {
    private final static Logger log = LoggerFactory.getLogger(ImageComparator.class);
    private final BufferedImage exp;
    private final BufferedImage act;
    private final ImageComparisonOptions options;

    private ImageComparator(BufferedImage exp, BufferedImage act, ImageComparisonOptions options) {
        this.exp = exp;
        this.act = act;
        this.options = options;
    }

    public static ImageComparisonResult compare(BufferedImage exp, BufferedImage act) {
        return compare(exp, act, ImageComparisonOptions.defaults());
    }

    public static ImageComparisonResult compare(BufferedImage exp, BufferedImage act, ImageComparisonOptions options) {
        return new ImageComparator(exp, act, options).compare();
    }

    //-------------------------------------------------------------------------------//

    private ImageComparisonResult compare() {
        return null;
    }
}

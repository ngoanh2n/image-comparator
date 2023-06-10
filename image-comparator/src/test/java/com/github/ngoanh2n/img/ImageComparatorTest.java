package com.github.ngoanh2n.img;

import com.github.ngoanh2n.Resources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author ngoanh2n
 */
public class ImageComparatorTest {
    @Test
    void equal() throws IOException {
        BufferedImage exp = getImage("com/github/ngoanh2n/img/exp/image.jpg");
        BufferedImage act = getImage("com/github/ngoanh2n/img/exp/image.jpg");

        ImageComparisonResult result = ImageComparator.compare(exp, act);
        Assertions.assertFalse(result.isDifferent());
    }

    @Test
    void different() throws IOException {
        BufferedImage exp = getImage("com/github/ngoanh2n/img/exp/image.jpg");
        BufferedImage act = getImage("com/github/ngoanh2n/img/act/image.jpg");

        ImageComparisonResult result = ImageComparator.compare(exp, act);
        Assertions.assertTrue(result.isDifferent());
    }

    private BufferedImage getImage(String resourceName) throws IOException {
        File file = Resources.getFile(resourceName);
        return ImageIO.read(file);
    }
}

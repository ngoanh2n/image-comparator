package com.github.ngoanh2n.img;

import com.github.ngoanh2n.Resources;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author ngoanh2n
 */
@Epic("Image Comparison")
public class ImageComparisonReportTest {
    @Test
    @Feature("Image Comparison: No Diff")
    void noDiff() throws IOException {
        BufferedImage exp = getImage("com/github/ngoanh2n/img/exp/image.jpg");
        BufferedImage act = getImage("com/github/ngoanh2n/img/exp/image.jpg");

        ImageComparisonResult result = ImageComparator.compare(exp, act);
        Assertions.assertFalse(result.hasDiff());
    }

    @Test
    @Feature("Image Comparison: Has Diff")
    void hasDiff() throws IOException {
        BufferedImage exp = getImage("com/github/ngoanh2n/img/exp/image.jpg");
        BufferedImage act = getImage("com/github/ngoanh2n/img/act/image.jpg");

        ImageComparisonResult result = ImageComparator.compare(exp, act);
        Assertions.assertTrue(result.hasDiff());
    }

    private BufferedImage getImage(String resourceName) throws IOException {
        File file = Resources.getFile(resourceName);
        return ImageIO.read(file);
    }
}

package com.github.ngoanh2n.img;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.RuntimeError;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Default implementation for {@link ImageComparisonVisitor} for writing output.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class ImageComparisonOutput implements ImageComparisonVisitor {
    @Override
    public void comparisonFinished(ImageComparisonOptions options, BufferedImage exp, BufferedImage act, ImageComparisonResult result) {
        if (options.resultOptions().writeOutput()) {
            String fileName = Commons.timestamp() + ".png";
            Path location = options.resultOptions().location();
            Path output = Commons.createDir(location).resolve(fileName);

            try {
                ImageIO.write(result.getDiffImage(), "png", output.toFile());
            } catch (IOException e) {
                throw new RuntimeError("Could not write diff image: " + output, e);
            }
        }
    }
}

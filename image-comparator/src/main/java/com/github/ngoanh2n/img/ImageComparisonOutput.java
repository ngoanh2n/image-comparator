package com.github.ngoanh2n.img;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.RuntimeError;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

/**
 * A default implementation for {@link ImageComparisonVisitor} for writing output files.<br><br>
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
public class ImageComparisonOutput implements ImageComparisonVisitor {
    /**
     * Default constructor.
     */
    public ImageComparisonOutput() { /**/ }

    /**
     * {@inheritDoc}
     */
    @Override
    public void comparisonFinished(ImageComparisonOptions options, BufferedImage exp, BufferedImage act, ImageComparisonResult result) {
        if (options.resultOptions().writeOutputs()) {
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

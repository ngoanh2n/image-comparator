package com.github.ngoanh2n.img;

import java.awt.image.BufferedImage;

/**
 * Use to walk through {@link ImageComparator}.<br><br>
 *
 * <b>Build the service provider:</b>
 * <ol>
 *      <li>Create a class that implements SPI {@link ImageComparisonVisitor}
 *          <pre>{@code
 *              package com.company.project.impl;
 *
 *              import com.github.ngoanh2n.img.ImageComparisonVisitor;
 *
 *              public class MyComparisonVisitor implements ImageComparisonVisitor {
 *                  ...IMPLEMENTED METHODS...
 *              }
 *          }</pre>
 *      </li>
 *      <li>Create a provider configuration file
 *          <ol>
 *              <li>Location: {@code resources/META-INF/services}</li>
 *              <li>Name: {@code com.github.ngoanh2n.img.ImageComparisonVisitor}</li>
 *              <li>Content: {@code com.company.project.impl.MyComparisonVisitor}</li>
 *          </ol>
 *      </li>
 * </ol>
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
public interface ImageComparisonVisitor {
    /**
     * Callback before {@link ImageComparator#compare(BufferedImage, BufferedImage, ImageComparisonOptions)}.
     *
     * @param exp     The expected BufferedImage.
     * @param act     The actual BufferedImage needs to compare.
     * @param options The {@link ImageComparisonOptions} to adjust behaviors of {@link ImageComparator}.
     */
    default void comparisonStarted(ImageComparisonOptions options, BufferedImage exp, BufferedImage act) {/**/}

    /**
     * Callback after {@link ImageComparator#compare(BufferedImage, BufferedImage, ImageComparisonOptions)}.
     *
     * @param exp     The expected BufferedImage.
     * @param act     The actual BufferedImage needs to compare.
     * @param options The {@link ImageComparisonOptions} to adjust behaviors of {@link ImageComparator}.
     * @param result  A {@link ImageComparisonResult} after comparison process ended.
     */
    default void comparisonFinished(ImageComparisonOptions options, BufferedImage exp, BufferedImage act, ImageComparisonResult result) {/**/}
}

package com.github.ngoanh2n.img;

import java.awt.image.BufferedImage;

/**
 * Used to walk through {@link ImageComparator}.<br>
 * How to build the service provider:<br>
 * <ul>
 *      <li>1. Create a class that implements SPI {@link ImageComparisonVisitor}
 *      <pre>{@code
 *      package com.company.project.impl;
 *
 *      import com.github.ngoanh2n.img.ImageComparisonVisitor;
 *
 *      public class MyComparisonVisitor implements ImageComparisonVisitor {
 *          //
 *          // IMPLEMENTED METHODS
 *          //
 *      }
 *      }</pre>
 *      <li>2. Create a provider configuration file:
 *      <ul>
 *          <li>Location: {@code resources/META-INF/services}
 *          <li>Name: {@code com.github.ngoanh2n.img.ImageComparisonVisitor}
 *          <li>Content: {@code com.company.project.impl.MyComparisonVisitor}
 *      </ul>
 * </ul>
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public interface ImageComparisonVisitor {
    /**
     * Callback before {@link ImageComparator#compare(BufferedImage, BufferedImage, ImageComparisonOptions)}.
     *
     * @param exp     The expected BufferedImage.
     * @param act     The actual BufferedImage needs to compare.
     * @param options {@link ImageComparisonOptions} you have provided.
     */
    default void comparisonStarted(ImageComparisonOptions options, BufferedImage exp, BufferedImage act) {/**/}

    /**
     * Callback after {@link ImageComparator#compare(BufferedImage, BufferedImage, ImageComparisonOptions)}.
     *
     * @param exp     The expected BufferedImage.
     * @param act     The actual BufferedImage needs to compare.
     * @param options {@link ImageComparisonOptions} you have provided.
     * @param result  {@link ImageComparisonResult} after comparison process ended.
     */
    default void comparisonFinished(ImageComparisonOptions options, BufferedImage exp, BufferedImage act, ImageComparisonResult result) {/**/}
}

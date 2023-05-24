package com.github.ngoanh2n.img;

import com.github.ngoanh2n.RuntimeError;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Starting point to compare 2 images.
 * <ul>
 *     <li>{@link ImageComparisonResult} = {@link ImageComparator#compare(BufferedImage, BufferedImage)}</li>
 *     <li>{@link ImageComparisonResult} = {@link ImageComparator#compare(BufferedImage, BufferedImage, ImageComparisonOptions)}</li>
 * </ul>
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@ParametersAreNonnullByDefault
public final class ImageComparator {
    private final static Logger log = LoggerFactory.getLogger(ImageComparator.class);
    private final BufferedImage exp;
    private final BufferedImage act;
    private final ImageComparisonOptions options;

    private ImageComparator(BufferedImage exp, BufferedImage act, ImageComparisonOptions options) {
        this.exp = Preconditions.checkNotNull(exp, "exp image cannot be null");
        this.act = Preconditions.checkNotNull(act, "act image cannot be null");
        this.options = Preconditions.checkNotNull(options, "options cannot be null");
    }

    //-------------------------------------------------------------------------------//

    /**
     * Compare 2 buffered images.
     *
     * @param exp The expected BufferedImage.
     * @param act The actual BufferedImage needs to compare.
     * @return A {@link ImageComparisonResult} after comparison process ended.
     */
    public static ImageComparisonResult compare(BufferedImage exp, BufferedImage act) {
        return compare(exp, act, ImageComparisonOptions.defaults());
    }

    /**
     * Compare 2 buffered images.
     *
     * @param exp     The expected BufferedImage.
     * @param act     The actual BufferedImage needs to compare.
     * @param options The {@link ImageComparisonOptions} to adjust behaviors of {@link ImageComparator}.
     * @return A {@link ImageComparisonResult} after comparison process ended.
     */
    public static ImageComparisonResult compare(BufferedImage exp, BufferedImage act, ImageComparisonOptions options) {
        return new ImageComparator(exp, act, options).compare();
    }

    //-------------------------------------------------------------------------------//

    private static ImageComparisonSources doComparison(BufferedImage exp, BufferedImage act, ImageComparisonOptions options) {
        ImageComparisonSource expComparisonSource = new ImageComparisonSource(exp);
        ImageComparisonSource actComparisonSource = new ImageComparisonSource(act);
        ImageComparisonSources comparisonSources = new ImageComparisonSources(expComparisonSource, actComparisonSource, options);

        if (!equalBytes(exp, act)) {
            ImageSource disregardSource = ImageSource.disregard(expComparisonSource, actComparisonSource);
            ImageSource differenceSource = ImageSource.difference(expComparisonSource, actComparisonSource);

            int width = comparisonSources.getMaxWidth();
            int height = comparisonSources.getMaxHeight();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Point point = new Point(x, y);
                    if (disregardSource.contain(point)) {
                        comparisonSources.addDisregard(point);
                        continue;
                    }
                    if (!comparisonSources.contain(point)) {
                        comparisonSources.addDifference(point);
                        continue;
                    }
                    if (differenceSource.contain(point)) {
                        if (comparisonSources.containWithRGB(point)) {
                            comparisonSources.addDifference(point);
                        }
                    }
                }
            }
        }
        return comparisonSources;
    }

    private static boolean equalBytes(BufferedImage exp, BufferedImage act) {
        return exp.getHeight() == act.getHeight() &&
                exp.getWidth() == act.getWidth() &&
                act.getColorModel().equals(exp.getColorModel()) &&
                equalBuffers(exp.getRaster().getDataBuffer(), act.getRaster().getDataBuffer());
    }

    private static boolean equalBuffers(DataBuffer exp, DataBuffer act) {
        return act.getSize() == exp.getSize() &&
                act.getDataType() == exp.getDataType() &&
                act.getNumBanks() == exp.getNumBanks() &&
                equalBufferElements(act, exp);
    }

    private static boolean equalBufferElements(DataBuffer exp, DataBuffer act) {
        for (int numBank = 0; numBank < exp.getNumBanks(); numBank++) {
            for (int i = 0; i < exp.getSize(); i++) {
                if (exp.getElem(numBank, i) != act.getElem(numBank, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    //-------------------------------------------------------------------------------//

    private ImageComparisonResult compare() {
        ImageComparisonResult result;
        long starting = System.currentTimeMillis();
        List<ImageComparisonVisitor> visitors = getVisitors();

        try {
            visitors.forEach(visitor -> visitor.comparisonStarted(options, exp, act));
            ImageComparisonSources comparisonSources = doComparison(exp, act, options);
            result = new ImageResult(comparisonSources, options);
            visitors.forEach(visitor -> visitor.comparisonFinished(options, exp, act, result));
            log.debug("Image comparison result: {}", result);
        } catch (Exception e) {
            String msg = "Error occurred while comparing";
            log.error(msg);
            throw new RuntimeError(msg, e);
        } finally {
            long ending = System.currentTimeMillis();
            String format = "[HH 'hours', mm 'minutes', ss 'seconds', SS 'milliseconds']";
            log.info("Image comparison finished in {}", DurationFormatUtils.formatDuration(ending - starting, format, true));
        }
        return result;
    }

    private List<ImageComparisonVisitor> getVisitors() {
        ServiceLoader<ImageComparisonVisitor> serviceLoader = ServiceLoader.load(ImageComparisonVisitor.class);
        ImmutableList<ImageComparisonVisitor> visitors = ImmutableList.copyOf(serviceLoader.iterator());

        if (!options.resultOptions().writeOutput()) {
            visitors = ImmutableList.copyOf(Collections2.filter(visitors,
                    visitor -> !visitor.getClass().getName().equals(ImageComparisonOutput.class.getName())));
        }
        visitors.forEach(visitor -> log.debug("{}: {}", ImageComparisonVisitor.class.getName(), visitor.getClass().getName()));
        return visitors;
    }
}

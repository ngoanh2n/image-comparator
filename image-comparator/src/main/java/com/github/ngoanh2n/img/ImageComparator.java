package com.github.ngoanh2n.img;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.RuntimeError;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Compare 2 image files.<br><br>
 *
 * <b>Comparison</b>
 * <ol>
 *     <li>Compare 2 images
 *          <pre>{@code
 *              ImageComparisonOptions options = ImageComparisonOptions
 *                      .builder()
 *                      .setAllowedDeviation(0.05)       // Default to 0.0
 *                      .setDiffColor(Color.CYAN)        // Default to Color.RED
 *                      .build();
 *              ImageComparisonResult result = ImageComparator.compare(expectedImage, actualImage, options);
 *          }</pre>
 *     </li>
 *     <li>Compare 2 image directories
 *          <pre>{@code
 *              Path expectedImageDir = Paths.get("data/expected");
 *              Path actualImageDir = Paths.get("data/actual");
 *
 *              ImageComparisonOptions options = ImageComparisonOptions
 *                      .builder()
 *                      .setAllowedDeviation(0.05)       // Default to 0.0
 *                      .setDiffColor(Color.CYAN)        // Default to Color.RED
 *                      .build();
 *              ImageBulkComparisonResult result = ImageComparator.compare(expectedImageDir, actualImageDir, options);
 *          }</pre>
 *     </li>
 * </ol>
 *
 * <b>Result</b><br>
 * {@link ImageComparisonResult} is the result of {@link ImageComparator#compare(BufferedImage, BufferedImage, ImageComparisonOptions) ImageComparator.compare(expectedImage, actualImage, options)}.
 * <pre>{@code
 *      boolean isDifferent = ImageComparisonResult.isDifferent();
 *      int diffSize = ImageComparisonResult.getDiffSize();
 *      BufferedImage diffImage = ImageComparisonResult.getDiffImage();
 *      double allowedDeviation = ImageComparisonResult.getAllowedDeviation();
 *      double currentDeviation = ImageComparisonResult.getCurrentDeviation();
 * }</pre><br>
 * {@link ImageBulkComparisonResult} is the result of {@link ImageComparator#compare(Path, Path, ImageComparisonOptions) ImageComparator.compare(expectedImageDir, actualImageDir, options)}.
 * <pre>{@code
 *      boolean hasDiff = ImageBulkComparisonResult.hasDiff();
 *      int diffTotal = ImageBulkComparisonResult.getDiffTotal();
 *      List<ImageComparisonResult> diffResults = ImageBulkComparisonResult.getDiffResults();
 * }</pre><br>
 *
 * <b>Visitor</b><br>
 * {@link ImageComparator} for walking through {@link ImageComparator}.
 * <ul>
 *     <li>{@link ImageComparisonVisitor#comparisonStarted(ImageComparisonOptions, BufferedImage, BufferedImage)}</li>
 *     <li>{@link ImageComparisonVisitor#comparisonFinished(ImageComparisonOptions, BufferedImage, BufferedImage, ImageComparisonResult)}</li>
 * </ul>
 *
 * <b>Output</b><br>
 * {@link ImageComparisonOutput} for writing comparison output files to specified location.<br>
 * An implementation of {@link ImageComparisonVisitor}.
 * <ul>
 *     <li>The output is always created at {@code build/ngoanh2n/img/{yyyyMMdd.HHmmss.SSS}} by default</li>
 *     <li>Use {@link ImageComparisonResultOptions} to adjust the output behaviors. And set to {@link ImageComparisonOptions}
 *         <pre>{@code
 *              ImageComparisonResultOptions resultOptions = ImageComparisonResultOptions
 *                     .builder()
 *                     .writeOutputs(false)                         // Default to true
 *                     //.setLocation(Paths.get("build/custom"))    // Default to build/ngoanh2n/img
 *                     .build();
 *              ImageComparisonOptions options = ImageComparisonOptions
 *                      .builder()
 *                      .setResultOptions(resultOptions)            // Default to ImageComparisonResultOptions.defaults()
 *                      .build();
 *         }</pre>
 *     </li>
 * </ul>
 *
 * <b>Allure Report</b><br>
 * When using Allure as a report framework, should use
 * <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/image-comparator-allure">com.github.ngoanh2n:image-comparator-allure</a>.<br><br>
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

    /**
     * Compare 2 image directories.
     *
     * @param exp The expected image directory.
     * @param act The actual image directory needs to compare.
     * @return A {@link ImageBulkComparisonResult} after comparison process ended.
     */
    public static ImageBulkComparisonResult compare(Path exp, Path act) {
        return compare(exp, act, ImageComparisonOptions.defaults());
    }

    /**
     * Compare 2 image directories.
     *
     * @param exp     The expected image directory.
     * @param act     The actual image directory needs to compare.
     * @param options The {@link ImageComparisonOptions} to adjust behaviors of {@link ImageComparator}.
     * @return A {@link ImageBulkComparisonResult} after comparison process ended.
     */
    public static ImageBulkComparisonResult compare(Path exp, Path act, ImageComparisonOptions options) {
        log.debug("//-----Bulk Image Comparison-----//");
        log.debug("Exp image directory: {}", Commons.getRelative(exp));
        log.debug("Act image directory: {}", Commons.getRelative(act));

        ImageBulkResult result = new ImageBulkResult();
        List<Map.Entry<Path, Path>> sources = getSources(exp, act);

        for (Map.Entry<Path, Path> source : sources) {
            BufferedImage actImage = readImage(source.getKey().toFile());
            BufferedImage expImage = readImage(source.getValue().toFile());
            result.put(compare(expImage, actImage, options));
        }
        return result;
    }

    //-------------------------------------------------------------------------------//

    private static ImageComparisonSource doComparison(BufferedImage exp, BufferedImage act, ImageComparisonOptions options) {
        ImageComparisonSource comparisonSource = new ImageComparisonSource(exp, act, options);
        ImageSource source = new ImageSource(comparisonSource);

        if (!equalBytes(exp, act)) {
            int width = comparisonSource.getMaxWidth();
            int height = comparisonSource.getMaxHeight();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Point point = new Point(x, y);

                    if (!comparisonSource.contain(point)) {
                        comparisonSource.addDiffPoint(point);
                        continue;
                    }
                    if (source.contain(point)) {
                        if (comparisonSource.containWithRGB(point)) {
                            comparisonSource.addDiffPoint(point);
                        }
                    }
                }
            }
        }
        return comparisonSource;
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

    private static BufferedImage readImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException ex) {
            String msg = "Error occurred while reading image: " + ex.getMessage();
            log.error(msg);
            throw new RuntimeError(msg, ex);
        }
    }

    private static List<Map.Entry<Path, Path>> getSources(Path exp, Path act) {
        try {
            List<Path> expFiles = getImageFiles(exp);
            List<Path> actFiles = getImageFiles(act);

            log.debug("Exp image files: {}", expFiles.size());
            log.debug("Act image files: {}", actFiles.size());
            List<Map.Entry<Path, Path>> sources = new ArrayList<>(actFiles.size());

            for (Path actFile : actFiles) {
                String actTarget = String.valueOf(act.relativize(actFile));

                for (Path expFile : expFiles) {
                    String expTarget = String.valueOf(exp.relativize(expFile));

                    if (actTarget.equals(expTarget)) {
                        sources.add(new AbstractMap.SimpleEntry<>(actFile, expFile));
                        break;
                    }
                }
            }
            return sources;
        } catch (IOException ex) {
            String msg = "Error occurred while reading image files in directory";
            log.error(msg);
            throw new RuntimeError(msg, ex);
        }
    }

    private static List<Path> getImageFiles(Path path) throws IOException {
        try (Stream<Path> stream = Files.walk(path)) {
            return stream.filter(Files::isRegularFile).collect(Collectors.toList());
        }
    }

    //-------------------------------------------------------------------------------//

    private ImageComparisonResult compare() {
        ImageComparisonResult result;
        long starting = System.currentTimeMillis();
        List<ImageComparisonVisitor> visitors = getVisitors();

        try {
            visitors.forEach(visitor -> visitor.comparisonStarted(options, exp, act));
            ImageComparisonSource comparisonSources = doComparison(exp, act, options);
            result = new ImageResult(comparisonSources, options);
            visitors.forEach(visitor -> visitor.comparisonFinished(options, exp, act, result));
            log.debug("Image comparison result: {}", result);
        } catch (Exception ex) {
            String msg = "Error occurred while comparing: " + ex.getMessage();
            log.error(msg);
            throw new RuntimeError(msg, ex);
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

        if (!options.resultOptions().writeOutputs()) {
            visitors = ImmutableList.copyOf(Collections2.filter(visitors,
                    visitor -> !visitor.getClass().getName().equals(ImageComparisonOutput.class.getName())));
        }
        visitors.forEach(visitor -> log.debug("{}: {}", ImageComparisonVisitor.class.getName(), visitor.getClass().getName()));
        return visitors;
    }
}

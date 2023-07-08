package com.github.ngoanh2n.img;

import java.nio.file.Path;
import java.util.List;

/**
 * The result of {@link ImageComparator#compare(Path, Path, ImageComparisonOptions) ImageComparator.compare(expectedImageDir, actualImageDir, options)}.<br><br>
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
public interface ImageBulkComparisonResult {
    /**
     * Whether there is any different file between expected image directory and actual image directory.
     *
     * @return Indicate expected file is different against to actual.
     */
    boolean hasDiff();

    /**
     * Get number of diff results after bulk comparison between 2 image directories.<br>
     * A diff result is a {@link ImageComparisonResult} after comparing between 2 image files.
     *
     * @return Diff result total.
     */
    int getDiffTotal();

    /**
     * Get all diff results after bulk comparison between 2 image directories.<br>
     * A diff result is a {@link ImageComparisonResult} after comparing between 2 image files.
     *
     * @return Diff result list.
     */
    List<ImageComparisonResult> getDiffResults();
}

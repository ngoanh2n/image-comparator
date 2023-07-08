package com.github.ngoanh2n.img;

import java.util.ArrayList;
import java.util.List;

/**
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/image-comparator">ngoanh2n/image-comparator</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/image-comparator">com.github.ngoanh2n:image-comparator</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2021
 */
class ImageBulkResult implements ImageBulkComparisonResult {
    private final List<ImageComparisonResult> results = new ArrayList<>();

    void put(ImageComparisonResult result) {
        results.add(result);
    }

    @Override
    public boolean hasDiff() {
        return getDiffTotal() > 0;
    }

    @Override
    public int getDiffTotal() {
        return (int) results.stream().filter(ImageComparisonResult::hasDiff).count();
    }

    @Override
    public List<ImageComparisonResult> getDiffResults() {
        return results;
    }
}

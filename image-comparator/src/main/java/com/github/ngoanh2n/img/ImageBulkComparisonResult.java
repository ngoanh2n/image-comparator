package com.github.ngoanh2n.img;

import java.util.List;

public interface ImageBulkComparisonResult {
    boolean hasDiff();

    int getDiffTotal();

    List<ImageComparisonResult> getDiffResults();
}

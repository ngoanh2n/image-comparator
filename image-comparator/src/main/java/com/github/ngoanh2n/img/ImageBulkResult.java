package com.github.ngoanh2n.img;

import java.util.ArrayList;
import java.util.List;

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

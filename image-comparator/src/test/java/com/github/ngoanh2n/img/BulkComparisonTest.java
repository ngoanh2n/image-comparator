package com.github.ngoanh2n.img;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author ngoanh2n
 */
public class BulkComparisonTest {
    @Test
    void compare() {
        Path exp = Paths.get("src/test/resources/com/github/ngoanh2n/img/exp/bulk_data");
        Path act = Paths.get("src/test/resources/com/github/ngoanh2n/img/act/bulk_data");

        ImageBulkComparisonResult result = ImageComparator.compare(exp, act);

        Assertions.assertFalse(result.hasDiff());
        Assertions.assertEquals(0, result.getDiffTotal());
        Assertions.assertEquals(3, result.getDiffResults().size());
    }
}

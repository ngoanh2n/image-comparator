package com.github.ngoanh2n.img;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.List;
import java.util.ServiceLoader;

@ParametersAreNonnullByDefault
public final class ImageComparator {
    private final static Logger log = LoggerFactory.getLogger(ImageComparator.class);
    private final BufferedImage exp;
    private final BufferedImage act;
    private final ImageComparisonOptions options;

    private ImageComparator(BufferedImage exp, BufferedImage act, ImageComparisonOptions options) {
        this.exp = exp;
        this.act = act;
        this.options = options;
    }

    public static ImageComparisonResult compare(BufferedImage exp, BufferedImage act) {
        return compare(exp, act, ImageComparisonOptions.defaults());
    }

    public static ImageComparisonResult compare(BufferedImage exp, BufferedImage act, ImageComparisonOptions options) {
        return new ImageComparator(exp, act, options).compare();
    }

    private static boolean equalBytes(BufferedImage exp, BufferedImage act) {
        return exp.getHeight() == act.getHeight() &&
                exp.getWidth() == act.getWidth() &&
                act.getColorModel().equals(exp.getColorModel()) &&
                equalBuffers(exp.getRaster().getDataBuffer(), act.getRaster().getDataBuffer());
    }

    //-------------------------------------------------------------------------------//
    
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
        return null;
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

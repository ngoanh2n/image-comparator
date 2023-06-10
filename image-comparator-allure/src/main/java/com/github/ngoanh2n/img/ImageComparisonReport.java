package com.github.ngoanh2n.img;

import com.github.ngoanh2n.Property;
import com.github.ngoanh2n.RuntimeError;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Write Allure results while comparing.
 *
 * @author ngoanh2n
 */
public class ImageComparisonReport implements ImageComparisonVisitor {
    /**
     * Indicate which attaches image sources to Allure report.<br>
     * Default to {@code true}.
     */
    public static final Property<Boolean> includeSource = Property.ofBoolean("ngoanh2n.img.includeSource", true);
    /**
     * Indicate which attaches comparison result (diff image and deviation) to Allure report.<br>
     * Default to {@code true}.
     */
    public static final Property<Boolean> includeResult = Property.ofBoolean("ngoanh2n.img.includeResult", true);
    private static final Logger log = LoggerFactory.getLogger(ImageComparisonReport.class);

    //-------------------------------------------------------------------------------//

    private String uuid;
    private AllureLifecycle lifecycle;

    /**
     * Default constructor.
     */
    public ImageComparisonReport() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    public void comparisonStarted(ImageComparisonOptions options, BufferedImage exp, BufferedImage act) {
        uuid = UUID.randomUUID().toString();
        lifecycle = Allure.getLifecycle();

        StepResult result = new StepResult().setName("Image Comparison");
        lifecycle.startStep(uuid, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void comparisonFinished(ImageComparisonOptions options, BufferedImage exp, BufferedImage act, ImageComparisonResult result) {
        attachSource(exp, act);
        attachResult(result);

        if (result.isDifferent()) {
            lifecycle.updateStep(uuid, sr -> sr.setStatus(Status.FAILED));
        } else {
            lifecycle.updateStep(uuid, sr -> sr.setStatus(Status.PASSED));
        }
        lifecycle.stopStep(uuid);
    }

    //-------------------------------------------------------------------------------//

    private void attachSource(BufferedImage exp, BufferedImage act) {
        if (includeSource.getValue()) {
            attachSource(exp, "Source: Exp Image");
            attachSource(act, "Source: Act Image");
        }
    }

    private void attachResult(ImageComparisonResult result) {
        if (includeResult.getValue()) {
            if (result.isDifferent()) {
                attachSource(result.getDiffImage(), "Result: Diff Image");
                attachDeviation(result);
            }
        }
    }

    private void attachSource(BufferedImage image, String fileDesc) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            byte[] bytes = os.toByteArray();
            lifecycle.addAttachment(fileDesc, "image/png", "", bytes);
        } catch (IOException e) {
            String msg = "Write image to OutputStream";
            log.error(msg);
            throw new RuntimeError(msg, e);
        }
    }

    private void attachDeviation(ImageComparisonResult result) {
        String deviation = String.join("\n",
                "Allowed: " + result.getAllowedDeviation(),
                "Current: " + result.getCurrentDeviation());
        lifecycle.addAttachment("Result: Deviation", "text/plain", "", deviation.getBytes());
    }
}

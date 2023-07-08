package com.github.ngoanh2n.img;

import com.github.ngoanh2n.PropertiesFile;
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
 * Write Allure results while comparing.<br><br>
 *
 * <b>System Property</b>
 * <ul>
 *     <li>{@code ngoanh2n.img.includeSource}<br>
 *         Indicate which attaches image sources to Allure report. Default to {@code true}.
 *     </li>
 *     <li>{@code ngoanh2n.img.includeResult}<br>
 *         Indicate which attaches comparison result (diff image and deviation) to Allure report. Default to {@code true}.
 *     </li>
 * </ul>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/image-comparator">ngoanh2n/image-comparator</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/image-comparator-allure">com.github.ngoanh2n:image-comparator-allure</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2021
 */
public class ImageComparisonReport implements ImageComparisonVisitor {
    private static final Logger log = LoggerFactory.getLogger(ImageComparisonReport.class);
    private static final PropertiesFile allureDesc = new PropertiesFile("image-comparator-allure.properties");
    private static final Property<Boolean> includeSource = Property.ofBoolean("ngoanh2n.img.includeSource", true);
    private static final Property<Boolean> includeResult = Property.ofBoolean("ngoanh2n.img.includeResult", true);

    //-------------------------------------------------------------------------------//

    private String uuid;
    private AllureLifecycle lifecycle;

    /**
     * Default constructor.
     */
    public ImageComparisonReport() { /**/ }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    public void comparisonStarted(ImageComparisonOptions options, BufferedImage exp, BufferedImage act) {
        uuid = UUID.randomUUID().toString();
        lifecycle = Allure.getLifecycle();

        StepResult result = new StepResult().setName(allureDesc.getProperty("subject"));
        lifecycle.startStep(uuid, result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void comparisonFinished(ImageComparisonOptions options, BufferedImage exp, BufferedImage act, ImageComparisonResult result) {
        attachSource(exp, act);
        attachResult(result);

        if (result.hasDiff()) {
            lifecycle.updateStep(uuid, sr -> sr.setStatus(Status.FAILED));
        } else {
            lifecycle.updateStep(uuid, sr -> sr.setStatus(Status.PASSED));
        }
        lifecycle.stopStep(uuid);
    }

    //-------------------------------------------------------------------------------//

    private void attachSource(BufferedImage exp, BufferedImage act) {
        if (includeSource.getValue()) {
            attachSource(exp, allureDesc.getProperty("expImage"));
            attachSource(act, allureDesc.getProperty("actImage"));
        }
    }

    private void attachResult(ImageComparisonResult result) {
        if (includeResult.getValue()) {
            if (result.hasDiff()) {
                attachSource(result.getDiffImage(), allureDesc.getProperty("resultImage"));
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
        lifecycle.addAttachment(allureDesc.getProperty("resultDeviation"), "text/plain", "", deviation.getBytes());
    }
}

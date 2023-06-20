package com.github.ngoanh2n.img;

import java.awt.*;

/**
 * Adjust behaviors of {@link ImageComparator}.<br><br>
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
public interface ImageComparisonOptions {
    /**
     * Get {@link Builder} class where allows to build your {@link ImageComparisonOptions}.
     *
     * @return A {@link Builder}.
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * Get {@link ImageComparisonOptions} with default options.
     *
     * @return A {@link ImageComparisonOptions}.
     */
    static ImageComparisonOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    /**
     * The deviation for making a judgment on whether images are different corresponds to the allowed deviation.
     *
     * @return Allowed deviation.
     */
    double allowedDeviation();

    /**
     * Color for decorating at ignored pixels after compared.
     *
     * @return A {@link Color}.
     */
    Color disregardColor();

    /**
     * Color for decorating at different pixels after compared.
     *
     * @return A {@link Color}.
     */
    Color differenceColor();

    /**
     * The comparison result options to adjust behaviors of {@link ImageComparisonResult}.
     *
     * @return A {@link ImageComparisonResultOptions}.
     */
    ImageComparisonResultOptions resultOptions();

    //===============================================================================//

    /**
     * Build a {@link ImageComparisonOptions}.
     */
    final class Builder {
        private double deviation;
        private Color disregardColor;
        private Color differenceColor;
        private ImageComparisonResultOptions resultOptions;

        private Builder() {
            this.deviation = 0.0;
            this.disregardColor = Color.GRAY;
            this.differenceColor = Color.RED;
            this.resultOptions = ImageComparisonResultOptions.defaults();
        }

        /**
         * Set deviation for making a judgment on whether images are different corresponds to the allowed deviation.
         *
         * @param value Allowed deviation.
         * @return The current {@link Builder}.
         */
        public Builder setAllowedDeviation(double value) {
            this.deviation = value;
            return this;
        }

        /**
         * Set color for decorating at ignored pixels after compared.
         *
         * @param value A {@link Color}.
         * @return The current {@link Builder}.
         */
        public Builder setDisregardColor(Color value) {
            this.disregardColor = value;
            return this;
        }

        /**
         * Set color for decorating at different pixels after compared.
         *
         * @param value A {@link Color}.
         * @return The current {@link Builder}.
         */
        public Builder setDifferenceColor(Color value) {
            this.differenceColor = value;
            return this;
        }

        /**
         * Set {@link ImageComparisonResultOptions} to adjust {@link ImageComparisonResult} output.
         *
         * @param value A {@link ImageComparisonResultOptions}.
         * @return The current {@link Builder}.
         */
        public Builder setResultOptions(ImageComparisonResultOptions value) {
            this.resultOptions = value;
            return this;
        }

        /**
         * Build {@link ImageComparisonOptions} based on {@link Builder}.
         *
         * @return A {@link ImageComparisonOptions}.
         */
        public ImageComparisonOptions build() {
            return new ImageComparisonOptions() {
                @Override
                public double allowedDeviation() {
                    return deviation;
                }

                @Override
                public Color disregardColor() {
                    return disregardColor;
                }

                @Override
                public Color differenceColor() {
                    return differenceColor;
                }

                @Override
                public ImageComparisonResultOptions resultOptions() {
                    return resultOptions;
                }
            };
        }
    }
}

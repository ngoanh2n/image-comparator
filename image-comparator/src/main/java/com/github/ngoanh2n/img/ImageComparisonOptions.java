package com.github.ngoanh2n.img;

import java.awt.*;

/**
 * This class allows to adjust {@link ImageComparator} by your expectation.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public interface ImageComparisonOptions {
    /**
     * Get {@link Builder} class where allows to build your {@link ImageComparisonOptions}.
     *
     * @return {@link ImageComparisonOptions.Builder}.
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * Get {@link ImageComparisonOptions} with default options.
     *
     * @return {@link ImageComparisonOptions}.
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
    double deviation();

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
     * The comparison result options to adjust your {@link ImageComparisonResult} output.
     *
     * @return {@link ImageComparisonResultOptions}.
     */
    ImageComparisonResultOptions resultOptions();

    //===============================================================================//

    /**
     * This class allows to build {@link ImageComparisonOptions}.
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
         * @return {@link ImageComparisonOptions.Builder}.
         */
        public Builder setDeviation(double value) {
            this.deviation = value;
            return this;
        }

        /**
         * Set color for decorating at ignored pixels after compared.
         *
         * @param value {@link Color} to decorate differences.
         * @return {@link ImageComparisonOptions.Builder}.
         */
        public Builder setDisregardColor(Color value) {
            this.disregardColor = value;
            return this;
        }

        /**
         * Set color for decorating at different pixels after compared.
         *
         * @param value {@link Color} to decorate differences.
         * @return {@link ImageComparisonOptions.Builder}.
         */
        public Builder setDifferenceColor(Color value) {
            this.differenceColor = value;
            return this;
        }

        /**
         * Set {@link ImageComparisonResultOptions} to adjust {@link ImageComparisonResult} output.
         *
         * @param value to adjust output.
         * @return {@link ImageComparisonOptions.Builder}.
         */
        public Builder setResultOptions(ImageComparisonResultOptions value) {
            this.resultOptions = value;
            return this;
        }

        /**
         * Build {@link ImageComparisonOptions} based on {@link ImageComparisonOptions.Builder}.
         *
         * @return {@link ImageComparisonOptions}.
         */
        public ImageComparisonOptions build() {
            return new ImageComparisonOptions() {
                @Override
                public double deviation() {
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

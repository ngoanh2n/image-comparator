package com.github.ngoanh2n.img;

import java.awt.*;

public interface ImageComparisonOptions {
    static Builder builder() {
        return new Builder();
    }

    static ImageComparisonOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    double deviation();

    Color disregardColor();

    Color differenceColor();

    ImageComparisonResultOptions resultOptions();

    //===============================================================================//

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

        public Builder setDeviation(double value) {
            this.deviation = value;
            return this;
        }

        public Builder setDisregardColor(Color value) {
            this.disregardColor = value;
            return this;
        }

        public Builder setDifferenceColor(Color value) {
            this.differenceColor = value;
            return this;
        }

        public Builder setResultOptions(ImageComparisonResultOptions value) {
            this.resultOptions = value;
            return this;
        }

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

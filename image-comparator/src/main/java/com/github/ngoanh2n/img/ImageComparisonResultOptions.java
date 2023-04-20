package com.github.ngoanh2n.img;

import com.google.common.base.Preconditions;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class allows to adjust {@link ImageComparisonResult} output by your expectation.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@ParametersAreNonnullByDefault
public interface ImageComparisonResultOptions {
    /**
     * Gets {@link ImageComparisonResultOptions.Builder} class
     * where allows to build your {@link ImageComparisonResultOptions}.
     *
     * @return {@link ImageComparisonResultOptions.Builder}.
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * Gets {@link ImageComparisonResultOptions} with default options.
     *
     * @return {@link ImageComparisonResultOptions}.
     */
    static ImageComparisonResultOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    /**
     * Where you want to store comparison result output files.
     *
     * @return path to location.
     */
    Path location();

    /**
     * Whether wrote output files.
     *
     * @return Indicate to write output files.
     */
    boolean writeOutput();

    //===============================================================================//

    /**
     * This class allows to build {@link ImageComparisonResultOptions}.
     */
    final class Builder {
        private Path location;
        private boolean writeOutput;

        private Builder() {
            writeOutput = true;
            location = Paths.get("build/ngoanh2n/img");
        }

        /**
         * Set location where you want to store comparison result output file.
         *
         * @param value The path to store output.
         * @return {@link Builder}.
         */
        public Builder setLocation(Path value) {
            location = Preconditions.checkNotNull(value, "path cannot not be null");
            return this;
        }

        /**
         * Indicate which writes output files.
         *
         * @param enabled The flag whether that includes or not.
         * @return {@link Builder}.
         */
        public Builder writeOutput(boolean enabled) {
            writeOutput = enabled;
            return this;
        }

        /**
         * Build {@link ImageComparisonResultOptions} based on {@link Builder}.
         *
         * @return {@link ImageComparisonResultOptions}.
         */
        public ImageComparisonResultOptions build() {
            return new ImageComparisonResultOptions() {
                @Override
                public Path location() {
                    return location;
                }

                @Override
                public boolean writeOutput() {
                    return writeOutput;
                }
            };
        }
    }
}

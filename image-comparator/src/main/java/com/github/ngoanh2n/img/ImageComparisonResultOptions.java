package com.github.ngoanh2n.img;

import com.google.common.base.Preconditions;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Adjust behaviors of {@link ImageComparisonResult}.
 *
 * @author ngoanh2n
 */
@ParametersAreNonnullByDefault
public interface ImageComparisonResultOptions {
    /**
     * Get {@link ImageComparisonResultOptions.Builder} class where allows to build your {@link ImageComparisonResultOptions}.
     *
     * @return A {@link ImageComparisonResultOptions.Builder}.
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * Get {@link ImageComparisonResultOptions} with default options.
     *
     * @return A {@link ImageComparisonResultOptions}.
     */
    static ImageComparisonResultOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    /**
     * Where you want to store comparison result output file.
     *
     * @return The path to location.
     */
    Path location();

    /**
     * Whether wrote output file.
     *
     * @return Indicate to write output file.
     */
    boolean writeOutput();

    //===============================================================================//

    /**
     * Build a {@link ImageComparisonResultOptions}.
     */
    final class Builder {
        private Path location;
        private boolean writeOutput;

        private Builder() {
            this.location = Paths.get("build/ngoanh2n/img");
            this.writeOutput = true;
        }

        /**
         * Set location where you want to store comparison result output file.
         *
         * @param value The path to store output.
         * @return {@link Builder}.
         */
        public Builder setLocation(Path value) {
            this.location = Preconditions.checkNotNull(value, "path cannot not be null");
            return this;
        }

        /**
         * Indicate which writes output files.
         *
         * @param enabled The flag whether that includes or not.
         * @return {@link Builder}.
         */
        public Builder writeOutput(boolean enabled) {
            this.writeOutput = enabled;
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

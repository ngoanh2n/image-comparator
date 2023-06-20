package com.github.ngoanh2n.img;

import com.google.common.base.Preconditions;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Adjust behaviors of {@link ImageComparisonResult} and {@link ImageComparisonOutput}.<br><br>
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
@ParametersAreNonnullByDefault
public interface ImageComparisonResultOptions {
    /**
     * Get {@link Builder} class where allows to build your {@link ImageComparisonResultOptions}.
     *
     * @return A {@link Builder}.
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
     * Where you want to store comparison result output files.
     *
     * @return The path to location.
     */
    Path location();

    /**
     * Whether wrote output files.
     *
     * @return Indicate to write output files.
     */
    boolean writeOutputs();

    //===============================================================================//

    /**
     * Build a {@link ImageComparisonResultOptions}.
     */
    final class Builder {
        private Path location;
        private boolean writeOutputs;

        private Builder() {
            this.location = Paths.get("build/ngoanh2n/img");
            this.writeOutputs = true;
        }

        /**
         * Set location where you want to store comparison result output files.
         *
         * @param path The path to location.
         * @return The current {@link Builder}.
         */
        public Builder setLocation(Path path) {
            this.location = Preconditions.checkNotNull(path, "path cannot not be null");
            return this;
        }

        /**
         * Indicate which writes output files.
         *
         * @param enabled The flag whether that includes or not.
         * @return The current {@link Builder}.
         */
        public Builder writeOutputs(boolean enabled) {
            this.writeOutputs = enabled;
            return this;
        }

        /**
         * Build {@link ImageComparisonResultOptions} based on {@link Builder}.
         *
         * @return A {@link ImageComparisonResultOptions}.
         */
        public ImageComparisonResultOptions build() {
            return new ImageComparisonResultOptions() {
                @Override
                public Path location() {
                    return location;
                }

                @Override
                public boolean writeOutputs() {
                    return writeOutputs;
                }
            };
        }
    }
}

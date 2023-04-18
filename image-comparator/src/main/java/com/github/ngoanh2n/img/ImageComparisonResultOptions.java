package com.github.ngoanh2n.img;

import com.google.common.base.Preconditions;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.nio.file.Paths;

@ParametersAreNonnullByDefault
public interface ImageComparisonResultOptions {
    static Builder builder() {
        return new Builder();
    }

    static ImageComparisonResultOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    Path location();

    boolean writeOutput();

    //===============================================================================//

    final class Builder {
        private Path location;
        private boolean writeOutput;

        private Builder() {
            writeOutput = true;
            location = Paths.get("build/ngoanh2n/img");
        }

        public Builder setLocation(Path value) {
            location = Preconditions.checkNotNull(value, "path cannot not be null");
            return this;
        }

        public Builder writeOutput(boolean enabled) {
            writeOutput = enabled;
            return this;
        }

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

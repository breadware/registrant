package br.com.breadware.model;

import java.io.File;

public class CloudStorageFileMappingLocation {

    private CloudStorageLocation cloudStorageLocation;

    private File mappedFile;

    public CloudStorageLocation getCloudStorageLocation() {
        return cloudStorageLocation;
    }

    public File getMappedFile() {
        return mappedFile;
    }


    public static final class Builder {
        private CloudStorageLocation cloudStorageLocation;
        private File mappedFile;

        private Builder() {
        }

        public static Builder aCloudStorageFileMappingLocation() {
            return new Builder();
        }

        public Builder cloudStorageLocation(CloudStorageLocation cloudStorageLocation) {
            this.cloudStorageLocation = cloudStorageLocation;
            return this;
        }

        public Builder mappedFile(File mappedFile) {
            this.mappedFile = mappedFile;
            return this;
        }

        public CloudStorageFileMappingLocation build() {
            CloudStorageFileMappingLocation cloudStorageFileMappingLocation = new CloudStorageFileMappingLocation();
            cloudStorageFileMappingLocation.cloudStorageLocation = this.cloudStorageLocation;
            cloudStorageFileMappingLocation.mappedFile = this.mappedFile;
            return cloudStorageFileMappingLocation;
        }
    }
}

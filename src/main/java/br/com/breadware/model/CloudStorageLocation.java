package br.com.breadware.model;

public class CloudStorageLocation {

    private String bucket;

    private String object;

    public String getBucket() {
        return bucket;
    }

    public String getObject() {
        return object;
    }

    public static final class Builder {
        private String bucket;
        private String object;

        private Builder() {
        }

        public static Builder aCloudStorageLocation() {
            return new Builder();
        }

        public Builder bucket(String bucket) {
            this.bucket = bucket;
            return this;
        }

        public Builder object(String object) {
            this.object = object;
            return this;
        }

        public CloudStorageLocation build() {
            CloudStorageLocation cloudStorageLocation = new CloudStorageLocation();
            cloudStorageLocation.bucket = bucket;
            cloudStorageLocation.object = object;
            return cloudStorageLocation;
        }
    }
}

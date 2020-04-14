package br.com.breadware.google.mail.message.analyser.model;

import br.com.breadware.model.Associate;

public class MessageAnalysisResult {

    private MessageAnalysisStatus status;

    private Associate associate;

    public MessageAnalysisStatus getStatus() {
        return status;
    }

    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    public static final class Builder {
        private MessageAnalysisStatus status;
        private Associate associate;

        private Builder() {
        }

        public static Builder aMessageAnalysisResult() {
            return new Builder();
        }

        public Builder status(MessageAnalysisStatus status) {
            this.status = status;
            return this;
        }

        public Builder associate(Associate associate) {
            this.associate = associate;
            return this;
        }

        public MessageAnalysisResult build() {
            MessageAnalysisResult messageAnalysisResult = new MessageAnalysisResult();
            messageAnalysisResult.setAssociate(associate);
            messageAnalysisResult.status = this.status;
            return messageAnalysisResult;
        }
    }
}

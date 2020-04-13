package br.com.breadware.subscriber.message.analyser;

import br.com.breadware.model.Associate;
import com.google.api.services.gmail.model.Message;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

public class MessageAnalysisContext {

    private Message message;

    private Optional<MimeMessage> mimeMessage;

    private Optional<String> messageContent;

    private Optional<Associate> associate;

    private MessageAnalysisStatus status;

    public MessageAnalysisContext(Message message) {
        this.message = message;
        mimeMessage = Optional.empty();
        messageContent = Optional.empty();
        associate = Optional.empty();
        status = MessageAnalysisStatus.UNDEFINED;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Optional<MimeMessage> getMimeMessage() {
        return mimeMessage;
    }

    public void setMimeMessage(Optional<MimeMessage> mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public Optional<String> getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(Optional<String> messageContent) {
        this.messageContent = messageContent;
    }

    public Optional<Associate> getAssociate() {
        return associate;
    }

    public void setAssociate(Optional<Associate> associate) {
        this.associate = associate;
    }

    public MessageAnalysisStatus getStatus() {
        return status;
    }

    public void setStatus(MessageAnalysisStatus status) {
        this.status = status;
    }
}

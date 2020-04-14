package br.com.breadware.google.mail.message.analyser.model;

import br.com.breadware.model.Associate;
import com.google.api.services.gmail.model.Message;

import javax.mail.internet.MimeMessage;

public class MessageAnalysisContext {

    private Message message;

    private MimeMessage mimeMessage;

    private String messageContent;

    private Associate associate;

    private MessageAnalysisStatus status;

    public MessageAnalysisContext(Message message) {
        this.message = message;
        mimeMessage = null;
        messageContent = null;
        associate = null;
        status = MessageAnalysisStatus.UNDEFINED;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public MimeMessage getMimeMessage() {
        return mimeMessage;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    public MessageAnalysisStatus getStatus() {
        return status;
    }

    public void setStatus(MessageAnalysisStatus status) {
        this.status = status;
    }
}

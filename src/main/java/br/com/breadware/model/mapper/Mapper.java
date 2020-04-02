package br.com.breadware.model.mapper;

import javax.mail.MessagingException;

public interface Mapper<I, O> {

    O map(I input) throws Exception;
}

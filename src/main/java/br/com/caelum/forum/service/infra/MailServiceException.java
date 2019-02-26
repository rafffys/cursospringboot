package br.com.caelum.forum.service.infra;

public class MailServiceException extends RuntimeException {

    public MailServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

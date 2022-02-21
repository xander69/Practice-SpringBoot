package org.xander.practice.webapp.exception;

public class InvalidCaptchaException extends RuntimeException {
    public InvalidCaptchaException() {
        super("Fill the captcha");
    }
}

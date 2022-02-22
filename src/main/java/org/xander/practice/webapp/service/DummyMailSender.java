package org.xander.practice.webapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.xander.practice.webapp.exception.InternalServerException;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

@Slf4j
@SuppressWarnings("NullableProblems")
public class DummyMailSender implements JavaMailSender {

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS")
                    .withLocale(Locale.forLanguageTag("ru"))
                    .withZone(ZoneId.systemDefault());

    private final Path mailDir;

    public DummyMailSender(String mailPath) {
        this.mailDir = prepareMailPath(mailPath);
    }

    @Override
    public MimeMessage createMimeMessage() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MimeMessage createMimeMessage(InputStream inputStream) throws MailException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(SimpleMailMessage simpleMailMessage) throws MailException {
        saveMail(simpleMailMessage);
    }

    @Override
    public void send(SimpleMailMessage... simpleMailMessages) throws MailException {
        for (SimpleMailMessage simpleMailMessage : simpleMailMessages) {
            send(simpleMailMessage);
        }
    }

    private Path prepareMailPath(String mailPath) {
        Path mailDir = Paths.get(mailPath);
        if (!Files.exists(mailDir)) {
            try {
                log.info("Created mail path: {}", mailPath);
                Files.createDirectories(mailDir);
            } catch (IOException e) {
                throw new InternalServerException(e.toString(), e);
            }
        }
        return mailDir;
    }

    private void saveMail(SimpleMailMessage mailMessage) throws MailException {
        String mailFilename = formatter.format(Instant.now()) + ".eml";
        StringBuilder message = new StringBuilder()
                .append("From: ").append(mailMessage.getFrom()).append("\n")
                .append("To: ").append(Arrays.toString(mailMessage.getTo())).append("\n")
                .append("Subject: ").append(mailMessage.getSubject()).append("\n")
                .append("-------------------------------------------\n");
        if (mailMessage.getText() != null) {
            message.append(mailMessage.getText()).append("\n");
        }
        File mailFile = new File(mailDir.toFile(), mailFilename);
        try {
            Files.write(mailFile.toPath(), message.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new MailSendException(e.toString(), e);
        }
    }
}

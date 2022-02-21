package org.xander.practice.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xander.practice.webapp.exception.InvalidCaptchaException;
import org.xander.practice.webapp.model.CaptchaResponseModel;

import java.util.Collections;
import java.util.Objects;

@Service
public class CaptchaService {
    private static final Logger log = LoggerFactory.getLogger(CaptchaService.class);

    private final RestTemplate restTemplate;
    private final boolean recaptchaUse;
    private final String recaptchaSecret;
    private final String recaptchaUrl;

    @Autowired
    public CaptchaService(RestTemplate restTemplate,
                          @Value("${recaptcha.use:false}") boolean recaptchaUse,
                          @Value("${recaptcha.secret}") String recaptchaSecret,
                          @Value("${recaptcha.url}") String recaptchaUrl) {
        this.restTemplate = restTemplate;
        this.recaptchaUse = recaptchaUse;
        this.recaptchaSecret = recaptchaSecret;
        this.recaptchaUrl = recaptchaUrl;
    }

    public void checkCaptcha(String captchaResponse) {
        if (recaptchaUse) {
            String url = String.format(recaptchaUrl, recaptchaSecret, captchaResponse);
            CaptchaResponseModel response = restTemplate
                    .postForObject(url, Collections.emptyList(), CaptchaResponseModel.class);
            if (Objects.nonNull(response) && !response.isSuccess()) {
                response.getErrorCodes().forEach(log::warn);
                throw new InvalidCaptchaException();
            }
        }
    }
}

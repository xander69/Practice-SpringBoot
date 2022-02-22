package org.xander.practice.webapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xander.practice.webapp.exception.InvalidCaptchaException;
import org.xander.practice.webapp.model.CaptchaResponseModel;

import java.util.Collections;
import java.util.Objects;

@Slf4j
@Service
public class CaptchaService {

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
            log.debug("captcha request: {}", url);
            CaptchaResponseModel response = restTemplate
                    .postForObject(url, Collections.emptyList(), CaptchaResponseModel.class);
            log.debug("captcha response: {}", response);
            if (Objects.nonNull(response) && !response.isSuccess()) {
                response.getErrorCodes().forEach(log::warn);
                throw new InvalidCaptchaException();
            }
        }
    }
}

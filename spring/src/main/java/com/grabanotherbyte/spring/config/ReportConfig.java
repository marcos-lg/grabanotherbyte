package com.grabanotherbyte.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix = "report")
public class ReportConfig implements Validator {

  @NotBlank private String targetFile;
  private boolean sendByEmail;
  private String emailSubject;
  private String recipient;

  public String getTargetFile() {
    return targetFile;
  }

  public void setTargetFile(String targetFile) {
    this.targetFile = targetFile;
  }

  public boolean isSendByEmail() {
    return sendByEmail;
  }

  public void setSendByEmail(boolean sendByEmail) {
    this.sendByEmail = sendByEmail;
  }

  public String getEmailSubject() {
    return emailSubject;
  }

  public void setEmailSubject(String emailSubject) {
    this.emailSubject = emailSubject;
  }

  public String getRecipient() {
    return recipient;
  }

  public void setRecipient(String recipient) {
    this.recipient = recipient;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return ReportConfig.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ReportConfig config = (ReportConfig) target;

    if (config.isSendByEmail()) {
      ValidationUtils.rejectIfEmpty(
          errors, "emailSubject", "required-non-empty", "Email subject is required");
      ValidationUtils.rejectIfEmpty(
          errors, "recipient", "required-non-empty", "Recipient is required");
    }
  }
}

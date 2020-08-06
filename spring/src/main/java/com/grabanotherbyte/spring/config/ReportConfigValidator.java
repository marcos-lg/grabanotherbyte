package com.grabanotherbyte.spring.config;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ReportConfigValidator implements Validator {

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

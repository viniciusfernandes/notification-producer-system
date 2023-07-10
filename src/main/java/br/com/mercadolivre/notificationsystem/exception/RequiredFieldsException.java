package br.com.mercadolivre.notificationsystem.exception;

import java.util.List;

public class RequiredFieldsException extends Exception {
  public List<String> validationMessages;

  public RequiredFieldsException(String message) {
    super(message);
  }

  public RequiredFieldsException(String message, List<String> validationMessages) {
    super(message);
    this.validationMessages = validationMessages;
  }

  public List<String> getValidationMessages() {
    return validationMessages;
  }
}

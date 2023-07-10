package br.com.mercadolivre.notificationproducersystem.stepdefs;

import br.com.mercadolivre.notificationproducersystem.utils.Endpoint;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AdvertisementNotificationSteps {
  private Endpoint endpoint;
  private String requestFile;
  private final String advertisementNotificationURL = "/advertisement-notifications";

  @Given("the notification request {string}")
  public void theNotificationRequestRequestsAdvertisementNotificationJson(String requestFile) {
    this.requestFile = requestFile;
  }

  @When("I {string} this request to the the advertisement notification endpoint")
  public void iPOSTThisRequestToTheTheAdvertisementNotificationEndpoint(String method) {
    endpoint = Endpoint.build()
        .method(method)
        .url(advertisementNotificationURL)
        .requestBodyFile(requestFile);
  }

  @Then("I get {int} as response status")
  public void iGetAsResponseStatus(int statusCode) {
    endpoint.httpStatus(statusCode).mock();
  }

  @And("I have one new notification record in my data base")
  public void iHaveOneNewNotificationRecordInMyDataBase() {
    //advertisementService.findAll();
  }
}

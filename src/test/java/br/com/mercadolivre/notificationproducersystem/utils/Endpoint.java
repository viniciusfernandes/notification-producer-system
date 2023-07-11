package br.com.mercadolivre.notificationproducersystem.utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Endpoint {
  private String url;
  private String queryParams;
  private HttpMethod method;
  private int httpStatus;
  private String requestBodyFile;
  private String responseBodyFile;
  private static WireMockServer wireMockServer;

  public static Endpoint build() {
    init();
    return new Endpoint();
  }

  private static void init() {
    if (wireMockServer == null) {
      wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8080)); // Set the desired port
      wireMockServer.start();
    }
  }

  public Endpoint delete(String url) {
    this.url = url;
    this.method = HttpMethod.DELETE;
    return this;
  }

  public Endpoint get(String url) {
    this.url = url;
    this.method = HttpMethod.GET;
    return this;
  }

  public Endpoint patch(String url) {
    this.url = url;
    this.method = HttpMethod.PATCH;
    return this;
  }

  public Endpoint post(String url) {
    this.url = url;
    this.method = HttpMethod.POST;
    return this;
  }

  public Endpoint put(String url) {
    this.url = url;
    this.method = HttpMethod.PUT;
    return this;
  }

  public Endpoint responseBodyFile(String responseBodyFile) {
    this.responseBodyFile = responseBodyFile;
    return this;
  }

  public Endpoint requestBodyFile(String requestBodyFile) {
    this.requestBodyFile = requestBodyFile;
    return this;
  }

  public Endpoint httpStatus(int httpStatus) {
    this.httpStatus = httpStatus;
    return this;
  }

  public Endpoint queryParams(String queryParams) {
    this.queryParams = queryParams;
    return this;
  }

  public Endpoint method(String method) {
    this.method = HttpMethod.valueOf(method);
    return this;
  }

  public Endpoint url(String url) {
    this.url = url;
    return this;
  }

  private boolean mocked;

  public void mock() {
    if (mocked) {
      throw new IllegalStateException(
          "This endpoint %s:%s was already mocked".formatted(method, url));
    }
    WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
        .willReturn(WireMock.aResponse()
            .withStatus(httpStatus)
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBodyFile("classpath:/requests/" + requestBodyFile)));

    mocked = true;
  }

  private MappingBuilder buildResource() {
    MappingBuilder resource = null;
    if (method == HttpMethod.DELETE) {
      resource = WireMock.delete(WireMock.urlEqualTo(url));
    } else if (method == HttpMethod.GET) {
      resource = WireMock.get(WireMock.urlEqualTo(url));
      WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
          .willReturn(null));

    } else if (method == HttpMethod.PATCH) {
      resource = WireMock.patch(WireMock.urlEqualTo(url));
    } else if (method == HttpMethod.POST) {
      resource = WireMock.post(WireMock.urlEqualTo(url));
    } else if (method == HttpMethod.PUT) {
      resource = WireMock.put(WireMock.urlEqualTo(url));

    }

    queryParams(resource);
    var response = WireMock.aResponse()
        .withStatus(httpStatus)
        .withHeader("Content-Type", "application/json");
    responseBody(response);
    return resource.willReturn(response);
  }

  private void responseBody(ResponseDefinitionBuilder response) {
    if (responseBodyFile != null) {
      response.withBodyFile("classpath:/requests/" + requestBodyFile);

    }
  }

  private void queryParams(MappingBuilder resource) {
    if (queryParams != null) {
      for (var param : queryParams.split("&")) {
        var val = param.split("=");
        resource.withQueryParam(val[0], WireMock.equalTo(val[1]));
      }
    }
  }
}
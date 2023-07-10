package br.com.mercadolivre.notificationproducersystem;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:/features")
public class CucumberTestRunner {
  // No implementation needed
}

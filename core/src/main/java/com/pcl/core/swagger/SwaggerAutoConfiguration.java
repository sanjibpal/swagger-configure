package com.pcl.core.swagger;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enabled")
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {
	private Logger logger = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);
	private final SwaggerProperties properties;

	public SwaggerAutoConfiguration(SwaggerProperties properties) {
		this.properties = properties;
	}

	@Bean
	public Docket customImplementation() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build()
				.pathMapping("/").useDefaultResponseMessages(false)
				// .operationOrdering(new O)
				.globalResponseMessage(RequestMethod.POST, getResponseMessages())
				.globalResponseMessage(RequestMethod.GET, getResponseMessages())
				.globalOperationParameters(getSwaggerGlobalParameters()).apiInfo(getSwaggerApiInfo())
				// .ignoredParameterTypes(ApiIgnore.class) // hack for swagger 2.5.0 - ApiIgnore
				// was not really ignored from the docs
				.enable(isSwaggerEnabled());
	}

	private List<ResponseMessage> getResponseMessages() {
		return Stream.of(
				new ResponseMessageBuilder().code(NOT_FOUND.value())
				.message("Exception stack trace. Recorded in splunk").responseModel(null).build(),
				new ResponseMessageBuilder().code(FORBIDDEN.value())
				.message("Exception stack trace. Recorded in splunk").responseModel(null).build()
				)
		.collect(toList());
	}

	private Boolean isSwaggerEnabled() {
		if (properties.isEnabled()) {
			logger.info("Swagger documentation will be generated.");
		} else {
			logger.error(
					"Swagger documentation will NOT be generated. Make sure that the JVM property hostname is set.");
		}
		return properties.isEnabled();
	}

	protected List<Parameter> getSwaggerGlobalParameters() {
		return properties.getGlobalParameters().stream()
				.map(parameter -> new ParameterBuilder().name(parameter.getName())
						.description(parameter.getDescription()).modelRef(new ModelRef("string"))
						.parameterType(parameter.getParameterType()).defaultValue(parameter.getDefaultValue())
						.allowableValues(parameter.getAllowedValues() != null
								? new AllowableListValues(parameter.getAllowedValues(), "string")
								: null)
						.required(true).build())
				.collect(toList());
	}

	protected ApiInfo getSwaggerApiInfo() {
		return new ApiInfo(properties.getTitle(), properties.getDescription(), properties.getVersion(), "urn:tos",
				ApiInfo.DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",
				new ArrayList<VendorExtension>());
	}
}

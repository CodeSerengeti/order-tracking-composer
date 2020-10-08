package skyglass.composer.order.web;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT = new Contact(
			"Mykhailo Skliar", "https://github.com/skyglass", "skyglass2001@gmail.com");

	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
			"Awesome API Title", "Awesome API Description", "1.0",
			"urn:tos", DEFAULT_CONTACT,
			"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Arrays.asList());

	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(Arrays.asList("application/json",
			"application/xml"));

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO)
				.produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES)
				.protocols(new HashSet<>(Arrays.asList("HTTP")))
				.genericModelSubstitutes(ResponseEntity.class, CompletableFuture.class)
				.useDefaultResponseMessages(false)
				.alternateTypeRules(
						newRule(typeResolver.resolve(DeferredResult.class,
								typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
								typeResolver.resolve(WildcardType.class)))
				.select()
				.apis(RequestHandlerSelectors.basePackage("skyglass.composer.order"))
				.paths(PathSelectors.any())
				.build();
	}

	@Autowired
	private TypeResolver typeResolver;
}

package com.pcl.store.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping({ "/v1.0/" })
@Api(value = "product", description = "SearchLight and voyage data", produces = "application/json,text/xml", consumes = "application/json,text/xml")
public class ProductController {

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public List<ProductDto> getProducts() {
		ProductDto product = new ProductDto("1234", "A", "B", "Swagger with auto-configuration is cool!!!");
		return Arrays.asList(product);
	}
}

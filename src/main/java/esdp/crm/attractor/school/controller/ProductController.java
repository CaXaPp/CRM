package esdp.crm.attractor.school.controller;

import esdp.crm.attractor.school.dto.ProductDto;
import esdp.crm.attractor.school.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/product-list")
    public ResponseEntity<List<ProductDto>> getProductNameAndId() {
        return new ResponseEntity<>(productService.getProductNameAndId(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ProductDto>> getProductsByDepartmentId(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductByDepartmentId(id), HttpStatus.OK);
    }
}

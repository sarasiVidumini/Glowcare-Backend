package lk.ijse.triglowglowcare_backend.controller;

import lk.ijse.triglowglowcare_backend.dto.ProductDTO;
import lk.ijse.triglowglowcare_backend.service.impl.ProductServiceImpl;
import lk.ijse.triglowglowcare_backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated

public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @PostMapping
    public ResponseEntity<APIResponse<String>> saveProduct(@RequestBody ProductDTO productDTO) {
        productServiceImpl.saveProduct(productDTO);
        return new ResponseEntity<>(new APIResponse<>(201,"Product saved Successfully",null), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateProduct(@RequestBody ProductDTO productDTO) {
        productServiceImpl.updateProduct(productDTO);
        return new ResponseEntity<>(new APIResponse<>(200,"Product updated Successfully",null), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<APIResponse<String>> deleteProduct(@PathVariable long productId) {
        productServiceImpl.deleteProduct(productId);
        return new ResponseEntity<>(new APIResponse<>(200,"Product deleted Successfully",null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = productServiceImpl.getAllProducts();
        return new ResponseEntity<>(new APIResponse<>(200,"Product List Successfully",products), HttpStatus.OK);
    }
}

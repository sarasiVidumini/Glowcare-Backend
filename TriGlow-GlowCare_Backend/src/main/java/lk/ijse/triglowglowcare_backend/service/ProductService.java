package lk.ijse.triglowglowcare_backend.service;

import lk.ijse.triglowglowcare_backend.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    public void saveProduct(ProductDTO productDTO);
    public void updateProduct(ProductDTO productDTO);
    public void deleteProduct(long productId);
    public List<ProductDTO> getAllProducts();

}

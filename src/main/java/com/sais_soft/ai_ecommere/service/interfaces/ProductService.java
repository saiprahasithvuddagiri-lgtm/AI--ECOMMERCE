package com.sais_soft.ai_ecommere.service.interfaces;

import java.util.List;

import com.sais_soft.ai_ecommere.Entity.Product;
import com.sais_soft.ai_ecommere.dto.ProductResponseDTO;
import com.sais_soft.ai_ecommere.dto.ProductSearchDTO;
import com.sais_soft.ai_ecommere.dto.saveProductDTO;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();
    
    List<ProductResponseDTO> searchProducts(ProductSearchDTO searchDto);
    
    ProductResponseDTO saveProduct(saveProductDTO  product);
    
}

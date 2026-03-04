package com.sais_soft.ai_ecommere.service.interfaces;

import java.util.List;

import com.sais_soft.ai_ecommere.dto.ProductResponseDTO;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();
}

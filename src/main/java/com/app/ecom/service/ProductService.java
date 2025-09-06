package com.app.ecom.service;

import com.app.ecom.dto.product.ProductRequest;
import com.app.ecom.dto.product.ProductResponse;
import com.app.ecom.entites.product.Product;
import com.app.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ModelMapper modelMapper;
    private final   ProductRepository productRepository;

    public List<ProductResponse> fetchAllProducts(){
        return productRepository.findByActiveTrue().stream().map(product -> modelMapper.map(product,ProductResponse.class)).toList();
    }

    public void addProduct(ProductRequest productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);
        productRepository.save(product);

    }

    public Boolean deleteProduct(Long id){
       return productRepository.findById(id).map(product -> {
           product.setActive(false);
           productRepository.save(product);
           return true;
       }).orElse(false);




    }

    public Boolean updateProduct(Long id,ProductRequest updatedProductRequest) {
        return productRepository.findById(id).
                map(existingproduct-> {
                        modelMapper.map(updatedProductRequest,Product.class);
                        productRepository.save(existingproduct);
                        return true;
    }).orElse(false);

    }


    public List<ProductResponse> searchProducts(String keyword) {
       return productRepository.searchProducts(keyword).stream().map(product -> modelMapper.map(product,ProductResponse.class)).toList();

    }
}



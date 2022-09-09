package com.assignment.shoponline.service;

import com.assignment.shoponline.entity.Product;
import com.assignment.shoponline.entity.dto.ProductDto;
import com.assignment.shoponline.entity.search.ProductSpecification;
import com.assignment.shoponline.entity.search.base.SearchCriteria;
import com.assignment.shoponline.entity.search.base.SearchCriteriaOperator;
import com.assignment.shoponline.repository.ProductRepository;
import com.assignment.shoponline.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    final ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    /**
     * Tìm kiếm sản phẩm theo id
     * @param id Id cua san pham
     * @return Optional< Product >
     */
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Xóa hoàn toàn khỏi database
     * @param id
     */
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Tạo mới một sản phẩm và lưu vào database
     * @param productDto
     * @param adminId
     * @return product
     */
    public Product create(ProductDto productDto, Long adminId) {
        if (!productDto.isDataValid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fail to create new product");
        }
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        product.setCreatedBy(adminId);
        return save(product);
    }

    /**
     * Update thông tin của một sản phẩm
     * @param productDto
     * @param existProduct
     * @param adminId
     * @return product
     */
    public Product update(ProductDto productDto, Product existProduct, Long adminId) {
        if (!productDto.isDataValid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fail to update product information");
        }
        else {
            BeanUtils.copyProperties(productDto, existProduct);
            existProduct.setUpdatedBy(adminId);
            existProduct.setUpdatedAt(LocalDateTime.now());
            return save(existProduct);
        }
    }

    /**
     * Update trạng thái của sản phẩm thành DELETED, chưa xóa khỏi database
     * @param existProduct
     * @param adminId
     * @return product
     */
    public Product delete(Product existProduct, Long adminId) {
        existProduct.setStatus(Enums.ProductStatus.DELETED);
        existProduct.setDeletedAt(LocalDateTime.now());
        existProduct.setDeletedBy(adminId);
        return save(existProduct);
    }

    /**
     * Bộ lọc lấy danh sách sản phẩm từ database
     * @param name
     * @param priceFrom
     * @param priceTo
     * @param status
     * @param category
     * @param pageable
     * @return Page< Product >
     */
    public Page<Product> search(String name, int priceFrom, int priceTo, Enums.ProductStatus status, String category, Pageable pageable) {
        Specification<Product> specification = Specification.where(null);
        if (null != category && category.length() > 0) {
            ProductSpecification filter = new ProductSpecification(new SearchCriteria("category", SearchCriteriaOperator.EQUALS, category));
            specification = specification.and(filter);
        }
        if (null != name && name.length() > 0) {
            ProductSpecification filter = new ProductSpecification(new SearchCriteria("name", SearchCriteriaOperator.LIKE, name));
            specification = specification.and(filter);
        }
        if (null != status) {
            ProductSpecification filter = new ProductSpecification(new SearchCriteria("status", SearchCriteriaOperator.EQUALS, status));
            specification = specification.and(filter);
        }
        if (priceFrom > 0) {
            ProductSpecification filter = new ProductSpecification(new SearchCriteria("price", SearchCriteriaOperator.GREATER_THAN_OR_EQUALS, priceFrom));
            specification = specification.and(filter);
        }
        if (priceTo > 0) {
            ProductSpecification filter = new ProductSpecification(new SearchCriteria("price", SearchCriteriaOperator.LESS_THAN_OR_EQUALS, priceTo));
            specification = specification.and(filter);
        }
        return productRepository.findAll(specification, pageable);
    }
}

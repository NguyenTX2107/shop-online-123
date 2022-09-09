package com.assignment.shoponline.entity.search;

import com.assignment.shoponline.entity.Product;
import com.assignment.shoponline.entity.order.OrderDetail;
import com.assignment.shoponline.entity.search.base.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class OrderSpecification implements Specification<Order> {

    private final SearchCriteria searchCriteria;

    public OrderSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (searchCriteria.getOperator()) {
            case EQUALS:
                return criteriaBuilder.equal(
                        root.get(searchCriteria.getKey()),
                        searchCriteria.getValue());
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case GREATER_THAN_OR_EQUALS:
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case LESS_THAN:
                return criteriaBuilder.lessThan(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case LESS_THAN_OR_EQUALS:
                return criteriaBuilder.lessThanOrEqualTo(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case JOIN:
                Join<Order, OrderDetail> orderDetailProductJoin = root.join("orderDetails");
                Join<OrderDetail, Product> productJoin = orderDetailProductJoin.join("product");
                return criteriaBuilder.or(
                        criteriaBuilder.like(productJoin.get("detail"), "%" + searchCriteria.getValue() + "%"),
                        criteriaBuilder.like(productJoin.get("name"), "%" + searchCriteria.getValue() + "%")
                );
        }
        return null;
    }
}

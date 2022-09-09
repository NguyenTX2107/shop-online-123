package com.assignment.shoponline.utils;

public class Enums <T> {
    /**
     * ADMIN=0
     * USER=1
     */
    public enum Role {
        ADMIN, USER
    }

    /**
     * ACTIVE=0, còn hàng.
     * DEACTIVATE=1, không còn hàng.
     * DELETED=2, đã xóa hàng.
     */
    public enum ProductStatus {
        ACTIVE, DEACTIVATE, DELETED
    }

    /**
     * PENDING=0, đơn đang trong trạng thái chờ.
     * COMPLETED=1, hoàn thành đơn.
     * CANCELED=2, đơn bị hủy.
     */
    public enum OrderStatus {
        PENDING, COMPLETED, CANCELED
    }

}

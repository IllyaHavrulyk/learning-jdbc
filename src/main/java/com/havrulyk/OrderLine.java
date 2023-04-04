package com.havrulyk;

import java.math.BigDecimal;

public class OrderLine {
    private int quantity;
    private String productCode;
    private String productName;
    private int productSize;
    private String productVariety;
    private BigDecimal productPrice;

    public OrderLine() {
    }

    public OrderLine(int quantity, String productCode, String productName, int productSize, String productVariety,
                     BigDecimal productPrice) {
        this.quantity = quantity;
        this.productCode = productCode;
        this.productName = productName;
        this.productSize = productSize;
        this.productVariety = productVariety;
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductSize() {
        return productSize;
    }

    public void setProductSize(int productSize) {
        this.productSize = productSize;
    }

    public String getProductVariety() {
        return productVariety;
    }

    public void setProductVariety(String productVariety) {
        this.productVariety = productVariety;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
}

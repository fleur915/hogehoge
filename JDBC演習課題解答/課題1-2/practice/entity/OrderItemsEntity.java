/*
 * Copyright ©　Intelligence Business Solutions. All rights reserved.
 */
package training.jdbc.practice.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 注文詳細エンティティクラス
 * 
 * @author go.yokoyama
 * @since 2013/05/20
 */
public class OrderItemsEntity {
    /** 注文ID */
    private Integer orderId = null;
    /** 注文枝番号 */
    private Integer lineItemId = null;
    /** 製品ID */
    private Integer productId = null;
    /** 単価 */
    private BigDecimal unitPrice = null;
    /** 個数 */
    private Integer quantity = null;

    /**
     * コンストラクタ。
     * 指定されたパラメータを元に、インスタンスを生成します。
     * 
     * @param orderId 注文ID
     * @param lineItemId 注文枝番号
     * @param productId 製品ID
     * @param unitPrice 単価
     * @param quantity 個数
     */
    public OrderItemsEntity(Integer orderId, Integer lineItemId, Integer productId, BigDecimal unitPrice,
            Integer quantity) {
        super();
        this.orderId = orderId;
        this.lineItemId = lineItemId;
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    /**
     * コンストラクタ。
     * 指定されたパラメータを元に、インスタンスを生成します。
     * 
     * @param orderId 注文ID
     * @param productId 製品ID
     * @param unitPrice 単価
     * @param quantity 個数
     */
    public OrderItemsEntity(Integer orderId, Integer productId, BigDecimal unitPrice,
            Integer quantity) {
        super();
        this.orderId = orderId;
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    /**
     * 注文IDを取得する。
     * 
     * @return 注文ID
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 注文IDを設定する。
     * 
     * @param orderId 注文ID
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 注文枝番号を取得する。
     * 
     * @return 注文枝番号
     */
    public Integer getLineItemId() {
        return lineItemId;
    }

    /**
     * 注文枝番号を設定する。
     * 
     * @param lineItemId 注文枝番号
     */
    public void setLineItemId(Integer lineItemId) {
        this.lineItemId = lineItemId;
    }

    /**
     * 製品IDを取得する。
     * 
     * @return 製品ID
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 製品IDを設定する。
     * 
     * @param productId 製品ID
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 単価を取得する。
     * 
     * @return 単価
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * 単価を設定する。
     * 
     * @param unitPrice 単価
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 個数を取得する。
     * 
     * @return 個数
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 個数を設定する。
     * 
     * @param quantity 個数
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 単価×個数を返却する。
     * 
     * @return 小計金額
     */
    public BigDecimal getSubTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * 合計金額を計算する。
     * 
     * @param orderItemsList 注文詳細のリスト
     * @return 合計金額
     */
    public static BigDecimal sumTotaPrice(List<OrderItemsEntity> orderItemsList) {
        BigDecimal sum = BigDecimal.ZERO;
        for (OrderItemsEntity orderItems : orderItemsList) {
            sum = sum.add(orderItems.getSubTotalPrice());
        }
        return sum;
    }

    /**
     * toStringのラッピングです。
     * 
     * @return このオブジェクトの文字列表現
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("OrderItemsEntity [orderId=%s, lineItemId=%s, productId=%s, unitPrice=%s, quantity=%s]",
                orderId, lineItemId, productId, unitPrice, quantity);
    }

}

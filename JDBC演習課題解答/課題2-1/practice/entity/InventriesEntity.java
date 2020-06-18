/*
 * Copyright ©　Intelligence Business Solutions. All rights reserved.
 */
package training.jdbc.practice.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 倉庫エンティティクラス
 * 
 * @author go.yokoyama
 * @since 2013/05/21
 */
public class InventriesEntity implements Cloneable {
    /** 製品ID */
    private Integer productId = null;
    /** 倉庫ID */
    private Integer warehouseId = null;
    /** 在庫量 */
    private Integer quantityOnHand = null;

    /**
     * コンストラクタ。
     * 指定されたパラメータを元に、インスタンスを生成します。
     * 
     * @param productId 製品ID
     * @param warehouseId 倉庫ID
     * @param quantityOnHand 在庫量
     */
    public InventriesEntity(Integer productId, Integer warehouseId, Integer quantityOnHand) {
        super();
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.quantityOnHand = quantityOnHand;
    }

    /**
     * 製品IDを取得する。
     * 
     * @return productId
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 製品IDを設定する。
     * 
     * @param productId productId
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 倉庫IDを取得する。
     * 
     * @return 倉庫ID
     */
    public Integer getWarehouseId() {
        return warehouseId;
    }

    /**
     * 倉庫IDを設定する。
     * 
     * @param warehouseId 倉庫ID
     */
    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * 在庫量を取得する。
     * 
     * @return 在庫量
     */
    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    /**
     * 在庫量を設定する。
     * 
     * @param quantityOnHand 在庫量
     */
    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    /**
     * 在庫の引き当てを行う。
     * 
     * @param inventriesList 引き当て前の在庫情報
     * @param count 引き当て数
     * @return 引き当て後の在庫情報
     */
    public static List<InventriesEntity> reserveInvation(List<InventriesEntity> inventriesList, int count) {
        // 課題2-1 で実装
        List<InventriesEntity> resultInventriesList = new ArrayList<InventriesEntity>();

        int leftCount = count; // 引き当て残数
        for (InventriesEntity inventries : inventriesList) {
            try {
                InventriesEntity inventriesCopy = (InventriesEntity) inventries.clone();

                if (leftCount == 0) {
                    // 引き当て済みの場合
                    resultInventriesList.add(inventriesCopy);
                    continue;
                }
                int reservableCount = inventriesCopy.getQuantityOnHand() - leftCount;
                if (0 <= reservableCount) {
                    // 現在の倉庫で割り当て可能
                    inventriesCopy.setQuantityOnHand(reservableCount);
                    leftCount = 0;
                } else {
                    // 割り当て不可能
                    inventriesCopy.setQuantityOnHand(0);
                    leftCount = Math.abs(reservableCount);
                }
                // 更新した倉庫情報を設定
                resultInventriesList.add(inventriesCopy);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        if (leftCount != 0) {
            throw new IllegalStateException("在庫の引当に失敗しました");
        }
        return resultInventriesList;
    }

    /**
     * toStringのラッピングです。
     * 
     * @return このオブジェクトの文字列表現
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("InventriesEntity [product_id=%s, warehouseId=%s, quantityOnHand=%s]", productId,
                warehouseId, quantityOnHand);
    }

    /**
     * cloneのラッピングです。
     * 
     * @return このオブジェクトのコピー
     * @throws CloneNotSupportedException 発生することはない
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}

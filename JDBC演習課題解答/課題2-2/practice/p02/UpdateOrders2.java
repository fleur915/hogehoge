/*
 * Copyright ©　Intelligence Business Solutions. All rights reserved.
 */
package training.jdbc.practice.p02;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import training.jdbc.practice.dao.InventriesDao;
import training.jdbc.practice.dao.ItemInfoDao;
import training.jdbc.practice.dao.OrderItemsDao;
import training.jdbc.practice.dao.OrdersDao;
import training.jdbc.practice.entity.InventriesEntity;
import training.jdbc.practice.entity.OrderItemsEntity;
import training.jdbc.practice.entity.OrdersEntity;
import training.jdbc.practice.info.ItemInfo;

/**
 *
 * @author go.yokoyama
 * @since 2013/05/09
 */
public final class UpdateOrders2 {

    /**
     * インスタンスの生成を抑止。
     */
    private UpdateOrders2() {
    }

    /**
     * @param args 引数なし
     */
    public static void main(String[] args) {
        // 課題2-2 で実装
        Connection conn = null;
        try {
            // DBとの接続
            DriverManager.setLoginTimeout(20);
            conn = DriverManager.getConnection("jdbc:postgresql://vm-fy20-postgresql.japaneast.cloudapp.azure.com:5432/[接続先DB名]", "[DB接続ユーザ名]", "[DB接続パスワード]");
            System.out.println("接続成功");

            conn.setAutoCommit(false); // 自動コミットの無効化
            // 引数
            int orderId = 9001;
            // 再注文する製品ID
            int item01ProductId = 1750;
            int item03ProductId = 3209;
            // 再注文する製品の数量
            int item01count = 2;
            int item03count = 50;
            // 再注文する製品の単価
            BigDecimal item01Price = BigDecimal.valueOf(699);
            BigDecimal item03Price = BigDecimal.valueOf(13);

            // 注文詳細の設定
            List<OrderItemsEntity> orderItemsList = new ArrayList<OrderItemsEntity>();
            OrderItemsEntity orderItems01 = new OrderItemsEntity(orderId, item01ProductId,
                    item01Price, item01count);
            OrderItemsEntity orderItems03 = new OrderItemsEntity(orderId, item03ProductId,
                    item03Price, item03count);

            orderItemsList.add(orderItems01);
            orderItemsList.add(orderItems03);

            // 前回注文の取得
            List<OrderItemsEntity> oldOrderItemsList = OrderItemsDao.selectByOrderId(conn, orderId);

            // 在庫のロックして前回の引き当て分を返却する。
            for (OrderItemsEntity orderItems : oldOrderItemsList) {
                List<InventriesEntity> inventriesList = InventriesDao.selectByProductIdForUpdate(conn,
                        orderItems.getProductId());
                int quantityOnHand = inventriesList.get(0).getQuantityOnHand() + orderItems.getQuantity();
                inventriesList.get(0).setQuantityOnHand(quantityOnHand);
                // 在庫を戻す。
                InventriesDao.update(conn, inventriesList);
            }

            // 在庫のチェック
            for (OrderItemsEntity orderItems : orderItemsList) {
                ItemInfo itemInfo = ItemInfoDao.selectById(conn, orderItems.getProductId());

                if (itemInfo.getQuantityOnHand() == null || itemInfo.getQuantityOnHand() < orderItems.getQuantity()) {
                    System.out.println("在庫が足りません：製品ID[" + orderItems.getProductId() + "]在庫数："
                            + itemInfo.getQuantityOnHand() + "希望数：" + orderItems.getQuantity());
                    conn.rollback();
                    return;
                }
            }

            // 在庫引き当て
            InsertOrders2.reserveInventries(conn, orderItemsList);

            // 注文の合計金額の計算
            BigDecimal allListPrice = OrderItemsEntity.sumTotaPrice(orderItemsList);

            // 注文を検索
            OrdersEntity orders = OrdersDao.selectById(conn, orderId);

            // 合計金額の更新
            orders.setOrderTotal(allListPrice);

            // 注文詳細の削除
            OrderItemsDao.deleteByOrderId(conn, orderId);

            // 注文の更新
            OrdersDao.updateOrders(conn, orders);

            // 注文詳細の登録
            OrderItemsDao.insert(conn, orderItemsList);
            // コミット
            conn.commit();

            // CHECKSTYLE:OFF 何かしらの例外が発生した場合にRollbackしたいためExceptionでキャッチする
        } catch (Exception e) {
            // CHECKSTYLE:ON
            // 例外処理
            e.printStackTrace();
            if (conn != null) {
                try {
                    // ロールバック
                    conn.rollback();
                    System.out.println("ロールバック");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    // 接続の終了処理
                    conn.close();
                    System.out.println("接続終了");
                } catch (SQLException e) {
                    // 切断失敗
                    e.printStackTrace();
                }
            }
        }
    }
}

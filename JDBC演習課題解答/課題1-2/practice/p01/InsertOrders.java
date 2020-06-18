/*
 * Copyright ©　Intelligence Business Solutions. All rights reserved.
 */
package training.jdbc.practice.p01;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import training.jdbc.practice.dao.OrderItemsDao;
import training.jdbc.practice.dao.OrdersDao;
import training.jdbc.practice.entity.OrderItemsEntity;
import training.jdbc.practice.entity.OrdersEntity;

/**
 * 製品の注文を行う。
 * 
 * @author go.yokoyama
 * @since 2013/05/09
 */
public final class InsertOrders {

    /**
     * インスタンスの生成を抑止。
     */
    private InsertOrders() {
    }

    /** 注文方法：オンライン */
    private static final String ORDER_MODE_ONLINE = "online";

    /** 注文状況：注文 */
    private static final int ORDER_STATUS_ENTRY = 1;

    /**
     * @param args 引数なし
     */
    public static void main(String[] args) {
        // 課題1-2 で実装
        Connection conn = null;
        try {
            // DBとの接続
            DriverManager.setLoginTimeout(20);
            conn = DriverManager.getConnection("jdbc:postgresql://vm-fy20-postgresql.japaneast.cloudapp.azure.com:5432/[接続先DB名]", "[DB接続ユーザ名]", "[DB接続パスワード]");
            System.out.println("接続成功");

            conn.setAutoCommit(false); // 自動コミットの無効化
            // 入力情報
            int customerId = 101;
            // 注文する製品ID
            int item01ProductId = 1750;
            int item02ProductId = 2266;
            int item03ProductId = 3209;
            // 注文する製品の数量
            int item01count = 2;
            int item02count = 20;
            int item03count = 30;
            // 注文する製品の単価
            BigDecimal item01Price = BigDecimal.valueOf(699);
            BigDecimal item02Price = BigDecimal.valueOf(333);
            BigDecimal item03Price = BigDecimal.valueOf(13);

            int orderId = 9001;

            // 注文詳細の設定
            List<OrderItemsEntity> orderItemsList = new ArrayList<OrderItemsEntity>();
            OrderItemsEntity orderItems01 = new OrderItemsEntity(orderId, item01ProductId,
                    item01Price, item01count);
            OrderItemsEntity orderItems02 = new OrderItemsEntity(orderId, item02ProductId,
                    item02Price, item02count);
            OrderItemsEntity orderItems03 = new OrderItemsEntity(orderId, item03ProductId,
                    item03Price, item03count);
            orderItemsList.add(orderItems01);
            orderItemsList.add(orderItems02);
            orderItemsList.add(orderItems03);

            // 注文の合計金額の計算
            BigDecimal allListPrice = OrderItemsEntity.sumTotaPrice(orderItemsList);

            // 注文作成
            OrdersEntity orders = new OrdersEntity(orderId,
                    new Timestamp(System.currentTimeMillis()),
                    ORDER_MODE_ONLINE,
                    customerId,
                    ORDER_STATUS_ENTRY,
                    allListPrice,
                    null,
                    null);
            // 注文の登録
            OrdersDao.insertOrders(conn, orders);
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

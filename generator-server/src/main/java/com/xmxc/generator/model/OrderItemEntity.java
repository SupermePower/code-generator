package com.xmxc.generator.model;

import lombok.Setter;
import lombok.Getter;
import java.math.BigDecimal;
import java.sql.Timestamp;


@Setter
@Getter
public class OrderItemEntity {
   /**主键*/
   private Long itemId;
   /**订单id*/
   private Long orderId;
   /**商品id*/
   private Long goodsId;
   /**商品类型（例如：中份，微辣）*/
   private String goodsType;
   /**商品名称*/
   private String goodsName;
   /**商品数量*/
   private Integer goodsNum;
   /**商品价格*/
   private BigDecimal goodsPrice;
   /**实际价格*/
   private BigDecimal realPrice;
   /**小计*/
   private BigDecimal subtotal;
   /**订单类型（1预约，0店中，2外卖）*/
   private Byte orderType;
   /**支付时间*/
   private Timestamp payTime;
   /**创建时间*/
   private Timestamp createTime;
   /**更新时间*/
   private Timestamp updateTime;
   /**版本*/
   private Byte version;
   /**数据状态*/
   private Byte dataStatus;
   /**用户主键*/
   private Long userId;
}
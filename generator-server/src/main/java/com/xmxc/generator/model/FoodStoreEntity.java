package com.xmxc.generator.model;

import lombok.Setter;
import lombok.Getter;
import java.math.BigDecimal;


@Setter
@Getter
public class FoodStoreEntity {
   /**店铺id*/
   private String cId;
   /**用户ID*/
   private Integer nUserId;
   /**店铺名称*/
   private String cStoreName;
   /**店铺头像*/
   private String cAvatar;
   /**店铺banner*/
   private Object cBannerString;
   /**描述*/
   private String cDescribe;
   /***/
   private String cProvince;
   /**城市*/
   private String cCity;
   /**区域*/
   private String cDistrict;
   /**经度*/
   private String cLongitude;
   /**纬度*/
   private String cLatitude;
   /**地址*/
   private String cAddress;
   /**门牌号*/
   private String cHouseUmber;
   /**营业开始时间*/
   private String cStartTime;
   /**营业结束时间*/
   private String cEndTime;
   /**联系人*/
   private String cContact;
   /**起送价*/
   private BigDecimal nStartPrice;
   /**配送价格*/
   private BigDecimal nDeliveryPrice;
   /***/
   private BigDecimal nApiecePrice;
   /**联系电话*/
   private Long nPhoneNumber;
   /**是否自动接单 0 不是 1 是*/
   private Integer nAutoOrder;
   /***/
   private String cGeohash;
   /**是否带堂食  0 否 1 是*/
   private Integer nDine;
   /**是否外卖 0 否 1 是*/
   private Integer nTakeaway;
   /**是否支持自提 0 否 1 是*/
   private Integer nPickup;
   /**配送距离 公里*/
   private BigDecimal dDeliveryDistancee;
   /**达达配送商户id*/
   private String cDadaSource;
   /**状态  0 开业 1 未开业*/
   private Integer nStatus;
   /**是否删除 0 否 1 是*/
   private Integer nDelete;
   /**创建时间*/
   private Long nCreateTime;
   /**更新时间*/
   private Long nUpdateTime;
}
use zhmv;
-- ----------------------------
-- Alter Table structure for order
-- ----------------------------
ALTER TABLE zhmv.`order`
ADD COLUMN `pay_id`  varchar(64) NULL DEFAULT NULL COMMENT '微信统一下单的预支付ID' AFTER `remark`;
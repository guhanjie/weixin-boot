use zhmv;
-- ----------------------------
-- Alter Table structure for order
-- ----------------------------
ALTER TABLE `order`
ADD COLUMN `source`  varchar(100) NULL COMMENT '推广源' AFTER `remark`;
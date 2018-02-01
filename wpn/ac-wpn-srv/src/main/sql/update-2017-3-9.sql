-- -----------------------------------------------------
-- Table `ac_wpn_db`.`WeixinQRCode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ac_wpn_db`.`WeixinQRCode` ;

CREATE TABLE IF NOT EXISTS `ac_wpn_db`.`WeixinQRCode` (
  `weixinQRCodeId` BIGINT NOT NULL AUTO_INCREMENT COMMENT '二维码编号',
  `sceneId` VARCHAR(64) NOT NULL COMMENT '场景编号',
  `qrCodeType` VARCHAR(8) NOT NULL COMMENT '二维码类型',
  `paramJson` VARCHAR(1024) NULL COMMENT '二维码参数',
  `ticket` VARCHAR(256) NOT NULL COMMENT '二维码票据',
  `qrCodeShortUrl` VARCHAR(32) NOT NULL COMMENT '短连接',
  `qrCodeBizType` CHAR(2) NOT NULL COMMENT '业务类型',
  `appId` VARCHAR(128) NOT NULL COMMENT '公众号',
  `qrCodeContent` VARCHAR(256) NOT NULL,
  `traceNo` VARCHAR(64) NULL,
  `expireSeconds` BIGINT NULL,
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `updateTime` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`weixinQRCodeId`),
  INDEX `sceneId_index` (`sceneId` ASC),
  INDEX `traceNo_qrCodeBizType_index` (`qrCodeBizType` ASC, `traceNo` ASC))
ENGINE = InnoDB
COMMENT = '微信二维码';
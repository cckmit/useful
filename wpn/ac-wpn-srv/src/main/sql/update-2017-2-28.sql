
-- -----------------------------------------------------
-- Table `ac_wpn_db`.`WeixinQRCode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ac_wpn_db`.`WeixinQRCode` ;

CREATE TABLE IF NOT EXISTS `ac_wpn_db`.`WeixinQRCode` (
  `qrCodeParamId` BIGINT NOT NULL AUTO_INCREMENT COMMENT '二维码编号',
  `sceneId` VARCHAR(64) NOT NULL COMMENT '场景编号',
  `qrCodeType` VARCHAR(8) NOT NULL COMMENT '二维码类型',
  `paramJson` VARCHAR(1024) NULL COMMENT '二维码参数',
  `ticket` VARCHAR(256) NOT NULL COMMENT '二维码票据',
  `qrCodeShortUrl` VARCHAR(32) NOT NULL COMMENT '短连接',
  `qrCodeBizType` CHAR(2) NOT NULL COMMENT '业务类型',
  `appId` VARCHAR(128) NOT NULL COMMENT '公众号',
  `expireSeconds` BIGINT NULL,
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `updateTime` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`qrCodeParamId`),
  INDEX `sceneId_index` (`sceneId` ASC))
ENGINE = InnoDB
COMMENT = '微信二维码';


-- -----------------------------------------------------
-- Table `ac_wpn_db`.`WeixinNotifyInfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ac_wpn_db`.`WeixinNotifyInfo` ;

CREATE TABLE IF NOT EXISTS `ac_wpn_db`.`WeixinNotifyInfo` (
  `weixinNotifyInfoId` BIGINT NOT NULL AUTO_INCREMENT,
  `appId` VARCHAR(128) NOT NULL COMMENT '公众号',
  `messageHashCode` VARCHAR(128) NOT NULL COMMENT '消息散列值',
  `notifyContent` TEXT NULL,
  `sign` VARCHAR(128) NOT NULL COMMENT '消息签名信息',
  `dealStatus` CHAR(1) NOT NULL COMMENT '处理状态',
  `dealResultDesc` VARCHAR(512) NULL COMMENT '处理描述信息',
  `receiveTime` DATETIME NOT NULL COMMENT '接收时间',
  `updateTime` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`weixinNotifyInfoId`))
ENGINE = InnoDB
COMMENT = '微信通知消息信息';


-- -----------------------------------------------------
-- Table `ac_wpn_db`.`WxBindCardHolder`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ac_wpn_db`.`WxBindCardHolder` ;

CREATE TABLE IF NOT EXISTS `ac_wpn_db`.`WxBindCardHolder` (
  `wxBindCardHolderId` BIGINT NOT NULL AUTO_INCREMENT COMMENT '微信绑定信息编号',
  `cardNOToken` VARCHAR(64) NOT NULL COMMENT '卡号令牌编号',
  `userId` BIGINT NOT NULL COMMENT '用户编号',
  `bindStatus` CHAR(1) NOT NULL COMMENT '绑定状态',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `updateTime` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`wxBindCardHolderId`))
ENGINE = InnoDB
COMMENT = '持卡人绑定信息';


-- 微信公众号增加别名
ALTER TABLE `ac_wpn_db`.`WxPublicNumber`
ADD COLUMN `appAlais` VARCHAR(128) NULL COMMENT 'app别名' AFTER `appId`,

-- 去掉密码唯一性限制
ALTER TABLE `ac_wpn_db`.`WxPublicNumber`
DROP INDEX `secret_UNIQUE` ;


-- -----------------------------------------------------
-- Table `ac_wpn_db`.`WxMessageTemplate` 微信消息模板
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ac_wpn_db`.`WxMessageTemplate` ;

CREATE TABLE IF NOT EXISTS `ac_wpn_db`.`WxMessageTemplate` (
  `wxMessageTemplateId` BIGINT NOT NULL AUTO_INCREMENT,
  `templateName` VARCHAR(128) NOT NULL COMMENT '模板消息编号',
  `appAlais` VARCHAR(128) NOT NULL COMMENT '微信公众号编号',
  `templateDesc` TEXT NOT NULL COMMENT '模板描述',
  `templateGroup` CHAR(2) NOT NULL COMMENT '模板消息组',
  `weixinTemplateId` VARCHAR(128) NOT NULL COMMENT '微信模板编号',
  `paramConfigs` VARCHAR(512) NOT NULL,
  `url` VARCHAR(256) NULL COMMENT '链接地址',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `updateTime` VARCHAR(45) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`wxMessageTemplateId`),
  INDEX `templateName_applais_index` (`templateName` ASC, `appAlais` ASC))
ENGINE = InnoDB;


--- 数据更新
--
-- UPDATE `ac_wpn_db`.`WxPublicNumber` SET `appAlais`='andpay_partner', `token`='QASKJIUD99KOSJJSAAS99SAS' WHERE `wxPublicNumberId`='1';
-- UPDATE `ac_wpn_db`.`WxPublicNumber` SET `appAlais`='andpay_service_platform', `token`='SSANLLOPDFIURKKSSFSFKKS' WHERE `wxPublicNumberId`='2';
--
--



--- 测试环境
--- 插入消息模板
-- INSERT INTO `ac_wpn_db`.`WxMessageTemplate` (`templateName`, `appAlais`, `templateDesc`, `templateGroup`, `weixinTemplateId`, `paramConfigs`, `createTime`, `updateTime`) VALUES ('ac-caws.account-change-everyday-notify-templdate', 'andpay_partner', '账户资金变动提醒(每日)', '01', 'MSO7iEGJqPUB4xgWUpPJY9FiVr09-dpUFQuKniCOd0c', '{\"first\":{\"format\":\"尊敬的{}，您今日累计获得分润\"},\"keyword1\":{},\"keyword2\":{\"color\": \"#0092D9\",\"format\": \"人民币 {}\"},\"keyword3\":{\"color\": \"#0092D9\",\"format\": \"人民币 {}\"}}', '2017-02-09 17:35:32', '2017-02-09 17:35:32');
-- INSERT INTO `ac_wpn_db`.`WxMessageTemplate` (`templateName`, `appAlais`, `templateDesc`, `templateGroup`, `weixinTemplateId`, `paramConfigs`, `createTime`, `updateTime`) VALUES ('ac-caws.account-change-realtime-notify-templdate', 'andpay_partner', '账户资金变动提醒（实时)', '01', 'MSO7iEGJqPUB4xgWUpPJY9FiVr09-dpUFQuKniCOd0c', '{\"first\":{\"format\":\"尊敬的{}，您有一笔分润入账\"},\"keyword1\":{},\"keyword2\":{\"color\": \"#0092D9\",\"format\": \"人民币 {}\"},\"keyword3\":{\"color\": \"#0092D9\",\"format\": \"人民币 {}\"}}', '2017-02-09 17:35:32', '2017-02-09 17:35:32');
-- INSERT INTO `ac_wpn_db`.`WxMessageTemplate` (`templateName`, `appAlais`, `templateDesc`, `templateGroup`, `weixinTemplateId`, `paramConfigs`, `createTime`, `updateTime`) VALUES ('ac-caws.withdraw-succ-notify-template', 'andpay_partner', '提现到账通知', '01', '2emlz7ByOS23SC4n-aSzqUdl4fh7TSoh5RTtJYqfC5k', '{\"first\": {\"format\":\"尊敬的{}，您的提现已到账\"},     \"keyword1\": {},     \"keyword2\": {},     \"keyword3\": {         \"color\": \"#0092D9\",         \"format\": \"人民币 {}\"     },     \"keyword4\": {         \"color\": \"#0092D9\",         \"format\": \"人民币 {}\"     },     \"keyword5\": {         \"color\": \"#0092D9\",         \"format\": \"人民币 {}\"     },     \"remark\": {} }', '2017-02-09 17:35:32', '2017-02-09 17:35:32');
-- INSERT INTO `ac_wpn_db`.`WxMessageTemplate` (`templateName`, `appAlais`, `templateDesc`, `templateGroup`, `weixinTemplateId`, `paramConfigs`, `createTime`, `updateTime`) VALUES ('ac-oss.deliver-goods-notify-templdate', 'andpay_partner', '订单发货通知', '01', 'CgRKbueMmwPdaJ7bKaPztIlGrMTqkJl9iiMHyh13YQ8', '{\"first\":{\"format\":\"尊敬的{}，您的订单已发货\"},\"keyword1\":{},\"keyword2\":{},\"keyword3\":{}, \"keyword4\":{},\"remark\":{}}', '2017-02-09 17:35:32', '2017-02-09 17:35:32');
-- INSERT INTO `ac_wpn_db`.`WxMessageTemplate` (`templateName`, `appAlais`, `templateDesc`, `templateGroup`, `weixinTemplateId`, `paramConfigs`, `createTime`, `updateTime`) VALUES ('ac-txn.entry-account-t1-notify-templdate', 'andpay_service_platform', 'T0交易入账通知', '01', 'GlfNiEsZ-Wdv3_6skFc06FpSRIW9Vh7lKuRwDfTkrKs', '{\"first\":{\"format\":\"尊敬的{}，您的T0交易已入账\"},\"keyword1\":{\"color\": \"#0092D9\",\"format\": \"人民币 {}\"},\"keyword2\":{},\"keyword3\":{\"color\": \"#00', '2017-02-09 17:35:32', '2017-02-09 17:35:32');
-- INSERT INTO `ac_wpn_db`.`WxMessageTemplate` (`templateName`, `appAlais`, `templateDesc`, `templateGroup`, `weixinTemplateId`, `paramConfigs`, `createTime`, `updateTime`) VALUES ('ac-txn.entry-account-t0-notify-templdate', 'andpay_service_platform', 'T1交易入账通知', '01', 'GlfNiEsZ-Wdv3_6skFc06FpSRIW9Vh7lKuRwDfTkrKs', '{\"first\":{\"format\":\"尊敬的{}，您上一工作日的刷卡交易已入账，实际到账请以银行为准\"},\"keyword1\":{\"color\": \"#0092D9\",\"format\": \"人民币 {}\"},\"keyword2\":{},\"keyword3', '2017-02-09 17:35:32', '2017-02-09 17:35:32');
-- INSERT INTO `ac_wpn_db`.`WxMessageTemplate` (`templateName`, `appAlais`, `templateDesc`, `templateGroup`, `weixinTemplateId`, `paramConfigs`, `createTime`, `updateTime`) VALUES ('ac-oss.deliver-goods-notify-templdate', 'andpay_service_platform', '订单发货通知', '01', 'CgRKbueMmwPdaJ7bKaPztIlGrMTqkJl9iiMHyh13YQ8', '{\"first\":{\"format\":\"尊敬的{}，您的订单已发货\"},\"keyword1\":{},\"keyword2\":{},\"keyword3\":{}, \"keyword4\":{},\"remark\":{}}', '2017-02-09 17:35:32', '2017-02-09 17:35:32');
--
--



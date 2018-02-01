-- -----------------------------------------------------
-- Table `ac_wpn_db`.`WxTemplateMessage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ac_wpn_db`.`WxTemplateMessage` ;

CREATE TABLE IF NOT EXISTS `ac_wpn_db`.`WxTemplateMessage` (
  `templateName` VARCHAR(128) NOT NULL COMMENT '模板消息编号',
  `templateDesc` TEXT NOT NULL COMMENT '模板描述',
  `templateGroup` CHAR(2) NOT NULL COMMENT '模板消息组',
  `weixinTemplateId` VARCHAR(128) NOT NULL COMMENT '微信模板编号',
  `paramConfigs` VARCHAR(512) NOT NULL,
  `appId` VARCHAR(128) NOT NULL COMMENT '微信公众号编号',
  `url` VARCHAR(256) NULL COMMENT '链接地址',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  `updateTime` VARCHAR(45) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`templateName`))
ENGINE = InnoDB;

-- 添加模板消息
INSERT INTO `ac_wpn_db`.`WxTemplateMessage` (`templateName`, `templateDesc`, `templateGroup`, `weixinTemplateId`, `paramConfigs`, `appId`, `url`, `createTime`, `updateTime`) VALUES ('ac-caws.withdraw-succ-notify-template', '提现到账通知', '01', 'KbvCXXhH8kgtnCewcpaFD0F2lwhRvrFNhbFafv6yrqM', '{\"first\": {\"format\":\"尊敬的{}，您的提现已到账\"},     \"keyword1\": {},     \"keyword2\": {},     \"keyword3\": {         \"color\": \"#0092D9\",         \"format\": \"人民币 {}\"     },     \"keyword4\": {         \"color\": \"#0092D9\",         \"format\": \"人民币 {}\"     },     \"keyword5\": {         \"color\": \"#0092D9\",         \"format\": \"人民币 {}\"     },     \"remark\": {} }', 'wx2139bbc83e2e73e2', '', '2017-02-09 17:35:32', '2017-02-09 17:35:32');
INSERT INTO `ac_wpn_db`.`WxTemplateMessage` (`templateName`, `templateDesc`, `templateGroup`, `weixinTemplateId`, `paramConfigs`, `appId`, `url`, `createTime`, `updateTime`) VALUES ('fa_huo_tong_zhi', '订单发货通知', '01', 'efJEqLXdCuzyCmOPemN8KGvsl44bbQSGSL8hhytQ9Js', '{\"first\":{\"format\":\"尊敬的{}，您的订单已发货\"},\"keyword1\":{},\"keyword2\":{},\"keyword3\":{}, \"keyword4\":{},\"remark\":{}}', 'wx2139bbc83e2e73e2', '', '2017-02-09 17:35:32', '2017-02-09 17:35:32');


-- 公众号增加字段
ALTER TABLE `ac_wpn_db`.`WxPublicNumber`
ADD COLUMN `primaryndustry` VARCHAR(8) NULL COMMENT '公众号模板消息所属行业编号 主营' AFTER `token`,
ADD COLUMN `secondaryndustry` VARCHAR(8) NULL COMMENT '公众号模板消息所属行业编号 副营' AFTER `primaryndustry`;

-- 模板消息服务配置
INSERT INTO `ti_mns_db`.`ServiceProviderConfig` (`providerType`, `providerName`, `messageType`, `weight`, `description`, `available`, `attr1`, `createTime`, `updateTime`) VALUES ('wx_tp_msg', '微信模板消息', '5', '100', '微信模板消息', '1', 'https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN\nhttps://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN\nhttps://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKENhttps://api.weixin.qq.com/cgi-bin/message/template/send?access_token=', now(), now());
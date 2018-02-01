-- 删除表数据备份
select * from `ac_wpn_db`.`WxTemplateMessage` where templateName in ('ac-caws.withdraw-succ-notify-template','fa_huo_tong_zhi');
-- 删除废弃的表
DROP TABLE `ac_wpn_db`.`WxTemplateMessage`;

-- 更改索引为唯一索引
ALTER TABLE `ac_wpn_db`.`WxMessageTemplate`
DROP COLUMN `templateGroup`,
DROP INDEX `templateName_applais_index` ,
ADD UNIQUE INDEX `idx_wxTp_templateName_appAlais` (`templateName` ASC, `appAlais` ASC),
ADD COLUMN `messageTemplateGroupId` BIGINT NOT NULL DEFAULT 0 COMMENT '分组编号' AFTER `templateDesc`;

-- -----------------------------------------------------
-- Table `ac_wpn_db`.`MessageSwitchConfig`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ac_wpn_db`.`MessageSwitchConfig` (
  `messageSwitchConfigId` BIGINT NOT NULL AUTO_INCREMENT COMMENT '开关编号',
  `open` TINYINT(1) NOT NULL COMMENT '是否打开',
  `target` VARCHAR(32) NOT NULL COMMENT '开关目标对象',
  `targetType` CHAR(1) NOT NULL COMMENT '开关目标对象类型',
  `messageTemplateGroupId` BIGINT NOT NULL COMMENT '消息组编号',
  `createTime` DATETIME NOT NULL,
  PRIMARY KEY (`messageSwitchConfigId`),
  INDEX `idx_MessageSwitchConfig_messageTemplateGroupId` (`messageTemplateGroupId` ASC)  COMMENT '消息组索引')
ENGINE = InnoDB
COMMENT = '消息开关配置';


-- -----------------------------------------------------
-- Table `ac_wpn_db`.`MessageTemplateGroup`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `ac_wpn_db`.`MessageTemplateGroup` (
  `messageTemplateGroupId` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息组编号',
  `groupName` VARCHAR(256) NOT NULL COMMENT '消息组名称',
  `appId` VARCHAR(16) NOT NULL COMMENT '所属应用',
  `priority` INT NOT NULL COMMENT '优先级',
  `createTime` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`messageTemplateGroupId`))
ENGINE = InnoDB
COMMENT = '消息组';

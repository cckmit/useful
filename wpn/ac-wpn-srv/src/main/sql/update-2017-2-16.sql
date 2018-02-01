-- -----------------------------------------------------
-- Table `ac_ecs_db`.`Sequence`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ac_wpn_db`.`Sequence` ;

CREATE  TABLE IF NOT EXISTS `ac_wpn_db`.`Sequence` (
  `sequenceType` VARCHAR(128) NOT NULL ,
  `startValue` BIGINT(20) NOT NULL ,
  `value` BIGINT(20) NOT NULL ,
  `crtTime` DATETIME NOT NULL ,
  PRIMARY KEY (`sequenceType`) )
ENGINE = InnoDB;


UPDATE `ac_wpn_db`.`WxTemplateMessage` SET `url`='https://ts.andpay.me/wpn/wxauth/1?redirect=https%3a%2f%2fts.andpay.me%2fptn%2findex%23%2ftab%2fprofit%2fprofit-detail%2faccount&scope=snsapi_userinfo' WHERE `templateName`='ac-caws.withdraw-succ-notify-template';
UPDATE `ac_wpn_db`.`WxTemplateMessage` SET `url`='https://ts.andpay.me/wpn/wxauth/1?redirect=https%3a%2f%2fts.andpay.me%2fecs%2findex%3fbindType%3dPT%23%2forder-list&scope=snsapi_base' WHERE `templateName`='fa_huo_tong_zhi';


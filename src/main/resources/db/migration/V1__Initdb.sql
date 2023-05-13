
-- ----------------------------
-- Table structure for m_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `m_user`  (
  `user_id` VARCHAR(36) PRIMARY KEY,
  `user_name` VARCHAR(255) NOT NULL,
  `user_email` VARCHAR(255) NOT NULL,
  `user_password` VARCHAR(100) NOT NULL,
  `create_date` TIMESTAMP NULL,
  `create_user` VARCHAR(36) NULL,
  `update_date` TIMESTAMP NULL,
  `update_user` VARCHAR(36) NULL
) ;

INSERT INTO m_user (`user_id`, `user_name`, `user_email`, `user_password`, `create_date`, `create_user`)
VALUES
('1', 'admin', 'admin@aquariux.com', 'password', CURRENT_TIMESTAMP, '1')
;

-- ----------------------------
-- Table structure for m_wallet
-- ----------------------------
CREATE TABLE IF NOT EXISTS `m_wallet`  (
  `wallet_id` VARCHAR(36) PRIMARY KEY,
  `user_id` VARCHAR(36) NOT NULL,
  `usdt` DECIMAL(100,20) NULL,
  `ethusdt` DECIMAL(100,20) NULL,
  `btcusdt` DECIMAL(100,20) NULL,
  `create_date` TIMESTAMP NULL,
  `create_user` VARCHAR(36) NULL,
  `update_date` TIMESTAMP NULL,
  `update_user` VARCHAR(36) NULL,
  CONSTRAINT `m_wallet_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `m_user` (`user_id`) ON DELETE RESTRICT
) ;

INSERT INTO m_wallet (`wallet_id`, `user_id`, `usdt`, `ethusdt`, `btcusdt`, `create_date`, `create_user`)
VALUES
('1', '1', '50000', '0', '0', CURRENT_TIMESTAMP, '1')
;

-- ----------------------------
-- Table structure for t_price_aggregation
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_price_aggregation`  (
  `aggregation_id` VARCHAR(36) PRIMARY KEY,
  `source_price` VARCHAR(36) NOT NULL,
  `trading_pair` VARCHAR(36) NOT NULL,
  `bid_price` DECIMAL(100,20) NULL,
  `ask_price` DECIMAL(100,20) NULL,
  `create_date` TIMESTAMP NULL,
  `create_user` VARCHAR(36) NULL,
  `update_date` TIMESTAMP NULL,
  `update_user` VARCHAR(36) NULL
) ;
CREATE INDEX t_price_agg_trading_pair_ibfk_1 ON t_price_aggregation(trading_pair);

-- ----------------------------
-- Table structure for t_trading_history
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_trading_history`  (
  `trading_id` VARCHAR(36) PRIMARY KEY,
  `trading_type` TINYINT,
  `trading_pair` VARCHAR(36) NOT NULL,
  `price` DECIMAL(100,20) NOT NULL,
  `amount` DECIMAL(100,20) NOT NULL,
  `total` DECIMAL(100,20) NOT NULL,
  `oldusdt` DECIMAL(100,20) NULL,
  `usdt` DECIMAL(100,20) NULL,
  `create_date` TIMESTAMP NULL,
  `create_user` VARCHAR(36) NULL,
  `update_date` TIMESTAMP NULL,
  `update_user` VARCHAR(36) NULL
) ;
CREATE INDEX t_trading_pair_ibfk_1 ON t_trading_history(trading_pair);
CREATE INDEX t_trading_type_ibfk_2 ON t_trading_history(trading_type);

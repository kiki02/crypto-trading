
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
('cc99c2cc-4649-468e-980b-6339463c00ff', 'admin', 'admin@aquariux.com', 'password', CURRENT_TIMESTAMP, '1')
;

-- ----------------------------
-- Table structure for m_wallet
-- ----------------------------
CREATE TABLE IF NOT EXISTS `m_wallet`  (
  `wallet_id` VARCHAR(36) PRIMARY KEY,
  `user_id` VARCHAR(36) NOT NULL,
  `usdt` DECIMAL(100,20) NULL,
  `eth` DECIMAL(100,20) NULL,
  `btc` DECIMAL(100,20) NULL,
  `create_date` TIMESTAMP NULL,
  `create_user` VARCHAR(36) NULL,
  `update_date` TIMESTAMP NULL,
  `update_user` VARCHAR(36) NULL,
  CONSTRAINT `m_wallet_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `m_user` (`user_id`) ON DELETE RESTRICT
) ;

INSERT INTO m_wallet (`wallet_id`, `user_id`, `usdt`, `eth`, `btc`, `create_date`, `create_user`)
VALUES
('cc99c2cc-4649-468e-980b-6d48e6c433b4', 'cc99c2cc-4649-468e-980b-6339463c00ff', '50000', '0', '0', CURRENT_TIMESTAMP, 'cc99c2cc-4649-468e-980b-6339463c00ff')
;

-- ----------------------------
-- Table structure for t_price_aggregation
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_price_aggregation`  (
  `aggregation_id` VARCHAR(36) PRIMARY KEY,
  `trading_pair` VARCHAR(36) NOT NULL,
  `bid_source` VARCHAR(255) NOT NULL,
  `bid_price` DECIMAL(100,20) NULL,
  `ask_source` VARCHAR(255) NOT NULL,
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
  `trading_status` TINYINT,
  `request_id` VARCHAR(36) NOT NULL,
  `request_time` TIMESTAMP NOT NULL,
  `result_code` TINYINT,
  `result_message` VARCHAR(255) NULL,
  `trading_type` TINYINT,
  `trading_pair` VARCHAR(36) NOT NULL,
  `trading_source` VARCHAR(255) NOT NULL,
  `price` DECIMAL(100,20) NOT NULL,
  `amount` DECIMAL(100,20) NOT NULL,
  `total` DECIMAL(100,20) NOT NULL,
  `usdt` DECIMAL(100,20) NULL,
  `eth` DECIMAL(100,20) NULL,
  `btc` DECIMAL(100,20) NULL,
  `create_date` TIMESTAMP NULL,
  `create_user` VARCHAR(36) NULL,
  `update_date` TIMESTAMP NULL,
  `update_user` VARCHAR(36) NULL
) ;
CREATE INDEX t_trading_pair_ibfk_1 ON t_trading_history(trading_pair);
CREATE INDEX t_trading_type_ibfk_2 ON t_trading_history(trading_type);

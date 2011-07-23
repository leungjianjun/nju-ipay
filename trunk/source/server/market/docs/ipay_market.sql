-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2011 年 07 月 23 日 05:58
-- 服务器版本: 5.1.36
-- PHP 版本: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- 数据库: `ipay_market`
--

-- --------------------------------------------------------

--
-- 表的结构 `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3C3132F4663748` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `admin`
--


-- --------------------------------------------------------

--
-- 表的结构 `attribute`
--

CREATE TABLE IF NOT EXISTS `attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `key_string` varchar(255) DEFAULT NULL,
  `value_string` varchar(255) DEFAULT NULL,
  `atttributes_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7839CA7C9F865B70` (`atttributes_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `attribute`
--


-- --------------------------------------------------------

--
-- 表的结构 `authority`
--

CREATE TABLE IF NOT EXISTS `authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `authority`
--


-- --------------------------------------------------------

--
-- 表的结构 `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `cardnum` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `clientInfo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7877DFEB4663748` (`id`),
  KEY `FK7877DFEB52E5441C` (`clientInfo_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `client`
--


-- --------------------------------------------------------

--
-- 表的结构 `clientinfo`
--

CREATE TABLE IF NOT EXISTS `clientinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `phonenum` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `clientinfo`
--


-- --------------------------------------------------------

--
-- 表的结构 `client_order`
--

CREATE TABLE IF NOT EXISTS `client_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `cost_double` double DEFAULT NULL,
  `quantity_int` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `record_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBECF99DA4BD725B8` (`product_id`),
  KEY `FKBECF99DAB8D7F1FC` (`record_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `client_order`
--


-- --------------------------------------------------------

--
-- 表的结构 `market`
--

CREATE TABLE IF NOT EXISTS `market` (
  `cardnum` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `marketInfo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK88F1805CF9E727C` (`marketInfo_id`),
  KEY `FK88F1805C4663748` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `market`
--


-- --------------------------------------------------------

--
-- 表的结构 `marketinfo`
--

CREATE TABLE IF NOT EXISTS `marketinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `marketinfo`
--


-- --------------------------------------------------------

--
-- 表的结构 `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `market_id` int(11) DEFAULT NULL,
  `productInfo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK50C664CF3D3D661C` (`market_id`),
  KEY `FK50C664CFB5D70418` (`productInfo_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `product`
--


-- --------------------------------------------------------

--
-- 表的结构 `productinfo`
--

CREATE TABLE IF NOT EXISTS `productinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `barcode` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `productinfo`
--


-- --------------------------------------------------------

--
-- 表的结构 `record`
--

CREATE TABLE IF NOT EXISTS `record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `total` double NOT NULL,
  `client_id` int(11) DEFAULT NULL,
  `market_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK91AB58713D3D661C` (`market_id`),
  KEY `FK91AB5871F2EE07BC` (`client_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `record`
--


-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `user`
--


-- --------------------------------------------------------

--
-- 表的结构 `user_authority`
--

CREATE TABLE IF NOT EXISTS `user_authority` (
  `User_id` int(11) NOT NULL,
  `authorityList_id` int(11) NOT NULL,
  KEY `FKF114D7CFFBA1183C` (`User_id`),
  KEY `FKF114D7CFFD63E1DA` (`authorityList_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user_authority`
--


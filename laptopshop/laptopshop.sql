-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: laptopshop
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `advertisements`
--

DROP TABLE IF EXISTS `advertisements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `advertisements` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `link` varchar(500) COLLATE utf8mb3_unicode_ci NOT NULL,
  `thumbnail` varchar(500) COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advertisements`
--

LOCK TABLES `advertisements` WRITE;
/*!40000 ALTER TABLE `advertisements` DISABLE KEYS */;
/*!40000 ALTER TABLE `advertisements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_oce3937d2f4mpfqrycbr0l93m` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'asus'),(2,'dell'),(3,'hp'),(4,'logitech');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t8o6pivur7nn124jehx7cygw5` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'laptop'),(2,'keyboard'),(3,'mouse'),(4,'usb');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkouts`
--

DROP TABLE IF EXISTS `checkouts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkouts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` int DEFAULT NULL,
  `bank_code` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `bank_tran_no` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `card_type` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `order_info` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `pay_date` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `response_code` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `secure_hash` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `tmn_code` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `transaction_no` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `transaction_status` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `txn_ref` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkvnlopomueso0jt8al7st96l` (`order_id`),
  KEY `FK4cffsff8atevplv45x3yagq34` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkouts`
--

LOCK TABLES `checkouts` WRITE;
/*!40000 ALTER TABLE `checkouts` DISABLE KEYS */;
INSERT INTO `checkouts` VALUES (1,10000000,'NCB','VNP14035586','ATM','Thanh toan don hang:26120230611125048','20230611125141','00','6c52229c94640bfd7010796150e61ec57a787ac10201051f07a57ce12921bdba8132c20f0c88924d64eb1de64480a8b4bdefdb1b3c53b29166e404d4b55185ab','V5T6QY1R','14035586','00','26120230611125048',26,1),(2,10000000,'NCB','VNP14035586','ATM','Thanh toan don hang:26120230611125048','20230611125141','00','6c52229c94640bfd7010796150e61ec57a787ac10201051f07a57ce12921bdba8132c20f0c88924d64eb1de64480a8b4bdefdb1b3c53b29166e404d4b55185ab','V5T6QY1R','14035586','00','26120230611125048',26,1);
/*!40000 ALTER TABLE `checkouts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `message` varchar(500) COLLATE utf8mb3_unicode_ci NOT NULL,
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6uv0qku8gsu6x1r2jkrtqwjtn` (`product_id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedbacks`
--

DROP TABLE IF EXISTS `feedbacks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedbacks` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `email` varchar(150) COLLATE utf8mb3_unicode_ci NOT NULL,
  `message` tinytext COLLATE utf8mb3_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedbacks`
--

LOCK TABLES `feedbacks` WRITE;
/*!40000 ALTER TABLE `feedbacks` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedbacks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `galleries`
--

DROP TABLE IF EXISTS `galleries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `galleries` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `thumbnail` varchar(500) COLLATE utf8mb3_unicode_ci NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn4fllmf69rio4pq2q5k4xfc9l` (`product_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `galleries`
--

LOCK TABLES `galleries` WRITE;
/*!40000 ALTER TABLE `galleries` DISABLE KEYS */;
/*!40000 ALTER TABLE `galleries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8mb3_unicode_ci,
  `created_at` datetime DEFAULT NULL,
  `short_description` varchar(500) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `thumbnail` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6itmfjj4ma8lfpj10jx24mhvx` (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'<h1 style=\"text-align:center\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>Đ&Aacute;NH GI&Aacute; LAPTOP ASUS GAMING ROG STRIX G533ZW-LN134W</strong></span></h1>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p style=\"text-align:center\"><strong><img alt=\"\" src=\"https://hanoicomputercdn.com/media/lib/09-06-2022/rog-strix-g533-hacom-0.jpeg\" style=\"height:600px; width:800px\" /></strong></p>\r\n\r\n<p style=\"text-align:center\">&nbsp;</p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Lưu &yacute;:&nbsp;B&agrave;i viết v&agrave; h&igrave;nh ảnh chỉ c&oacute; t&iacute;nh chất tham khảo v&igrave; cấu h&igrave;nh v&agrave; đặc t&iacute;nh sản phẩm c&oacute; thể thay đổi theo thị trường v&agrave; từng phi&ecirc;n bản. Qu&yacute; kh&aacute;ch cần cấu h&igrave;nh cụ thể vui l&ograve;ng xem bảng th&ocirc;ng số kĩ thuật hoặc hỏi kinh doanh trước khi mua.&nbsp;</span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">ROG Strix series l&agrave; laptop gaming esports với m&agrave;n h&igrave;nh nhanh nhất thế giới c&oacute; thẻ l&ecirc;n đến 300Hz. Ngo&agrave;i ra, ASUS cũng cung cấp t&ugrave;y chọn Full HD 240Hz cho một số phi&ecirc;n bản. Năm nay, Strix SCAR được trang bị khả năng l&agrave;m m&aacute;t đột ph&aacute; với keo tản nhiệt kim loại lỏng tr&ecirc;n CPU.</span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Năm nay, Strix SCAR c&oacute; th&acirc;n m&aacute;y nhỏ gọn hơn tới 7% v&agrave; được trang bị chiếu nghỉ tay soft-touch dễ chịu c&ugrave;ng với vi&ecirc;n pin 90Wh v&agrave; sạc type-C cung cấp đủ năng lượng cho bạn trong mọi h&agrave;nh tr&igrave;nh. Ngo&agrave;i ra, m&aacute;y cũng hỗ trợ dải đ&egrave;n LED đa m&agrave;u sắc, t&iacute;nh năng Keystone II v&agrave; khả năng thay đổi Armor Cap để người d&ugrave;ng thể hiện phong c&aacute;ch v&agrave; c&aacute; t&iacute;nh ri&ecirc;ng.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://hanoicomputercdn.com/media/lib/09-06-2022/rog-strix-g533-hacom-1.png\" style=\"height:750px; width:1000px\" /></span></span></p>\r\n\r\n<h2><strong><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Hiệu năng mạnh mẽ</span></span></strong></h2>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Hỗ trợ l&ecirc;n đến CPU Core i9&nbsp; v&agrave; GPU GeForce&reg; RTX/GTX mới nhất cho tốc độ nhanh, hiệu năng mạnh mẽ v&agrave; hoạt động y&ecirc;n tĩnh (dưới 45dB ở Chế độ Turbo)/&nbsp;Với hiệu suất đ&aacute;ng nể n&agrave;y, kh&ocirc;ng c&oacute; tựa games n&agrave;o c&oacute; thể l&agrave;m kh&oacute; ROG Strix SCAR15. M&aacute;y cũng hỗ trợ 2 khe RAM DDR4 3200MHz, n&acirc;ng cấp l&ecirc;n đến 64GB v&agrave; 2 khe SSD M.2 PCIe NVMe.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://hanoicomputercdn.com/media/lib/09-06-2022/rog-strix-g533-hacom-2.png\" style=\"height:750px; width:1000px\" /></span></span></p>\r\n\r\n<h2><strong><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Tản nhiệt si&ecirc;u khủng</span></span></strong></h2>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Nhiệt độ l&agrave; vấn đề quan ngại nhất với c&aacute;c mẫu laptop gaming. Tuy nhi&ecirc;n tr&ecirc;n mẫu&nbsp;laptop Asus&nbsp;n&agrave;y c&oacute; khả năng l&agrave;m m&aacute;t đột ph&aacute; với keo tản nhiệt kim loại lỏng tr&ecirc;n CPU, gi&uacute;p hệ thống hoạt động m&aacute;t hơn keo tản nhiệt gốm truyền thống. Hệ thống tản nhiệt n&acirc;ng cấp mạnh l&ecirc;n đến 6 ống đồng v&agrave; 4 khe tản nhiệt c&oacute; k&iacute;ch thước lớn. Nhờ đ&oacute;, ROG Strix series c&oacute; thể hoạt động mạnh - m&aacute;t - &ecirc;m &aacute;i. Độ ồn kh&ocirc;ng qu&aacute; 45dB ở chế độ turbo, y&ecirc;n tĩnh hơn so với c&aacute;c sản phẩm đối thủ.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://hanoicomputercdn.com/media/lib/09-06-2022/rog-strix-g533-hacom-3.png\" style=\"height:750px; width:1000px\" /></span></span></p>\r\n\r\n<h2><strong><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Chiến game tốc độ cao si&ecirc;u mượt</span></span></strong></h2>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Chiến game mượt m&agrave; với m&agrave;n h&igrave;nh FHD 144Hz với Adaptive-Sync gi&uacute;p triệt ti&ecirc;u hiện tượng x&eacute; h&igrave;nh. Viền mỏng 4,5mm ở 3 cạnh gi&uacute;p giảm thiểu sự ph&acirc;n t&acirc;m cho trải nghiệm đắm ch&igrave;m.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://hanoicomputercdn.com/media/lib/09-06-2022/rog-strix-g533-hacom-4.png\" style=\"height:750px; width:1000px\" /></span></span></p>\r\n\r\n<h2><strong><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">B&agrave;n ph&iacute;m cơ quang học si&ecirc;u nhạy</span></span></strong></h2>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">ROG Strix SCAR series được trang bị b&agrave;n ph&iacute;m cơ quang học (Optical Mechanical Keyboard) gi&uacute;p c&aacute;c thao t&aacute;c lệnh được nhận nhanh v&agrave; ch&iacute;nh x&aacute;c. Từ đ&oacute; c&aacute;c game thủ chuy&ecirc;n nghiệp c&oacute; thể tung những skill đ&uacute;ng thời điểm v&agrave; tạo lợi thế cho chiến thắng. B&agrave;n ph&iacute;m n&agrave;y cũng c&oacute; độ bền đ&aacute;ng kể hơn b&agrave;n ph&iacute;m cao su b&igrave;nh thường tr&ecirc;n laptop.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://hanoicomputercdn.com/media/lib/09-06-2022/rog-strix-g533-hacom-5.png\" style=\"height:750px; width:1000px\" /></span></span></p>\r\n\r\n<h2><strong><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Tỏa s&aacute;ng với đ&egrave;n LED RGB rực rỡ</span></span></strong></h2>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Hệ thống đ&egrave;n LED đa m&agrave;u sắc nổi bật tại viền mặt đ&aacute;y của m&aacute;y, dưới viền m&agrave;n h&igrave;nh, cũng như đ&egrave;n b&agrave;n ph&iacute;m RGB Per-Key. Người d&ugrave;ng c&oacute; thể dễ d&agrave;ng t&ugrave;y chỉnh. Mặt lưng nh&ocirc;m cứng c&aacute;p cho khung m&aacute;y b&oacute;ng bẩy với th&acirc;n m&aacute;y nhỏ hơn tới 7% so với thế hệ trước. Chiếu nghỉ tay phủ vật liệu soft-touch dễ chịu. Kết nối Keystone II để nhanh ch&oacute;ng khởi chạy một loạt c&aacute;c chức năng linh hoạt, th&ecirc;m m&agrave;u sắc nổi bật với Armor Cap t&ugrave;y chỉnh v&agrave; điều khiển hệ thống đ&egrave;n với AURA Sync.</span></span></p>\r\n\r\n<h2><strong><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Lu&ocirc;n sẵn s&agrave;ng cho mọi h&agrave;nh tr&igrave;nh</span></span></strong></h2>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Vi&ecirc;n pin 90Wh cung cấp đủ năng lượng để l&agrave;m việc d&agrave;i trong ng&agrave;y. Sạc chuẩn Type-C 100W cho ph&eacute;p k&eacute;o d&agrave;i thời gian sử dụng từ c&aacute;c bộ sạc tương th&iacute;ch.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://hanoicomputercdn.com/media/lib/09-06-2022/rog-strix-g533-hacom-6.png\" style=\"height:750px; width:1000px\" /></span></span></p>\r\n\r\n<h2><strong><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">&Acirc;m thanh trong trẻo</span></span></strong></h2>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Hệ thống 4 loa c&ocirc;ng suất cao được hỗ trợ bởi Dolby Atmos mang đến &acirc;m thanh v&ograve;m với chất lượng &acirc;m thanh như ph&ograve;ng thu, đồng thời, t&iacute;nh năng chống ồn AI hai chiều đảm bảo việc ghi &acirc;m giọng n&oacute;i r&otilde; r&agrave;ng để ph&aacute;t trực tuyến v&agrave; hơn thế nữa.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://hanoicomputercdn.com/media/lib/09-06-2022/rog-strix-g533-hacom-7.png\" style=\"height:750px; width:1000px\" /></span></span></p>\r\n\r\n<h2><strong><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Khả năng n&acirc;ng cấp</span></span></strong></h2>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p style=\"text-align:center\"><strong><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://hanoicomputercdn.com/media/lib/09-06-2022/rog-strix-g533-hacom-9.png\" style=\"height:750px; width:1000px\" /></span></span></strong></p>\r\n\r\n<h2><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>KẾT NỐI TO&Agrave;N DIỆN</strong></span></span></h2>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong><img alt=\"\" src=\"https://hanoicomputercdn.com/media/lib/09-06-2022/rog-strix-g533-hacom-10.png\" style=\"height:750px; width:1000px\" /></strong></span></span></p>\r\n',NULL,'ROG Strix series là laptop gaming esports với màn hình nhanh nhất thế giới có thẻ lên đến 300Hz. Ngoài ra, ASUS cũng cung cấp tùy chọn Full HD 240Hz cho một số phiên bản. Năm nay, Strix SCAR được trang bị khả năng làm mát đột phá với keo tản nhiệt kim loại lỏng trên CPU.','https://sieuviet.vn/hm_content/uploads/anh-san-pham/laptop/asus/tin.png','ĐÁNH GIÁ LAPTOP ASUS GAMING ROG STRIX G533ZW-LN134W',NULL,1),(2,'<h1 style=\"text-align:center\"><strong><span style=\"font-family:Times New Roman,Times,serif\">Microsoft chuẩn bị cho ra mắt b&agrave;n ph&iacute;m PC mới với mỗi ph&iacute;m c&oacute; một chức năng chuy&ecirc;n dụng</span></strong></h1>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Microsoft một trong những g&atilde; khổng lồ c&ocirc;ng nghệ về mảng phần mềm với hệ điều h&agrave;nh Windows đang được sử dụng rất rộng r&atilde;i tr&ecirc;n c&aacute;c PC, Laptop ng&agrave;y nay. Trong những năm gần đ&acirc;y, Microsoft cũng đ&atilde; vươn ra khỏi mảng phần mềm với một số thiết bị phần cứng như m&aacute;y t&iacute;nh x&aacute;ch tay Surface, smartphone Lumia v&agrave; c&aacute;c thiết bị ngoại vi cho PC như chuột v&agrave; b&agrave;n ph&iacute;m.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:14px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" class=\"w-100\" src=\"https://www.anphatpc.com.vn/media/news/2408_Microsoft1.jpg\" style=\"height:563px; width:1000px\" /></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Sản phẩm mới nhất m&agrave; g&atilde; khổng lồ phần mềm hiện đang ph&aacute;t triển l&agrave; một b&agrave;n ph&iacute;m ho&agrave;n to&agrave;n mới cho PC với thiết kế lấy cảm hứng từ Surface, ngo&agrave;i ra b&agrave;n ph&iacute;m n&agrave;y cũng đi k&egrave;m với một n&uacute;t mới. Về ngoại h&igrave;nh, b&agrave;n ph&iacute;m mới sẽ c&oacute; ngoại h&igrave;nh tương tự như c&aacute;c mẫu b&agrave;n ph&iacute;m văn ph&ograve;ng kh&aacute;c, chưa biết độ d&agrave;y mỏng của b&agrave;n ph&iacute;m nhưng c&oacute; vẻ như n&oacute; hao hao b&agrave;n ph&iacute;m tr&ecirc;n Surface v&agrave; b&agrave;n ph&iacute;m rời của Apple.</span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">B&agrave;n ph&iacute;m mới của Microsoft sẽ c&oacute; một ph&iacute;m biểu tượng cảm x&uacute;c chuy&ecirc;n dụng được đặt ngay b&ecirc;n cạnh ph&iacute;m Alt b&ecirc;n phải. C&oacute; vẻ như ph&iacute;m n&agrave;y gi&uacute;p thực hiện lệnh khởi chạy giao diện người d&ugrave;ng biểu tượng cảm x&uacute;c đi k&egrave;m với Windows 10, điều m&agrave; Microsoft đ&atilde; ph&aacute;t triển trong một v&agrave;i bản cập nhật t&iacute;nh năng hệ điều h&agrave;nh gần đ&acirc;y nhất.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><img alt=\"\" class=\"w-100\" src=\"https://www.anphatpc.com.vn/media/news/2408_Microsoft2.jpg\" style=\"height:391px; width:1000px\" /></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Hơn nữa, c&oacute; vẻ như Microsoft cũng đ&atilde; thiết kế lại ph&iacute;m F1, ph&iacute;m n&agrave;y hiện được cho l&agrave; để điều khiển kết nối Bluetooth. Nhiều khả năng, ph&iacute;m mới n&agrave;y sẽ cho ph&eacute;p người d&ugrave;ng bật v&agrave; tắt Bluetooth nhanh hơn một ch&uacute;t, mặc d&ugrave; trong Windows 10, to&agrave;n bộ điều c&oacute; thể thực hiện ngay từ một thao t&aacute;c nhanh chuy&ecirc;n dụng trong Action Center.</span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Tại thời điểm n&agrave;y, vẫn chưa biết khi n&agrave;o b&agrave;n ph&iacute;m mới n&agrave;y được cho l&agrave; sẽ ra mắt, nhưng rất r&otilde; r&agrave;ng l&agrave; n&oacute; sẽ nhắm đến c&aacute;c PC, Laptop sử dụng Windows 10. Microsoft r&otilde; r&agrave;ng vẫn k&iacute;n tiếng về mọi kế hoạch của m&igrave;nh, nhưng mong rằng nhiều th&ocirc;ng tin hơn sẽ sớm được chia sẻ. Sẽ kh&ocirc;ng c&oacute; g&igrave; ngạc nhi&ecirc;n khi c&aacute;c đối t&aacute;c của Microsoft ph&aacute;t triển c&aacute;c b&agrave;n ph&iacute;m tương tự với một ph&iacute;m chuy&ecirc;n dụng cho biểu tượng cảm x&uacute;c trong Windows 10 tr&ecirc;n b&agrave;n ph&iacute;m của những mẫu laptop tương lai.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><img alt=\"\" class=\"w-100\" src=\"https://www.anphatpc.com.vn/media/news/2408_Microsoft3.jpg\" style=\"height:563px; width:1000px\" /></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Hiện tại, bất kỳ ai muốn khởi chạy bảng biểu tượng cảm x&uacute;c trong Windows 10 c&oacute; thể chỉ cần nhấn ph&iacute;m Windows + . T&iacute;nh năng n&agrave;y hoạt động trong c&aacute;c phi&ecirc;n bản Windows 10 gần đ&acirc;y nhất, bao gồm cả May 2020 Update (phi&ecirc;n bản 2004), hiện đang được tung ra cho c&aacute;c thiết bị tr&ecirc;n to&agrave;n thế giới.</span></span></p>\r\n\r\n<p>&nbsp;</p>\r\n',NULL,'Microsoft một trong những gã khổng lồ công nghệ về mảng phần mềm với hệ điều hành Windows đang được sử dụng rất rộng rãi trên các PC, Laptop ngày nay. Trong những năm gần đây, Microsoft cũng đã vươn ra khỏi mảng phần mềm với một số thiết bị phần cứng như máy tính xách tay Surface, smartphone Lumia và các thiết bị ngoại vi cho PC.','https://www.anphatpc.com.vn/media/news/2408_Microsoft1.jpg','Microsoft chuẩn bị cho ra mắt bàn phím PC mới với mỗi phím có một chức năng chuyên dụng',NULL,2),(3,'<h1 style=\"text-align:center\"><strong><span style=\"font-family:Times New Roman,Times,serif\">Tr&ecirc;n tay chuột MX Master 3S: Cảm biến 8000 DPI chạy mượt tr&ecirc;n k&iacute;nh, Logi Options+ trực quan hơn</span></strong></h1>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p><strong><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Tiếp nối th&agrave;nh c&ocirc;ng từ MX Master 3, nh&agrave; sản xuất tiếp tục giới thiệu đến người d&ugrave;ng phi&ecirc;n bản&nbsp;<a href=\"https://cellphones.com.vn/chuot-khong-day-logitech-mx-master-3s.html\" rel=\"noopener\" target=\"_blank\">Logitech MX Master 3S</a>&nbsp;với n&acirc;ng cấp lớn nhất l&agrave; phần cảm biến đ&atilde; được n&acirc;ng cấp từ 4000 l&ecirc;n 8000 DPI, thậm ch&iacute; c&ograve;n cao hơn c&aacute;c mẫu chuột gaming. B&ecirc;n cạnh đ&oacute; phần mềm thiết lập cũng được l&agrave;m mới với phi&ecirc;n bản Logi Options+ đẹp hơn, trực quan hơn.</span></span></strong></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Cũng giống như c&aacute;c phi&ecirc;n bản MX Master trước đ&acirc;y, MX Master 3S được đ&oacute;ng hộp kh&aacute; đơn giản với một sợi c&aacute;p sạc v&agrave; một đầu thu đi k&egrave;m. Với phi&ecirc;n bản 3S, người d&ugrave;ng cũng được trang bị đầu thu Logi Bolt, phi&ecirc;n bản xịn s&ograve; hơn so với phi&ecirc;n bản đầu thu Unifying của thế hệ trước. Khi sử dụng với đầu thu, kết nối sẽ đảm bảo được ổn định hơn so với Bluetooth, ngo&agrave;i ra đầu thu cũng gi&uacute;p bạn sử dụng với những chiếc m&aacute;y t&iacute;nh kh&ocirc;ng hỗ trợ Bluetooth.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" class=\"w-100\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-1.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-4.jpg\" style=\"height:167px; width:250px\" /><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-2.jpg\" style=\"height:167px; width:250px\" /><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-3.jpg\" style=\"height:167px; width:250px\" /><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-5.jpg\" style=\"height:167px; width:250px\" /></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Về thiết kế tổng thể, MX Master 3S gần như kh&ocirc;ng c&oacute; sự kh&aacute;c biệt so với MX Master 3. Ch&uacute;ng ta vẫn c&oacute; một con chuột k&iacute;ch thước kh&aacute; &quot;khổng lồ&quot;, với những người c&oacute; ng&oacute;n tay ngắn như m&igrave;nh thực sự hơi kh&oacute; khăn khi khi sử dụng con lăn. Nếu chưa từng sử dụng qua c&aacute;c mẫu MX Master th&igrave; bạn n&ecirc;n đến cửa h&agrave;ng trải nghiệm trước khi quyết định nh&eacute;.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-22.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">C&ograve;n nếu b&agrave;n tay bạn vừa với MX Master 3S th&igrave; ch&uacute; chuột n&agrave;y chắc chắn sẽ mang đến cho bạn những trải nghiệm tuyệt vời m&agrave; lỡ nghiện rồi bạn kh&oacute; m&agrave; chuyển sang nhưng sản phẩm kh&aacute;c sử dụng được.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-23.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Đầu ti&ecirc;n về cảm gi&aacute;c cầm nắm v&agrave; sử dụng, mặc d&ugrave; ng&oacute;n tay m&igrave;nh hơn ngắn để cuộn con lăn một c&aacute;ch thoải m&aacute;i nhưng cảm gi&aacute;c cầm tổng thể rất đ&atilde;, &ocirc;m tay, bề mặt tiếp x&uacute;c dễ chịu. Tuy nhi&ecirc;n m&igrave;nh kh&ocirc;ng bị ra mồ h&ocirc;i tay, nếu bạn n&agrave;o bị ra mồ h&ocirc;i tay nhiều th&igrave; cũng n&ecirc;n c&acirc;n nhắc v&igrave; bề mặt giống cao su của chuột d&iacute;nh mồ h&ocirc;i c&oacute; thể g&acirc;y kh&oacute; chịu, ngo&agrave;i ra nếu tay ra mồ h&ocirc;i nhiều cũng c&oacute; thể l&agrave;m cho bề mặt chuột nhanh hư.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-25.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-20.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-21.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-13.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Về trọng lượng, MX Master 3S nặng tới 141g, kh&aacute; nặng trong thế giới chuột. Nếu bạn th&iacute;ch một c&aacute;i g&igrave; đ&oacute; nhỏ gọn, nhẹ nh&agrave;ng th&igrave; r&otilde; r&agrave;ng đ&acirc;y kh&ocirc;ng phải lựa chọn d&agrave;nh cho bạn. MX Master 3S ph&ugrave; hợp với những ai th&iacute;ch một con chuột đằm tay, độ ch&iacute;nh x&aacute;c cao, cầm nắm tho&aacute;i m&aacute;i trong thời gian d&agrave;i.</span></span></p>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">B&ecirc;n cạnh đ&oacute;, bạn c&oacute; thể tham khảo th&ecirc;m c&aacute;c&nbsp;<strong><a href=\"https://cellphones.com.vn/sforum/tag/chuot-logitech\">mẹo hay khi sử dụng chuột Logitech</a></strong>&nbsp;đơn giản, dễ thực hiện tại nh&agrave;. Ngo&agrave;i ra, c&aacute;c mẫu tr&ecirc;n tay chuột logitech HOT cũng đang được li&ecirc;n tục cập nhật hiện nay.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-29.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Ngo&agrave;i cảm biến quang học c&oacute; thể sử dụng tr&ecirc;n k&iacute;nh 8000 DPI th&igrave; MX Master 3S c&ograve;n sở hữu kh&aacute; nhiều t&iacute;nh năng đặc biệt. Đầu ti&ecirc;n c&oacute; thể kể đến l&agrave; MagSpeed SmartShift - t&iacute;nh năng cho ph&eacute;p con lăn cuộn h&agrave;ng ng&agrave;n d&ograve;ng, ph&ugrave; hợp khi xử l&yacute; số liệu, l&agrave;m việc với Excel. Bạn cũng c&oacute; thể chuyển sang chế độ cuộn chuột từng nấc giống như những con chuột th&ocirc;ng thường kh&aacute;c.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-16.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Tr&ecirc;n MX Master 3S, bạn c&oacute; thể thiết lập n&uacute;t bấm để thay độ giữa hai chế độ cuộn chuột. Cụ thể th&igrave; tr&ecirc;n một số d&ograve;ng&nbsp;<a href=\"https://cellphones.com.vn/phu-kien/chuot-ban-phim-may-tinh/chuot/logitech.html\"><strong>chuột Logitech</strong></a>&nbsp;bạn chỉ c&oacute; thể thay đổi chế độ cuộn bằng c&aacute;ch bấm v&agrave;o con lăn - hay ch&iacute;nh l&agrave; n&uacute;t chuột giữa. Nếu bạn c&oacute; th&oacute;i quen d&ugrave;ng n&uacute;t chuột giữa để đ&oacute;ng mở tab, mở link trong tab mới... th&igrave; điều n&agrave;y g&acirc;y nhiều bất tiện. Để khắc phục nhược điểm đ&oacute;, MX Master 3S cho ph&eacute;p bạn t&ugrave;y chỉnh n&uacute;t thay đổi chế độ cuộn giữa n&uacute;t bấm v&agrave;o con lăn hoặc d&ugrave;ng một n&uacute;t bấm kh&aacute;c nằm ph&iacute;a dưới con lăn.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-30.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Tiếp theo phải kể đến con lăn nằm b&ecirc;n h&ocirc;ng, bạn c&oacute; thể sử dụng như một n&uacute;t cuộn ngang để l&agrave;m việc với Excel, lập tr&igrave;nh hoặc những c&ocirc;ng việc cần đến thanh cuộn ngang n&oacute;i chung. Nếu kh&ocirc;ng th&iacute;ch, bạn cũng c&oacute; thể thiết lập n&oacute; để chuyển&nbsp;<a href=\"https://cellphones.com.vn/man-hinh.html\" title=\"màn hình\">m&agrave;n h&igrave;nh</a>&nbsp;ảo, chuyển ứng dụng, thiết lập như một n&uacute;t zoom in/zoom out cho timeline khi dựng phim... Bạn cũng c&oacute; thể thiết lập cho tất cả c&aacute;c n&uacute;t c&oacute; tr&ecirc;n con chuột sẽ hoạt động với c&aacute;c chức năng ri&ecirc;ng biệt khi d&ugrave;ng c&aacute;c app kh&aacute;c nhau.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-15.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Khả năng t&ugrave;y chỉnh của&nbsp;<a href=\"https://cellphones.com.vn/sforum/tag/logitech\" rel=\"noopener\" target=\"_blank\"><strong>chuột Logitech</strong></a>&nbsp;gần như kh&ocirc;ng giới hạn bởi ngo&agrave;i những t&iacute;nh năng c&oacute; sẵn trong phần mềm, bạn c&oacute; thể g&aacute;n ph&iacute;m tắt cho ch&uacute;ng, bạn c&oacute; thể g&aacute;n ph&iacute;m tắt của hệ điều h&agrave;nh, của phần mềm... N&oacute;i chung giới hạn của việc t&ugrave;y biến chuột c&oacute; lẽ đến từ người d&ugrave;ng chứ kh&ocirc;ng đến từ thiết bị.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-12.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Ngo&agrave;i ra, giống c&aacute;c thế hệ trước MX Master 3S cũng c&oacute; một n&uacute;t bấm nằm ngay ng&oacute;n c&aacute;i, bạn c&oacute; thể thiết lập n&oacute; như một ph&iacute;m &quot;Shift&quot; hoặc một t&iacute;nh năng bất kỳ. MX Master 3S cũng c&oacute; t&iacute;nh năng Flow để chuyển file giữa c&aacute;c m&aacute;y t&iacute;nh nhưng m&igrave;nh &iacute;t d&ugrave;ng t&iacute;nh năng n&agrave;y.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/MX-MASTER-3S-sforum-6.jpg\" style=\"height:667px; width:1000px\" /></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Về phần mềm điều khiển, hiện tại Option đ&atilde; được đổi th&agrave;nh Logi Options+ trực quan hơn. Hiện tại c&aacute;c d&ograve;ng cũ hơn v&iacute; dụ như MX Anywhere 2S m&igrave;nh đang x&agrave;i cũng đề xuất m&igrave;nh d&ugrave;ng phần mềm mới n&agrave;y chứ kh&ocirc;ng c&ograve;n d&ugrave;ng phần mềm cũ nữa. Ngo&agrave;i việc trực quan hơn, gọn g&agrave;ng hơn th&igrave; Logi Options+ cũng nhẹ hơn.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/2-19.jpg\" style=\"height:571px; width:1000px\" /></span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/Screenshot-2022-09-29-185506.jpg\" style=\"height:286px; width:500px\" /><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/Screenshot-2022-09-29-185634.jpg\" style=\"height:286px; width:500px\" /></span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/Screenshot-2022-09-29-185652.jpg\" style=\"height:286px; width:500px\" /><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/Screenshot-2022-09-29-185615.jpg\" style=\"height:286px; width:500px\" /></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Khi lần đầu kết nối hoặc cắm đầu thu v&agrave;o m&aacute;y t&iacute;nh, một th&ocirc;ng b&aacute;o sẽ hiện l&ecirc;n đề nghị bạn c&agrave;i đặt phần mềm, với Logi Options+ dường như g&oacute;i c&agrave;i đặt được n&eacute;n lại để tải nhanh hơn, sau khi m&aacute;y tự tải về th&igrave; hệ thống cũng tự giải n&eacute;n v&agrave; c&agrave;i đặt lu&ocirc;n. N&oacute;i chung trải nghiệm ngon, liền lạc.</span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><img alt=\"\" src=\"https://cdn.sforum.vn/sforum/wp-content/uploads/2022/10/MX-MASTER-3S-sforum-28.jpg\" style=\"height:666px; width:1000px\" /></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Khi x&agrave;i với Mac, phần mềm cũng fix lu&ocirc;n vụ cuộn ngược cuộn xu&ocirc;i, tức l&agrave; bạn c&oacute; thể để mặc định để d&ugrave;ng với trackpad một c&aacute;c tự nhi&ecirc;n, khi d&ugrave;ng chuột cũng kh&ocirc;ng bị ngược. Vấn đề n&agrave;y th&igrave; bạn c&oacute; thể c&agrave;i th&ecirc;m phần mềm hỗ trợ nhưng nếu c&oacute; app Logi Options+ th&igrave; n&oacute; tự fix m&agrave; bạn kh&ocirc;ng cần l&agrave;m g&igrave; th&ecirc;m.</span></span></p>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">MX Master 3S c&oacute; hai m&agrave;u X&aacute;m trắng v&agrave; m&agrave;u than ch&igrave;, m&igrave;nh nghĩ m&agrave;u than ch&igrave; trong b&agrave;i ph&ugrave; hợp với số đ&ocirc;ng hơn bởi một con chuột m&agrave;u trắng lại c&oacute; bề mặt cao su th&igrave; rất dễ b&aacute;m bẩn, kh&oacute; vệ sinh. Mức gi&aacute; ni&ecirc;m yết của sản phẩm l&agrave; khoảng 3.3 triệu đồng.</span></span></p>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p>&nbsp;</p>\r\n',NULL,'Tiếp nối thành công từ MX Master 3, nhà sản xuất tiếp tục giới thiệu đến người dùng phiên bản Logitech MX Master 3S với nâng cấp lớn nhất là phần cảm biến đã được nâng cấp từ 4000 lên 8000 DPI, thậm chí còn cao hơn các mẫu chuột gaming. Bên cạnh đó phần mềm thiết lập cũng được làm mới với phiên bản Logi Options+ đẹp hơn.','https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/coover.jpg','Trên tay chuột MX Master 3S: Cảm biến 8000 DPI chạy mượt trên kính, Logi Options+ trực quan hơn',NULL,3),(4,'<h1 style=\"text-align:center\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>USB l&agrave; g&igrave;? Cấu tạo v&agrave; chức năng của USB c&oacute; thể bạn chưa biết</strong></span></h1>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<h3><strong><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">USB l&agrave; thiết bị kh&ocirc;ng thể thiếu khi sử dụng m&aacute;y t&iacute;nh. Vậy bạn đ&atilde; biết USB l&agrave; g&igrave;? Cấu tạo v&agrave; chức năng ra sao chưa? C&ugrave;ng t&igrave;m hiểu chi tiết qua những th&ocirc;ng tin của b&agrave;i viết.</span></span></strong></h3>\r\n\r\n<ul style=\"margin-left:40px\">\r\n	<li>\r\n	<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><a href=\"https://fptshop.com.vn/tin-tuc/thu-thuat/headset-la-gi-va-co-gi-khac-voi-headphone-132056\">Headset l&agrave; g&igrave;? Tai nghe Headset c&oacute; g&igrave; kh&aacute;c biệt với headphone?</a></span></span></p>\r\n	</li>\r\n	<li>\r\n	<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><a href=\"https://fptshop.com.vn/tin-tuc/thu-thuat/usb-dongle-la-gi-127813\">USB Dongle l&agrave; g&igrave;? C&oacute; bao nhi&ecirc;u loại v&agrave; chức năng ra sao?</a></span></span></p>\r\n	</li>\r\n	<li>\r\n	<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><a href=\"https://fptshop.com.vn/tin-tuc/thu-thuat/usb-token-la-gi-127807\">Chữ k&yacute; số Token (USB Token) l&agrave; g&igrave; v&agrave; d&ugrave;ng để l&agrave;m g&igrave;?</a></span></span></p>\r\n	</li>\r\n</ul>\r\n\r\n<h3><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">USB hay ổ USB, USB flash drive l&agrave; thiết bị lưu trữ dữ liện phổ biến v&agrave; tiện lợi nhất hiện nay, với c&aacute;c đặc điểm nổi bật gồm gọn nhẹ, dung lượng lớn v&agrave; đa chức năng. Trong b&agrave;i viết n&agrave;y, ch&uacute;ng ta sẽ t&igrave;m hiểu sơ qua về định nghĩa&nbsp;<a href=\"https://fptshop.com.vn/tin-tuc/thu-thuat/o-usb-la-gi-127727\" title=\"USB là gì\" type=\"USB là gì\">USB l&agrave; g&igrave;</a>? Cấu tạo v&agrave; c&aacute;c chức năng thường thấy của ổ USB.</span></span></h3>\r\n\r\n<h2><span style=\"font-size:18px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>Định nghĩa USB l&agrave; g&igrave;?</strong></span></span></h2>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Ổ USB Flash, thường được gọi l&agrave; USB, l&agrave; một thiết bị lưu trữ dữ liệu sử dụng bộ nhớ flash (một dạng IC &ndash; vi mạch nhớ hỗ trợ th&aacute;o lắp nhanh), t&iacute;ch hợp với giao tiếp USB (Universal Serial Bus).</span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">USB c&oacute; k&iacute;ch thước nhỏ nhẹ v&agrave; cho ph&eacute;p người d&ugrave;ng tự do ghi lại dữ liệu. Dung lượng của c&aacute;c&nbsp;<a href=\"https://fptshop.com.vn/phu-kien/usb-o-cung\" title=\"Tham khảo USB chính hãng giá rẻ\" type=\"Tham khảo USB chính hãng giá rẻ\">USB</a>&nbsp;tr&ecirc;n thị trường hiện nay c&oacute; thể l&ecirc;n tới 2TB v&agrave; c&ograve;n c&oacute; thể tăng nữa trong tương lai.</span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Xem th&ecirc;m:&nbsp;<em><a href=\"https://fptshop.com.vn/tin-tuc/danh-gia/usb-type-c-la-gi-tim-hieu-usb-type-c-57333\" title=\"USB type C là gì? Tìm hiểu về cổng USB type C\" type=\"USB type C là gì? Tìm hiểu về cổng USB type C\">USB type C l&agrave; g&igrave;? T&igrave;m hiểu về cổng USB type C</a></em></span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><em><img alt=\"\" src=\"https://images.fpt.shop/unsafe/filters:quality(90)/fptshop.com.vn/uploads/images/tin-tuc/127727/Originals/reuse-old-usb-stick.jpg\" style=\"height:405px; width:780px\" /></em></span></span></p>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><em>USB l&agrave; thiết bị g&igrave;?</em></span></span></p>\r\n\r\n<h2><span style=\"font-size:18px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>Cấu tạo của USB</strong></span></span></h2>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Một ổ USB flash th&ocirc;ng thường gồm c&aacute;c bộ phận sau:</span></span></p>\r\n\r\n<ul style=\"margin-left:40px\">\r\n	<li><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>Bản mạch in nhỏ:&nbsp;</strong>chứa c&aacute;c linh kiện điện tử c&ugrave;ng một (hoặc nhiều) chip nhớ flash h&agrave;n trực tiếp l&ecirc;n mạch in.</span></span></li>\r\n</ul>\r\n\r\n<p style=\"text-align:center\"><img alt=\"\" src=\"https://images.fpt.shop/unsafe/filters:quality(90)/fptshop.com.vn/uploads/images/tin-tuc/127727/Originals/1024px-USB_flash_drive.jpg\" style=\"height:666px; width:1000px\" /></p>\r\n\r\n<ul style=\"margin-left:40px\">\r\n	<li><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>Đầu cắm kết nối với c&aacute;c cổng USB:</strong>&nbsp;c&aacute;c kết nối thường sử dụng chuẩn A cho ph&eacute;p ch&uacute;ng kết nối trực tiếp với c&aacute;c khe cắm USB tr&ecirc;n&nbsp;<a href=\"https://fptshop.com.vn/may-tinh-xach-tay\" title=\"Laptop chính hãng\" type=\"Laptop chính hãng\">m&aacute;y t&iacute;nh</a>.</span></span></li>\r\n	<li><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>Vỏ bảo vệ:</strong>&nbsp;To&agrave;n bộ bản mạch in, chip nhớ flash nằm trong một vỏ bảo vệ kim loại hoặc nhựa gi&uacute;p n&oacute; đủ chắc chắn. Chỉ c&oacute; đầu kết nối USB l&agrave; nằm ngo&agrave;i vỏ bảo vệ n&agrave;y v&agrave; thường c&oacute; nắp để đậy lại, hoặc một n&uacute;t gạt cho ph&eacute;p đầu kết nối c&oacute; thể trượt v&agrave;o b&ecirc;n trong vỏ v&agrave; ch&igrave;a ra ngo&agrave;i khi cần thiết. Vỏ bảo vệ thường được thiết kế đa dạng nhằm hấp dẫn người sử dụng, một số loại c&ograve;n c&oacute; khả năng chống thấm ướt, chống sốc.</span></span></li>\r\n</ul>\r\n\r\n<p style=\"text-align:center\"><img alt=\"\" src=\"https://images.fpt.shop/unsafe/filters:quality(90)/fptshop.com.vn/uploads/images/tin-tuc/127727/Originals/SanDisk-Cruzer-USB-4GB-ThumbDrive(1).jpg\" style=\"height:580px; width:1000px\" /></p>\r\n\r\n<ul style=\"margin-left:40px\">\r\n	<li><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>Lẫy gạt chống ghi:&nbsp;</strong>Một số ổ USB flash c&oacute; thiết kế lẫy gạt để chống ghi dữ liệu, kh&ocirc;ng cho ph&eacute;p hệ điều h&agrave;nh ghi hoặc sửa đổi dữ liệu v&agrave;o ổ.</span></span></li>\r\n	<li><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>Đ&egrave;n b&aacute;o hoạt động:&nbsp;</strong>Đa phần c&aacute;c ổ USB flash c&oacute; một đ&egrave;n b&aacute;o nhỏ để hiển thị chế độ l&agrave;m việc của n&oacute; (đ&egrave;n n&agrave;y l&agrave; một điốt LED nhỏ gắn tr&ecirc;n bo mạch của ổ, c&oacute; m&agrave;u kh&aacute;c nhau tuỳ h&atilde;ng). C&aacute;ch đ&egrave;n b&aacute;o hiệu hoạt động cũng kh&ocirc;ng được thống nhất giữa c&aacute;c h&atilde;ng sản xuất: c&oacute; loại khi USB s&aacute;ng đ&egrave;n l&agrave; trạng th&aacute;i đang đọc hoặc ghi v&agrave; ngược lại tắt đ&egrave;n l&agrave; nghỉ, c&oacute; loại s&aacute;ng đ&egrave;n l&agrave; nghỉ v&agrave; tắt đ&egrave;n l&agrave; đọc/ghi v&agrave; sẽ nhấp nh&aacute;y li&ecirc;n tục trong suốt qu&aacute; tr&igrave;nh đ&oacute;. Người sử dụng n&ecirc;n tự quan s&aacute;t USB của m&igrave;nh v&agrave;i lần để biết được quy luật hoạt động của đ&egrave;n b&aacute;o v&agrave; tr&aacute;nh th&aacute;o thiết bị khi ch&uacute;ng đang l&agrave;m việc.</span></span></li>\r\n</ul>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Xem th&ecirc;m:&nbsp;<em><a href=\"https://fptshop.com.vn/tin-tuc/danh-gia/phan-biet-usb-3-0-va-usb-2-0-35333\" title=\"USB 3.0 là gì và cách phân biệt với USB 2.0\" type=\"USB 3.0 là gì và cách phân biệt với USB 2.0\">USB 3.0 l&agrave; g&igrave; v&agrave; c&aacute;ch ph&acirc;n biệt với USB 2.0</a></em></span></span></p>\r\n\r\n<h2><span style=\"font-size:18px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>Chức năng ch&iacute;nh của một USB</strong></span></span></h2>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Mặc d&ugrave; ch&iacute;nh năng ch&iacute;nh l&agrave; để lưu trữ dữ liệu v&agrave; chuyển dữ liệu qua lại giữa c&aacute;c thiết bị, USB vẫn được sử dụng với c&aacute;c c&ocirc;ng dụng sau:</span></span></p>\r\n\r\n<h3><span style=\"font-size:18px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>1. Sửa chữa m&aacute;y t&iacute;nh</strong></span></span></h3>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Đa số c&aacute;c m&aacute;y t&iacute;nh được sản xuất trong những năm gần đ&acirc;y đều cho ph&eacute;p khởi động từ ổ USB flash, tức l&agrave; sau khi cắm USB v&agrave;o m&aacute;y v&agrave; khởi động, người d&ugrave;ng c&oacute; thể thao t&aacute;c, sửa chữa hệ điều h&agrave;nh hoặc c&aacute;c&nbsp;<a href=\"https://fptshop.com.vn/phan-mem\" title=\"Phần mềm máy tính bản quyền\" type=\"Phần mềm máy tính bản quyền\">phần mềm</a>&nbsp;bị lỗi tr&ecirc;n m&aacute;y t&iacute;nh. Thậm ch&iacute; một số loại USB c&ograve;n cho ph&eacute;p lưu v&agrave; cập nhật BIOS, vốn trước đ&acirc;y chỉ c&oacute; thể thao t&aacute;c được qua đĩa mềm.</span></span></p>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<h3><span style=\"font-size:18px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>2. Quản trị hệ thống</strong></span></span></h3>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">C&aacute;c sử dụng n&agrave;y rất phổ biến với những người quản trị mạng v&agrave; hệ thống. Bằng c&aacute;ch lưu lại một bộ thiết lập từ m&aacute;y t&iacute;nh đầu ti&ecirc;n v&agrave;o USB, sau đ&oacute; chỉ cần cắm USB đ&oacute; v&agrave;o c&aacute;c m&aacute;y t&iacute;nh, bộ thiết lập sẽ được sao ch&eacute;p v&agrave; &aacute;p dụng ngay cho m&aacute;y t&iacute;nh mới v&agrave; kh&ocirc;ng cần người d&ugrave;ng phải tự tay thiết lập lại. Việc n&agrave;y sẽ đảm bảo tất cả c&aacute;c m&aacute;y t&iacute;nh c&oacute; c&ugrave;ng một bộ thiết lập y hệt nhau m&agrave; kh&ocirc;ng c&oacute; nhầm lẫn hoặc sai s&oacute;t n&agrave;o.</span></span></p>\r\n\r\n<h3><span style=\"font-size:18px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>3. Ch&igrave;a kh&oacute;a điện tử</strong></span></span></h3>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Với một số hệ thống m&aacute;y t&iacute;nh y&ecirc;u cầu bảo mật cao, USB c&oacute; thể đ&oacute;ng vai tr&ograve; như một chiếc ch&igrave;a kh&oacute;a điện tử để khởi động hệ thống hoặc một phần mềm tr&ecirc;n m&aacute;y. Một số h&atilde;ng viết phần mềm cũng sử dụng USB được thiết kế ri&ecirc;ng để k&iacute;ch hoạt mỗi khi cần sử dụng phần mềm, nhằm tr&aacute;nh sự sao ch&eacute;p v&agrave; sử dụng tr&aacute;i ph&eacute;p c&aacute;c phần mềm đ&oacute;.</span></span></p>\r\n\r\n<h2><span style=\"font-size:18px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong>Bảo mật dữ liệu trong USB</strong></span></span></h2>\r\n\r\n<p style=\"text-align:center\"><span style=\"font-size:18px\"><span style=\"font-family:Times New Roman,Times,serif\"><strong><img alt=\"\" src=\"https://images.fpt.shop/unsafe/filters:quality(90)/fptshop.com.vn/uploads/images/tin-tuc/127727/Originals/HTB1KOLRjcnI8KJjSspeq6AwIpXa6.jpg\" style=\"height:800px; width:1000px\" /></strong></span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Với một chiếc USB th&ocirc;ng thường, bất cứ ai cũng c&oacute; thể truy cập v&agrave; chỉnh sửa dữ liệu b&ecirc;n trong n&oacute;. Tuy nhi&ecirc;n t&ugrave;y v&agrave;o nhu cầu của người d&ugrave;ng, c&oacute; một số chiếc USB c&oacute; chức năng y&ecirc;u cầu mật khẩu mỗi khi sử dụng. Cao cấp hơn c&ograve;n c&oacute; loại USB y&ecirc;u cầu x&aacute;c nhận sinh trắc học (v&acirc;n tay), v&agrave; chỉ c&oacute; người sử dụng đầu ti&ecirc;n mới c&oacute; thể truy cập v&agrave;o dữ liệu b&ecirc;n trong USB đ&oacute;.</span></span></p>\r\n\r\n<p><span style=\"font-size:16px\"><span style=\"font-family:Times New Roman,Times,serif\">Tham khảo:&nbsp;<em><a href=\"https://fptshop.com.vn/tin-tuc/tu-van/usb-otg-la-gi-va-lam-the-nao-de-su-dung-no-11880\" title=\"USB OTG là gì và làm thế nào để sử dụng nó?\" type=\"USB OTG là gì và làm thế nào để sử dụng nó?\">USB OTG l&agrave; g&igrave; v&agrave; l&agrave;m thế n&agrave;o để sử dụng n&oacute;?</a></em></span></span></p>\r\n',NULL,'USB hay ổ USB, USB flash drive là thiết bị lưu trữ dữ liện phổ biến và tiện lợi nhất hiện nay, với các đặc điểm nổi bật gồm gọn nhẹ, dung lượng lớn và đa chức năng. Trong bài viết này, chúng ta sẽ tìm hiểu sơ qua về định nghĩa USB là gì? Cấu tạo và các chức năng thường thấy của ổ USB.','https://images.fpt.shop/unsafe/filters:quality(90)/fptshop.com.vn/uploads/images/tin-tuc/127727/Originals/reuse-old-usb-stick.jpg','USB là gì? Cấu tạo và chức năng của USB có thể bạn chưa biết',NULL,4);
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `num` int NOT NULL,
  `price` double NOT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `total_price` double NOT NULL,
  `discount` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  KEY `FK4q98utpd73imf4yhttm3w0eax` (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (31,1,1000000,25,1,1000000,0),(32,1,100000,26,27,100000,0),(33,2,100000,27,40,200000,0),(34,1,20000000,27,10,18000000,10),(51,3,100000,41,21,300000,0),(52,1,100000,41,27,100000,0),(53,1,100000,42,16,100000,0),(54,1,20000000,43,5,18000000,10),(55,1,100000,44,27,100000,0),(56,1,100000,45,27,100000,0),(57,1,1000000,46,11,1000000,0);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address_delivery` varchar(200) COLLATE utf8mb3_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `email` varchar(150) COLLATE utf8mb3_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb3_unicode_ci NOT NULL,
  `num` int NOT NULL,
  `phone` varchar(20) COLLATE utf8mb3_unicode_ci NOT NULL,
  `user_id` bigint NOT NULL,
  `payment` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `total_money` double NOT NULL,
  `code_order` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `state_checkout` smallint DEFAULT NULL,
  `state_order` smallint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (25,'hn','2023-06-11 10:53:22','user@gmail.com','Người dùng',1,'1234567890',2,'COD',1000000,'25220230611105321',0,2),(26,'hcm','2023-06-11 12:50:48','admin@gmail.com','Quản trị viên',1,'1234567890',1,'TRANSFER',100000,'26120230611125048',1,4),(27,'bbb','2023-06-12 21:47:19','admin@gmail.com','Quản trị viên',3,'222233333',1,'COD',18200000,'27120230612214719',0,3),(41,'f1','2023-06-26 18:23:39','admin@gmail.com','Quản trị viên',4,'f1',1,'COD',400000,'41120230626182338',0,0),(42,'d1','2023-06-26 18:32:58','admin@gmail.com','Quản trị viên',1,'d1',1,'COD',100000,'42120230626183258',0,0),(43,'321','2023-06-26 18:35:14','admin@gmail.com','Quản trị viên',1,'123',1,'TRANSFER',18000000,'43120230626183514',0,0),(44,'2','2023-06-26 18:35:59','admin@gmail.com','Quản trị viên',1,'2',1,'TRANSFER',100000,'44120230626183559',0,4),(45,'191','2023-06-26 21:37:31','admin@gmail.com','Quản trị viên',1,'191',1,'COD',100000,'45120230626213730',0,1),(46,'ccv','2023-06-26 21:43:49','admin@gmail.com','Quản trị viên',1,'ccv',1,'TRANSFER',1000000,'46120230626214349',0,0);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `deleted` int DEFAULT NULL,
  `description` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `discount` double DEFAULT NULL,
  `name` varchar(350) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  `thumbnail` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `quantity_in_stock` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv` (`brand_id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'2023-05-21 13:22:50',0,'Core i3, Ram 4GB, 256GB SSD, Windows 10',0,'ASUS 1',1000000,'ASUS-1.webp','2023-05-21 13:22:50',1,1,10),(2,'2023-05-21 13:22:50',0,'Core i3, Ram 8GB, 256GB SSD, Windows 10',0,'ASUS 2',2500000,'ASUS-2.webp',NULL,1,1,10),(3,'2023-05-21 13:22:50',0,'Core i5, Ram 8GB, 256GB SSD, Windows 10',0,'ASUS 3',5000000,'ASUS-3.webp',NULL,1,1,10),(4,'2023-05-21 13:22:50',0,'Core i7, Ram 8GB, 256GB SSD, Windows 10',20,'ASUS 4',10000000,'ASUS-4.webp',NULL,1,1,10),(5,'2023-05-21 13:22:50',0,'Core i7, Ram 16GB, 256GB SSD, Windows 10',10,'ASUS 5',20000000,'ASUS-5.webp',NULL,1,1,10),(6,'2023-05-21 13:22:50',0,'Core i3, Ram 4GB, 256GB SSD, Windows 10',0,'DELL 1',1000000,'DELL-1.webp',NULL,2,1,10),(7,'2023-05-21 13:22:50',0,'Core i3, Ram 8GB, 256GB SSD, Windows 10',0,'DELL 2',2500000,'DELL-2.webp',NULL,2,1,10),(8,'2023-05-21 13:22:50',0,'Core i5, Ram 8GB, 256GB SSD, Windows 10',0,'DELL 3',5000000,'DELL-3.webp',NULL,2,1,10),(9,'2023-05-21 13:22:50',0,'Core i7, Ram 8GB, 256GB SSD, Windows 10',20,'DELL 4',10000000,'DELL-4.webp',NULL,2,1,10),(10,'2023-05-21 13:22:50',0,'Core i7, Ram 16GB, 256GB SSD, Windows 10',10,'DELL 5',20000000,'DELL-5.webp',NULL,2,1,10),(11,'2023-05-21 13:22:50',0,'Core i3, Ram 4GB, 256GB SSD, Windows 10',0,'HP 1',1000000,'HP-1.webp',NULL,3,1,10),(12,'2023-05-21 13:22:50',0,'Core i3, Ram 8GB, 256GB SSD, Windows 10',0,'HP 2',2500000,'HP-2.webp',NULL,3,1,10),(13,'2023-05-21 13:22:50',0,'Core i5, Ram 8GB, 256GB SSD, Windows 10',0,'HP 3',5000000,'HP-3.webp',NULL,3,1,10),(14,'2023-05-21 13:22:50',0,'Core i7, Ram 8GB, 256GB SSD, Windows 10',20,'HP 4',10000000,'HP-4.webp',NULL,3,1,10),(15,'2023-05-21 13:22:50',0,'Core i7, Ram 16GB, 256GB SSD, Windows 10',10,'HP 5',20000000,'HP-5.webp',NULL,3,1,10),(16,'2023-05-21 13:22:50',0,'Loại kết nối bàn phím Bàn phím có dây, Kết nối của bàn phím USB 2.0, Kích thước của bàn phím Full size, Loại bàn phím Bàn phím cơ, NX Red Switch',0,'KEYBOARD ASUS 1',100000,'KEYBOARD-ASUS-1.webp',NULL,1,2,10),(17,'2023-05-21 13:22:50',0,'Bà̀n phím giả cơ, Kết nối: USB, Phím chức năng: Có',0,'KEYBOARD ASUS 2',200000,'KEYBOARD-ASUS-2.webp',NULL,1,2,10),(18,'2023-05-21 13:22:50',0,'Bàn phím cơ, Kết nối: USB 2.0, Switch: ROG NX Blue, Phím chức năng: Có',0,'KEYBOARD ASUS 3',400000,'KEYBOARD-ASUS-3.webp',NULL,1,2,10),(19,'2023-05-21 13:22:50',0,'Bàn phím cơ, Kết nối USB 2.0, Kiểu switch Cherry MX Blue',20,'KEYBOARD ASUS 4',800000,'KEYBOARD-ASUS-4.webp',NULL,1,2,10),(20,'2023-05-21 13:22:50',0,'Màu sắc của bàn phím Đen, Loại kết nối bàn phím Bàn phím không dây, Kết nối của bàn phím USB 2.0, 2.4 GHz Wireless, Loại bàn phím Bàn phím cơ, Switch: ROG NX Mechanical: Blue, Kích thước của bàn phím Tenkeyless',10,'KEYBOARD ASUS 5',1600000,'KEYBOARD-ASUS-5.webp',NULL,1,2,10),(21,'2023-05-21 13:22:50',0,'Kiểu: Bàn phím thường, Kiểu kết nối: Có dây, Kích thước: Full size, Màu sắc: Đen',0,'KEYBOARD DELL 1',100000,'KEYBOARD-DELL-1.webp',NULL,2,2,10),(22,'2023-05-21 13:22:50',0,'Kết nối USB 2.0, Kiểu cầm Ambidextrous / Đối xứng, Switch Omron',0,'MOUSE ASUS 1',100000,'MOUSE-ASUS-1.webp',NULL,1,3,10),(23,'2023-05-21 13:22:50',0,'Thiết kế cân xứng Ergonomic cho cảm giác cầm nắm thoải mái , Tốc độ chuột lên đến 7000 DPI kèm theo 2 nút DPI tinh chỉnh dễ dàng khi đang sử dụng , LED RGB tích hợp có thể điều chỉnh cùng với các tính năng qua phần mềm AMOURY II',0,'MOUSE ASUS 2',200000,'MOUSE-ASUS-2.webp',NULL,1,3,10),(24,'2023-05-21 13:22:50',0,'DPI tối đa: 16.000 dpi được tinh chỉnh đặc biệt, tốc độ quét 400 ips và polling rate 1000 Hz, Thiết kế socket cho switch Push-Fit giúp thay đổi lực click và kéo dài độ bền của chuột, Các nút L/R bằng polymer PBT cao cấp, ROG Paracord và feet chuột 100% PTFE giúp lướt nhanh và mượt, DPI On-The-Scroll để điều chỉnh độ chính xác dễ dàng, Phần mềm Armory Crate độc quyền để cấu hình dễ dàng và trực quan  ',0,'MOUSE ASUS 3',400000,'MOUSE-ASUS-3.webp',NULL,1,3,10),(25,'2023-05-21 13:22:50',0,'Điều khiển DPI linh hoạt, Bốn nấc DPI tùy chỉnh và được báo bằng đèn led, (500/1000/1500/2500 DPI), Thiết kế cân đối, tạo khuôn ôm ngón tay cái và áp út, tạo cảm giác cầm dễ dàng và quen thuộc, Hai lớp cao su được gắn bên hông tạo cảm giác cầm mềm và chống trượt tối ưu, Kích thước : 124.86 (L) x 68.72(W) x 40.11(H) mm',0,'MOUSE ASUS 4',800000,'MOUSE-ASUS-4.webp',NULL,1,3,10),(26,'2023-05-21 13:22:50',0,'Phiên bản giới hạn PNK LTD , Led RGB Aura hỗ trợ Aura Sync , Nút bấm Omron bền bỉ mang đến cảm giác chuột ấn tượng , Cảm biến Quang DPI lên đến 12000',0,'MOUSE ASUS 5',1600000,'MOUSE-ASUS-5.webp',NULL,1,3,10),(27,'2023-05-21 13:22:50',0,'Kiểu kết nối: Có dây, Dạng cảm biến: Optical, Độ phân giải: 1000 DPI, Màu sắc: Đen',0,'MOUSE DELL 1',100000,'MOUSE-DELL-1.webp',NULL,2,3,10),(28,'2023-05-21 13:22:50',0,'Kết nối 2.4 GHz Wireless, Kiểu cầm Ambidextrous / Đối xứng, Độ phân giải (CPI/DPI) 1000DPI, Dạng cảm biến Optical',0,'MOUSE DELL 2',200000,'MOUSE-DELL-2.webp',NULL,2,3,10),(29,'2023-05-21 13:22:50',0,'Kết nối USB 2.0, Trang bị dạng cảm biến Laser, 5 nút bấm tiện lợi, Trọng lượng nhẹ, độ phân giải 3200DPI, ',0,'MOUSE DELL 3',400000,'MOUSE-DELL-3.webp',NULL,2,3,10),(30,'2023-05-21 13:22:50',0,'Kiểu kết nối Chuột có dây, Đèn LED RGB, Màu sắc Tím, Kết nối USB 2.0, Kiểu cầm Ambidextrous / Đối xứng, Độ phân giải (CPI/DPI) 8000DPI',0,'MOUSE LOGITECH 1',100000,'MOUSE-LOGITECH-1.webp',NULL,4,3,10),(31,'2023-05-21 13:22:50',0,'Cảm biến quang AM10 mạnh mẽ nhận diện tốt trên mọi bề mặt, 8 nút tuỳ chỉnh với phần mềm Logitech Gaming Software, Chip xử lý ARM 32-bit cao cấu, Cấu trúc nút bấm cao cấp hỗ trợ click chuột ở cường độ cao, Tần số quét/ tốc độ phản hồi : 1000hz/1ms',0,'MOUSE LOGITECH 2',200000,'MOUSE-LOGITECH-2.webp',NULL,4,3,10),(32,'2023-05-21 13:22:50',0,'Kiểu kết nối: Có dây, Cảm biến: HERO, Độ phân giải: 16000 DPI, Tốc độ phản hồi: 1000 Hz (1ms), Màu sắc: Đen',0,'MOUSE LOGITECH 3',400000,'MOUSE-LOGITECH-3.webp',NULL,4,3,10),(33,'2023-05-21 13:22:50',0,'Kết nối USB 2.0, Kiểu cầm Ergonomic / Công thái học, Độ phân giải (CPI/DPI) 25600DPI, Tên cảm biến HERO, Thời gian phản hồi 1 ms, Số nút bấm 11',0,'MOUSE LOGITECH 4',800000,'MOUSE-LOGITECH-4.webp',NULL,4,3,10),(34,'2023-05-21 13:22:50',0,'Phiên bản đặc biệt League Of Legend Series, Cảm biến HERO 25k mang đến độ chính xác hoàn hảo, Kết nối không dây lightspeed, tối ưu độ trễ, mang lại cảm giác ổn định, Trọng lượng siêu nhẹ với 80g, 4-8 nút có thể lập trình tùy chỉnh, Led Lightsync RGB có thể lập trình với app G HUB',0,'MOUSE LOGITECH 5',1600000,'MOUSE-LOGITECH-5.webp',NULL,4,3,10),(35,'2023-05-21 13:22:50',0,'Bàn phím cơ, Kết nối: USB 2.0, Switch: GX Linear, Phím chức năng: Có',0,'KEYBOARD LOGITECH 1',100000,'KEYBOARD-LOGITECH-1.webp',NULL,4,2,10),(36,'2023-05-21 13:22:50',0,'Bàn phím ghép nối đa thiết bị, Bố cục tối giản, tiết kiệm không gian, Phím thiết kế lõm nhẹ tạo cảm giác mượt mà thoải mái khi sử dụng, Thiết kế nhỏ gọn nhẹ, dễ dàng mang theo, Có nút xoay và phím chuyển đổi, Kết nối không dây, tuổi thọ pin cao lên đến 24 tháng, Kháng nước cơ bản giúp tránh được những sự cố nhỏ về đổ tràn',0,'KEYBOARD LOGITECH 2',200000,'KEYBOARD-LOGITECH-2.webp',NULL,4,2,10),(37,'2023-05-21 13:22:50',0,'Bà̀n phím giả cơ, Kết nối USB 2.0, Kiểu switch Rubber Dome',0,'KEYBOARD LOGITECH 3',400000,'KEYBOARD-LOGITECH-3.webp',NULL,4,2,10),(38,'2023-05-21 13:22:50',0,'Phiên bản đặc biệt League Of Legend Series, Switch Gx Tactile tiên tiến, tăng hiệu suất và độ bền, Layout TKL chuẩn mực tiện lợi, cáp có thể tháo rời, Lightsync RGB tùy chỉnh Led tự do với phần mềm HUB G, Chân nâng 3 góc độ có feet cao su , 12 Macro phím F có thể lập trình',0,'KEYBOARD LOGITECH 4',800000,'KEYBOARD-LOGITECH-4.webp',NULL,4,2,10),(39,'2023-05-21 13:22:50',0,'Bàn phím thường, Kết nối 2.4 GHz Wireless',0,'KEYBOARD LOGITECH 5',1600000,'KEYBOARD-LOGITECH-5.webp',NULL,4,2,10),(40,'2023-05-21 13:22:50',0,'Hỗ trợ tốc độ truyền dữ liệu lên đến 480Mbps, Tương thích 2.0 / 1.1,  28 / 24AWG, Oxygen-Free Copper, Ba che chắn để tăng cường chống nhiễu',0,'USB 1',100000,'USB-1.webp',NULL,4,4,10),(51,'2023-06-26 22:25:34',NULL,'a1',1,'a1',1,'No-Image-Found.png','2023-06-26 22:25:48',1,1,1);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `fullname` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `email` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `hash_password` varchar(256) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `sex` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `birthday` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `address` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `img` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `created_at` varchar(33) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `verification_code` varchar(64) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reset_password_token` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `auth_type` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `state_user` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `token_expire_at` bigint DEFAULT NULL,
  `register_token` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `register_token_expire_at` bigint DEFAULT NULL,
  `reset_password_token_expire_at` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,1,'Quản trị viên','admin@gmail.com','$2a$12$xhxO.fh2WHse3u8V86K6De/R9g3n9CSa4vjpHTh4nckvsA0CdFFSW','FEMALE','1999-01-01','HCM','No-Image-Found.png','2023-02-04 00:00:00','2023-06-15 11:10:04',NULL,NULL,'DATABASE','ACTIVED',NULL,NULL,NULL,NULL),(2,2,'Người dùng','user@gmail.com','$2a$12$xhxO.fh2WHse3u8V86K6De/R9g3n9CSa4vjpHTh4nckvsA0CdFFSW','MALE','2023-02-04 12:37:14','hn','04abc994bd6b40ed9a148f56b37d68a6.jpg','2023-02-04 12:37:14','2023-06-14 16:00:42',NULL,NULL,'DATABASE','ACTIVED',NULL,NULL,NULL,NULL),(12,2,'Thien Tran','tranthien4649@gmail.com',NULL,'MALE','2023-02-04 12:37:14','hcm','No-Image-Found.png','2023-05-27 15:40:36.715','2023-06-26 20:42:52',NULL,NULL,'GOOGLE','ACTIVED',NULL,NULL,NULL,NULL),(13,2,'a','a@gmail.com','$2a$10$GLkzMUGzyIGEj1W5bO.F6OrGSxwaku8KifiqgAUlMWw1b97GK5W7G','MALE','a','a','04abc994bd6b40ed9a148f56b37d68a6.jpg','2023-05-31 21:13:03.565','2023-06-01 13:24:16',NULL,NULL,'DATABASE','ACTIVED',1686236898880,NULL,NULL,NULL),(22,2,'b','b@gmail.com','$2a$10$SgDA22vIIuSAffaQx..S/Oh47aFAE0tULEY0ZVv/Y0hMA/8yPQRju','MALE','','','y-nghia-hoa-anh-tuc-1-1277x800.jpg','2023-06-14 16:02:01.902','2023-06-14 16:03:00',NULL,NULL,'DATABASE','DISABLED',NULL,NULL,NULL,NULL),(24,2,'c','c@gmail.com','$2a$10$7tO8hX6iiLKqwXWzPOP9C.nP62z2DrOrnXpLvNajOqTIDCRsnYVLW','FEMALE','','hn','No-Image-Found.png','2023-06-26 21:22:10.148','2023-06-26 21:23:37',NULL,NULL,'DATABASE','ACTIVED',NULL,'3a81571d-8acd-403a-af8e-30e1b79e31e4',1687789630006,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-28 19:41:08

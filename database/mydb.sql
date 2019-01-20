-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 21, 2017 at 08:36 AM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mydb`
--
CREATE DATABASE IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `mydb`;
-- --------------------------------------------------------
use mydb;

--
-- Table structure for table `card_type`
--


CREATE TABLE `card_type` (
  `id` int(11) NOT NULL,
  `name` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `card_type`
--

INSERT INTO `card_type` (`id`, `name`) VALUES
(1, 'Full Card'),
(2, 'Half Card'),
(3, 'Free Card');

-- --------------------------------------------------------

--
-- Table structure for table `class_calendar`
--

CREATE TABLE `class_calendar` (
  `id` int(11) NOT NULL,
  `date` varchar(15) NOT NULL,
  `course_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `class_calendar`
--

INSERT INTO `class_calendar` (`id`, `date`, `course_id`) VALUES
(6, '2017-08-20', 2),
(9, '2017-08-20', 1),
(10, '2017-08-21', 2),
(11, '2017-08-21', 4);

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `start_date` varchar(15) DEFAULT NULL,
  `course_fee` double NOT NULL,
  `institution_id` int(11) NOT NULL,
  `delete_status` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`id`, `name`, `start_date`, `course_fee`, `institution_id`, `delete_status`) VALUES
(1, '2019 REVISION', '2016-10-03', 1000, 2, 1),
(2, '2019 REVISION', '2016-10-03', 2000, 3, 1),
(3, '2018 REVISION', '2017-08-21', 2000, 1, 0),
(4, '2018 REVISION ENGLISH', '2017-08-21', 2500, 1, 0),
(5, '2019 THEORY ENGLISH', '2017-08-21', 3000, 1, 0),
(6, '2019 THEORY SINHALA', '2017-08-21', 1500, 1, 0),
(7, '2019 THEORY SINHALA', '2017-08-21', 1500, 2, 0),
(8, '2019 THEORY SINHALA', '2017-08-21', 1000, 5, 0),
(9, '2018 REVISION', '2017-08-21', 1000, 6, 0),
(10, '2018 REVISION', '2017-08-21', 1000, 5, 0),
(11, '2020 REVISION SINHALA', '2017-08-21', 2000, 5, 0),
(12, '2020 REVISION ENGLISH', '2017-08-21', 2000, 5, 0),
(13, '2020 REVISION ENGLISH', '2017-08-21', 2000, 3, 0);

-- --------------------------------------------------------

--
-- Table structure for table `guardian`
--

CREATE TABLE `guardian` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `number` varchar(12) NOT NULL,
  `student_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `guardian`
--

INSERT INTO `guardian` (`id`, `name`, `number`, `student_id`) VALUES
(1, 'perera', '0775698785', 1),
(2, 'akakkakkaka', '', 2),
(3, 'edddddddddd', '', 3),
(4, 'fgcbvb', '0446889545', 4),
(5, 'aSNJANS', '', 5),
(6, 'S.M.perera', '', 6),
(7, 'Disanayaka', '', 7),
(8, 'Disanayaka', '', 8),
(9, 'W.S.Bandara', '0567898945', 9),
(10, 'W.S.Piyathilaka', '0564898945', 10),
(11, 'A.S.Gunathilaka', '0894898945', 11),
(12, 'A.S.Gunathilaka', '0894898945', 12),
(13, 'B.S.Weerathunaga', '0894898989', 13),
(14, 'J.S.Jayasuriya', '0891118989', 14),
(15, 'H.M.Herath', '', 15),
(16, 'Pathirana', '0885997845', 16),
(17, 'D.M.kusumalatha', '0823567845', 17),
(18, 'A.M.Nayanakkara', '0821237845', 18);

-- --------------------------------------------------------

--
-- Table structure for table `institution`
--

CREATE TABLE `institution` (
  `id` int(11) NOT NULL,
  `city` varchar(20) NOT NULL,
  `name` varchar(40) NOT NULL,
  `delete_status` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `institution`
--

INSERT INTO `institution` (`id`, `city`, `name`, `delete_status`) VALUES
(1, 'kurunegala', 'YMBA', 0),
(2, 'badulla', 'vision', 0),
(3, 'Nugegoda', 'Rotary', 0),
(4, 'Kegalle', 'Appex', 0),
(5, 'Thangalla', 'Marga', 0),
(6, 'borella', 'YMBA', 0);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `class_calendar_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`id`, `amount`, `class_calendar_id`, `student_id`) VALUES
(3, 1000, 9, 4),
(4, 2000, 6, 1),
(5, 2000, 6, 5),
(6, 2500, 11, 7),
(7, 1250, 11, 6);

-- --------------------------------------------------------

--
-- Table structure for table `phone_number`
--

CREATE TABLE `phone_number` (
  `id` int(11) NOT NULL,
  `telephone_number` varchar(12) DEFAULT NULL,
  `mobile_number` varchar(12) DEFAULT NULL,
  `student_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `phone_number`
--

INSERT INTO `phone_number` (`id`, `telephone_number`, `mobile_number`, `student_id`) VALUES
(1, '0372293316', '0770443148', 1),
(2, '', '0770446589', 2),
(3, '', '0770568945', 3),
(4, '0382296348', '0775993875', 4),
(5, '', '0155121224', 5),
(6, '0372293316', '0770443148', 6),
(7, '', '0448994521', 7),
(8, '', '0447994521', 8),
(9, '0123456789', '0447594521', 9),
(10, '0127856789', '0947594521', 10),
(11, '0127854889', '0912594521', 11),
(12, '0127154889', '0911594521', 12),
(13, '0127100889', '0911589521', 13),
(14, '0128900889', '0901015215', 14),
(15, '0127895889', '0901995215', 15),
(16, '0985632587', '0885997845', 16),
(17, '0905632587', '0888899784', 17),
(18, '0912342587', '0812349784', 18);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `barcode` varchar(45) NOT NULL,
  `name_with_initials` varchar(50) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `address` varchar(80) NOT NULL,
  `dob` varchar(15) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `nic` varchar(12) NOT NULL,
  `card_type_id` int(11) NOT NULL,
  `register_date` varchar(15) NOT NULL,
  `delete_status` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`id`, `barcode`, `name_with_initials`, `fullname`, `address`, `dob`, `email`, `nic`, `card_type_id`, `register_date`, `delete_status`) VALUES
(1, '100', 'A.M.N.P.Adikari', 'Adikari mudiyanselage nadun priyankara adikari', 'alahenegama,alahenegama', '1994-01-27', '', '940272645v', 1, '2017-08-20', 1),
(2, '102', 'A.M.G.H.Herath', 'askjjansnknasnkansk', 'amajnxksanjsmxs', '1994-08-20', '', '940272656v', 2, '2017-08-20', 1),
(3, '101', 'ajaja', 'sjdjadhks', 'ajajajaa', '1993-07-20', '', '940284578v', 2, '2017-08-20', 1),
(4, '103', 'dskdksahkd', 'sdknmsadmsa bdm', 'ddwjdbjsabd', '1993-10-20', '', '940685964v', 1, '2017-08-20', 1),
(5, '104', 'R.G.S.M.Gunathilaka', 'skndsakdkjabsdk smnc sc', 'mahiyangane', '1996-01-29', '', '960297845v', 1, '2017-08-20', 1),
(6, '200', 'A.M.N.P.Adikari', 'Adikari mudiyanselage nadun priyankara adikari', 'alahenegama,alahenegama', '1995-07-21', '', '940272645v', 2, '2017-08-21', 0),
(7, '201', 'D.S.Disanayake', 'Disanayaka Mudiyanselage Disanayaka', 'meegahakotuwa,kurunegala', '1997-07-21', '', '940582645v', 1, '2017-08-21', 0),
(8, '202', 'A.M.Nimaladeera', 'Nimaladeera Mudiyanselage Nimaladeera', 'penideniya,peradeniya', '1998-08-18', '', '980587845v', 1, '2017-08-21', 0),
(9, '203', 'K.M.Piyathilaka', 'Kaluarachchi Mudiyanselage Piyathilaka', 'penideniya,peradeniya,kandy', '1998-07-02', '', '980597845v', 3, '2017-08-21', 0),
(10, '204', 'K.M.Gunathilaka', 'Kaluarachchi Mudiyanselage Gunathilaka', 'helekadadakuna,amunukolapalessa', '1998-07-02', '', '989597845v', 2, '2017-08-21', 0),
(11, '205', 'S.M.D.Gunathilaka', 'Samarasena Mudiyanselage Dasun Gunathilaka', 'Ihalagama,amunukolapalessa', '1998-07-14', '', '989556845v', 3, '2017-08-21', 0),
(12, '206', 'K.M.D.Gunathilaka', 'Kamalananada Mudiyanselage Dasun Gunathilaka', 'pahalagama,Kalupane', '1998-07-15', '', '989558945v', 3, '2017-08-21', 0),
(13, '207', 'K.M.S.Weerathunga', 'Kamalananada Mudiyanselage suresh Weerathunga', 'katupotha,Kurunegala', '1997-07-15', '', '979558945v', 1, '2017-08-21', 0),
(14, '208', 'J.M.K.Jayasuriya', 'Jayasuriya Mudiyanselage Kamal Jayasuriya', 'katupotha,Kurunegala', '1997-05-15', '', '979001545v', 1, '2017-08-21', 0),
(15, '209', 'J.M.D.Kulathunga', 'Jayasuriya Mudiyanselage Darshana Kulathunga', 'katupotha,Kurunegala', '1997-05-15', '', '979004845v', 2, '2017-08-21', 0),
(16, '210', 'W.S.P.Jayasundara', 'Weerasundara Senanayakalage Piyal Jayasundara', '41,deyyanwela,kurunegala', '1996-08-21', 'jayasuriya@gmail.com', '960284578v', 1, '2017-08-21', 0),
(17, '211', 'N.M.P.Nayanakara', 'Nayanakara Mudiyanselage Piyadarshana Nayanakara', 'malkaduwawa,kurunegala', '1996-09-21', 'malin@gmail.com', '961100578v', 2, '2017-08-21', 0),
(18, '212', 'N.M.K.Nalinda', 'Nayanakara Mudiyanselage Kasun Nalinda', 'No 56 ,wilegedara,kaduwana,Dambulla', '1996-06-19', 'kasun@gmail.com', '961899568v', 1, '2017-08-21', 0);

-- --------------------------------------------------------

--
-- Table structure for table `student_has_class_calendar`
--

CREATE TABLE `student_has_class_calendar` (
  `student_id` int(11) NOT NULL,
  `class_calendar_id` int(11) NOT NULL,
  `tutes_issued` int(1) DEFAULT NULL,
  `marking_time` time(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `student_has_class_calendar`
--

INSERT INTO `student_has_class_calendar` (`student_id`, `class_calendar_id`, `tutes_issued`, `marking_time`) VALUES
(1, 6, 1, '13:52:04.000000'),
(2, 6, 1, '16:44:12.000000'),
(3, 6, 1, '16:48:56.000000'),
(4, 9, 1, '16:56:21.000000'),
(5, 6, 1, '23:52:05.000000'),
(5, 10, 1, '01:12:05.000000'),
(6, 11, 1, '12:23:44.000000'),
(7, 11, 1, '12:19:43.000000');

-- --------------------------------------------------------

--
-- Table structure for table `student_has_course`
--

CREATE TABLE `student_has_course` (
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `student_has_course`
--

INSERT INTO `student_has_course` (`student_id`, `course_id`) VALUES
(4, 1),
(5, 1),
(1, 2),
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(7, 3),
(8, 3),
(9, 3),
(10, 3),
(11, 3),
(12, 3),
(13, 3),
(14, 3),
(15, 3),
(16, 3),
(17, 3),
(18, 3),
(6, 4),
(7, 4),
(6, 5),
(16, 7),
(17, 7),
(18, 7),
(16, 10),
(17, 10),
(18, 10),
(7, 13),
(8, 13),
(9, 13);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `user_name`, `password`) VALUES
(1, 'nadun', '12345');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `card_type`
--
ALTER TABLE `card_type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Indexes for table `class_calendar`
--
ALTER TABLE `class_calendar`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idclass_calendar_UNIQUE` (`id`),
  ADD KEY `fk_class_calendar_course1_idx` (`course_id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idcourse_UNIQUE` (`id`),
  ADD KEY `fk_course_institution1_idx` (`institution_id`);

--
-- Indexes for table `guardian`
--
ALTER TABLE `guardian`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD KEY `fk_guardian_student_idx` (`student_id`);

--
-- Indexes for table `institution`
--
ALTER TABLE `institution`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idpayment_UNIQUE` (`id`),
  ADD KEY `fk_payment_class_calendar1_idx` (`class_calendar_id`),
  ADD KEY `fk_payment_student1_idx` (`student_id`);

--
-- Indexes for table `phone_number`
--
ALTER TABLE `phone_number`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idphone_number_UNIQUE` (`id`),
  ADD KEY `fk_phone_number_student1_idx` (`student_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `barcode_UNIQUE` (`barcode`),
  ADD UNIQUE KEY `idStudent_UNIQUE` (`id`),
  ADD KEY `fk_student_card_type1_idx` (`card_type_id`);

--
-- Indexes for table `student_has_class_calendar`
--
ALTER TABLE `student_has_class_calendar`
  ADD PRIMARY KEY (`student_id`,`class_calendar_id`),
  ADD KEY `fk_student_has_class_calendar_class_calendar1_idx` (`class_calendar_id`);

--
-- Indexes for table `student_has_course`
--
ALTER TABLE `student_has_course`
  ADD PRIMARY KEY (`student_id`,`course_id`),
  ADD KEY `fk_student_has_course_course1_idx` (`course_id`),
  ADD KEY `fk_student_has_course_student1_idx` (`student_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `card_type`
--
ALTER TABLE `card_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `class_calendar`
--
ALTER TABLE `class_calendar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `guardian`
--
ALTER TABLE `guardian`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `institution`
--
ALTER TABLE `institution`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `phone_number`
--
ALTER TABLE `phone_number`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `class_calendar`
--
ALTER TABLE `class_calendar`
  ADD CONSTRAINT `fk_class_calendar_course1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `fk_course_institution1` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `guardian`
--
ALTER TABLE `guardian`
  ADD CONSTRAINT `fk_guardian_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `fk_payment_class_calendar1` FOREIGN KEY (`class_calendar_id`) REFERENCES `class_calendar` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_payment_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `phone_number`
--
ALTER TABLE `phone_number`
  ADD CONSTRAINT `fk_phone_number_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `fk_student_card_type1` FOREIGN KEY (`card_type_id`) REFERENCES `card_type` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `student_has_class_calendar`
--
ALTER TABLE `student_has_class_calendar`
  ADD CONSTRAINT `fk_student_has_class_calendar_class_calendar1` FOREIGN KEY (`class_calendar_id`) REFERENCES `class_calendar` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_student_has_class_calendar_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `student_has_course`
--
ALTER TABLE `student_has_course`
  ADD CONSTRAINT `fk_student_has_course_course1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_student_has_course_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

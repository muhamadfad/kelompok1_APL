DROP TABLE IF EXISTS `Batch`;
CREATE TABLE `Batch` (
  `batchId` varchar(10) NOT NULL,
  `batchDes` varchar(50) DEFAULT NULL,
  `batchExpireDate` date DEFAULT NULL,
  `batchStatus` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`batchId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Doctor`;
CREATE TABLE `Doctor` (
  `doctorId` varchar(10) NOT NULL,
  `doctorNic` varchar(10) NOT NULL,
  `doctorName` varchar(50) DEFAULT NULL,
  `doctorAddress` varchar(50) DEFAULT NULL,
  `doctorMobile` varchar(10) DEFAULT NULL,
  `doctorLand` varchar(10) DEFAULT NULL,
  `doctorEmail` varchar(50) DEFAULT NULL,
  `doctorSpecialization` varchar(50) DEFAULT NULL,
  `doctorRegHospital` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`doctorId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Patient`;
CREATE TABLE `Patient` (
  `patientId` varchar(10) NOT NULL,
  `patientNic` varchar(10) NOT NULL,
  `patientName` varchar(50) DEFAULT NULL,
  `patientAddress` varchar(50) DEFAULT NULL,
  `patientMobile` varchar(10) DEFAULT NULL,
  `patientLand` varchar(10) DEFAULT NULL,
  `patientEmail` varchar(50) DEFAULT NULL,
  `patientGender` varchar(10) DEFAULT NULL,
  `patientDOB` date DEFAULT NULL,
  `patientAge` int(11) DEFAULT NULL,
  PRIMARY KEY (`patientId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Supplier`;
CREATE TABLE `Supplier` (
  `supplierId` varchar(10) NOT NULL,
  `supplierName` varchar(50) DEFAULT NULL,
  `supplierAddress` varchar(50) DEFAULT NULL,
  `supplierMobile` varchar(10) DEFAULT NULL,
  `supplierLand` varchar(10) DEFAULT NULL,
  `supplierEmail` varchar(50) DEFAULT NULL,
  `supplierVehicleNumber` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`supplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Location`;
CREATE TABLE `Location` (
  `locationId` varchar(10) NOT NULL,
  `locationDes` varchar(50) DEFAULT NULL,
  `locationStatus` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`locationId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `DrugItem`;
CREATE TABLE `DrugItem` (
  `drugCode` varchar(10) NOT NULL,
  `drugDes` varchar(50) DEFAULT NULL,
  `drugManufactureDate` date DEFAULT NULL,
  `drugExpireDate` date DEFAULT NULL,
  `drugQtyOnHand` int(11) DEFAULT NULL,
  `drugUnitPrice` decimal(10,2) DEFAULT NULL,
  `locationId` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`drugCode`),
  KEY `FK_drugLocation` (`locationId`),
  CONSTRAINT `FK_drugLocation` FOREIGN KEY (`locationId`) REFERENCES `Location` (`locationId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Order`;
CREATE TABLE `Order` (
  `orderId` varchar(10) NOT NULL,
  `orderDate` date DEFAULT NULL,
  `supplierId` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  KEY `FK_supplierId` (`supplierId`),
  CONSTRAINT `FK_supplierId` FOREIGN KEY (`supplierId`) REFERENCES `Supplier` (`supplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `OrderDetails`;
CREATE TABLE `OrderDetails` (
  `orderId` varchar(10) NOT NULL,
  `drugCode` varchar(10) NOT NULL,
  `quantity` int(5) DEFAULT NULL,
  `unitPrice` decimal(10,2) DEFAULT NULL,
  KEY `FK_orderId` (`orderId`),
  KEY `FK_orderDrugCode` (`drugCode`),
  CONSTRAINT `FK_orderDrugCode` FOREIGN KEY (`drugCode`) REFERENCES `DrugItem` (`drugCode`),
  CONSTRAINT `FK_orderId` FOREIGN KEY (`orderId`) REFERENCES `Order` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Grn`;
CREATE TABLE `Grn` (
  `grnId` varchar(10) NOT NULL,
  `orderId` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`grnId`),
  KEY `FK_grnOrderId` (`orderId`),
  CONSTRAINT `FK_grnOrderId` FOREIGN KEY (`orderId`) REFERENCES `Order` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `GrnDetails`;
CREATE TABLE `GrnDetails` (
  `grnId` varchar(10) DEFAULT NULL,
  `drugCode` varchar(10) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `unitPrice` decimal(10,2) DEFAULT NULL,
  KEY `PK_grnId` (`grnId`),
  KEY `PK_grnDrugCode` (`drugCode`),
  CONSTRAINT `PK_grnDrugCode` FOREIGN KEY (`drugCode`) REFERENCES `DrugItem` (`drugCode`),
  CONSTRAINT `PK_grnId` FOREIGN KEY (`grnId`) REFERENCES `Grn` (`grnId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Prescription`;
CREATE TABLE `Prescription` (
  `prescriptionId` varchar(10) NOT NULL,
  `prescriptionDate` date DEFAULT NULL,
  `doctorId` varchar(10) DEFAULT NULL,
  `patientId` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`prescriptionId`),
  KEY `FK_patientId` (`patientId`),
  KEY `FK_doctorId` (`doctorId`),
  CONSTRAINT `FK_doctorId` FOREIGN KEY (`doctorId`) REFERENCES `Doctor` (`doctorId`),
  CONSTRAINT `FK_patientId` FOREIGN KEY (`patientId`) REFERENCES `Patient` (`patientId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `PrescriptionDetails`;
CREATE TABLE `PrescriptionDetails` (
  `prescriptionId` varchar(10) NOT NULL,
  `drugCode` varchar(10) NOT NULL,
  `quantity` int(5) DEFAULT NULL,
  `unitPrice` decimal(10,2) DEFAULT NULL,
  KEY `FK_prescriptionId` (`prescriptionId`),
  KEY `FK_drugCode` (`drugCode`),
  CONSTRAINT `FK_drugCode` FOREIGN KEY (`drugCode`) REFERENCES `DrugItem` (`drugCode`),
  CONSTRAINT `FK_prescriptionId` FOREIGN KEY (`prescriptionId`) REFERENCES `Prescription` (`prescriptionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Stock`;
CREATE TABLE `Stock` (
  `batchId` varchar(10) NOT NULL,
  `drugCode` varchar(10) NOT NULL,
  `drugQty` int(11) DEFAULT NULL,
  `drugUnitPrice` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`batchId`,`drugCode`),
  KEY `FK_stockDrugCode` (`drugCode`),
  CONSTRAINT `FK_stockBatchId` FOREIGN KEY (`batchId`) REFERENCES `Batch` (`batchId`),
  CONSTRAINT `FK_stockDrugCode` FOREIGN KEY (`drugCode`) REFERENCES `DrugItem` (`drugCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Payment`;
CREATE TABLE `Payment` (
  `paymentId` varchar(10) NOT NULL,
  `paymentDate` date DEFAULT NULL,
  `paymentType` varchar(10) DEFAULT NULL,
  `prescriptionId` varchar(10) DEFAULT NULL,
  `orderId` varchar(10) DEFAULT NULL,
  `totalAmount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`paymentId`),
  KEY `FK_payPrescriptionId` (`prescriptionId`),
  KEY `FK_payOrderIdId` (`orderId`),
  CONSTRAINT `FK_payOrderIdId` FOREIGN KEY (`orderId`) REFERENCES `Order` (`orderId`),
  CONSTRAINT `FK_payPrescriptionId` FOREIGN KEY (`prescriptionId`) REFERENCES `Prescription` (`prescriptionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `Bill`;
CREATE TABLE `Bill` (
  `billId` int(11) NOT NULL AUTO_INCREMENT,
  `paymentId` varchar(10) DEFAULT NULL,
  `amountReceived` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`billId`),
  KEY `FK_paymentId` (`paymentId`),
  CONSTRAINT `FK_paymentId` FOREIGN KEY (`paymentId`) REFERENCES `Payment` (`paymentId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `UserRole`;
CREATE TABLE `UserRole` (
  `usrRoleId` varchar(10) NOT NULL,
  `roleName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`usrRoleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `UserRole` WRITE;
INSERT INTO `UserRole` VALUES ('ADMIN','Admin'),('MANAGER','Manager'),('PHARMACIST','Pharmacist');
UNLOCK TABLES;

DROP TABLE IF EXISTS `PharmacyUser`;
CREATE TABLE `PharmacyUser` (
  `userId` varchar(10) NOT NULL,
  `userFullName` varchar(50) DEFAULT NULL,
  `userNic` varchar(10) DEFAULT NULL,
  `userRole` varchar(10) DEFAULT NULL,
  `userEmail` varchar(50) DEFAULT NULL,
  `userName` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userId`,`userName`),
  KEY `FK_userRole` (`userRole`),
  CONSTRAINT `FK_userRole` FOREIGN KEY (`userRole`) REFERENCES `UserRole` (`usrRoleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;











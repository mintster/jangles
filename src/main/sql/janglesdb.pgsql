/*
Navicat PGSQL Data Transfer

Source Server         : postgresql
Source Server Version : 90306
Source Host           : localhost:5432
Source Database       : janglesdb
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90306
File Encoding         : 65001

Date: 2015-03-05 13:26:14
*/


-- ----------------------------
-- Table structure for jangles_users
-- ----------------------------
DROP TABLE IF EXISTS "public"."jangles_users";
CREATE TABLE "public"."jangles_users" (
"userid" int8 NOT NULL,
"firstName" varchar(50) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jangles_users
-- ----------------------------
INSERT INTO "public"."jangles_users" VALUES ('1', 'pgBill');
INSERT INTO "public"."jangles_users" VALUES ('2', 'pgBob');

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table jangles_users
-- ----------------------------
ALTER TABLE "public"."jangles_users" ADD PRIMARY KEY ("userid");

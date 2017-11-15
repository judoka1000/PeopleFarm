/*
-- Query: SELECT * FROM persondb.Button
LIMIT 0, 1000

-- Date: 2017-11-15 14:12
*/
INSERT INTO `Button` (`buttonID`,`bonusIQ`,`bonusIQDuration`,`bonusMetabolism`,`bonusMetabolismDuration`,`bonusSpeed`,`bonusSpeedDuration`,`bonusStamina`,`bonusStaminaDuration`,`buyCost`,`clickAction`,`hunger`,`image`,`name`,`tiredness`,`useCost`) VALUES (1,0,0,0,0,0,0,0,0,5,'eatingHamburger',80,'hamburger.png','Hamburger',0,3);
INSERT INTO `Button` (`buttonID`,`bonusIQ`,`bonusIQDuration`,`bonusMetabolism`,`bonusMetabolismDuration`,`bonusSpeed`,`bonusSpeedDuration`,`bonusStamina`,`bonusStaminaDuration`,`buyCost`,`clickAction`,`hunger`,`image`,`name`,`tiredness`,`useCost`) VALUES (2,-10,3,0,0,0,0,0,0,5,'eatingDogfood',50,'dogfood.jpg','Dogfood',0,1);
INSERT INTO `Button` (`buttonID`,`bonusIQ`,`bonusIQDuration`,`bonusMetabolism`,`bonusMetabolismDuration`,`bonusSpeed`,`bonusSpeedDuration`,`bonusStamina`,`bonusStaminaDuration`,`buyCost`,`clickAction`,`hunger`,`image`,`name`,`tiredness`,`useCost`) VALUES (3,0,0,0,0,0,0,0,0,0,'sleep',0,'sleep.png','Sleep',100,0);
INSERT INTO `Button` (`buttonID`,`bonusIQ`,`bonusIQDuration`,`bonusMetabolism`,`bonusMetabolismDuration`,`bonusSpeed`,`bonusSpeedDuration`,`bonusStamina`,`bonusStaminaDuration`,`buyCost`,`clickAction`,`hunger`,`image`,`name`,`tiredness`,`useCost`) VALUES (4,0,0,0,0,0,0,0,0,50,'reproduce',-20,'heart.png','Reproduce',-20,0);
INSERT INTO `Button` (`buttonID`,`bonusIQ`,`bonusIQDuration`,`bonusMetabolism`,`bonusMetabolismDuration`,`bonusSpeed`,`bonusSpeedDuration`,`bonusStamina`,`bonusStaminaDuration`,`buyCost`,`clickAction`,`hunger`,`image`,`name`,`tiredness`,`useCost`) VALUES (5,0,0,0,0,0,0,0,0,0,'collect',0,'collect.png','Collect',0,0);
INSERT INTO `Button` (`buttonID`,`bonusIQ`,`bonusIQDuration`,`bonusMetabolism`,`bonusMetabolismDuration`,`bonusSpeed`,`bonusSpeedDuration`,`bonusStamina`,`bonusStaminaDuration`,`buyCost`,`clickAction`,`hunger`,`image`,`name`,`tiredness`,`useCost`) VALUES (6,0,0,0,0,0,0,0,0,0,'info',0,'questionmark.png','Info',0,0);
INSERT INTO `Button` (`buttonID`,`bonusIQ`,`bonusIQDuration`,`bonusMetabolism`,`bonusMetabolismDuration`,`bonusSpeed`,`bonusSpeedDuration`,`bonusStamina`,`bonusStaminaDuration`,`buyCost`,`clickAction`,`hunger`,`image`,`name`,`tiredness`,`useCost`) VALUES (7,0,0,0,0,0,0,0,0,0,'compare',0,'compare.png','Compare',0,0);
INSERT INTO `Button` (`buttonID`,`bonusIQ`,`bonusIQDuration`,`bonusMetabolism`,`bonusMetabolismDuration`,`bonusSpeed`,`bonusSpeedDuration`,`bonusStamina`,`bonusStaminaDuration`,`buyCost`,`clickAction`,`hunger`,`image`,`name`,`tiredness`,`useCost`) VALUES (8,0,0,0,0,0,0,0,0,0,'none',0,'pointercursor.png','None',0,0);
INSERT INTO `Button` (`buttonID`,`bonusIQ`,`bonusIQDuration`,`bonusMetabolism`,`bonusMetabolismDuration`,`bonusSpeed`,`bonusSpeedDuration`,`bonusStamina`,`bonusStaminaDuration`,`buyCost`,`clickAction`,`hunger`,`image`,`name`,`tiredness`,`useCost`) VALUES (9,0,0,0,0,10,3,10,3,80,'eatingRedbull',0,'redbull.png','Redbull',0,10);
INSERT INTO `Button` (`buttonID`,`bonusIQ`,`bonusIQDuration`,`bonusMetabolism`,`bonusMetabolismDuration`,`bonusSpeed`,`bonusSpeedDuration`,`bonusStamina`,`bonusStaminaDuration`,`buyCost`,`clickAction`,`hunger`,`image`,`name`,`tiredness`,`useCost`) VALUES (10,0,0,0,0,0,0,0,0,0,'kill',-20,'ax.png','Kill',0,0);

INSERT INTO `account` (`id`, `password`, `username`) VALUES (1, '1', '1');

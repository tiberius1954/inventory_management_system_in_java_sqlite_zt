BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "banks" (
	"bid"	INTEGER,
	"mark"	TEXT DEFAULT ' ',
	"code"	TEXT DEFAULT ' ',
	"bname"	TEXT DEFAULT ' ',
	"address"	TEXT DEFAULT ' ',
	"iban"	TEXT DEFAULT ' ',
	"bankaccountnumber"	TEXT DEFAULT ' ',
	PRIMARY KEY("bid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "banktransactions" (
	"btid"	INTEGER,
	"bid"	INTEGER DEFAULT 0,
	"mark"	TEXT,
	"code"	TEXT,
	"indb"	INTEGER DEFAULT 0,
	"register"	INTEGER DEFAULT 0,
	"btnumber"	TEXT DEFAULT ' ',
	"bdate"	TEXT DEFAULT ' ',
	"btype"	TEXT DEFAULT ' ',
	"claim"	TEXT DEFAULT ' ',
	"parid"	INTEGER DEFAULT 0,
	"receipt"	TEXT DEFAULT ' ',
	"referenc"	TEXT DEFAULT ' ',
	"amount"	NUMERIC DEFAULT 0,
	"description"	INTEGER DEFAULT ' ',
	"account_no"	INTEGER DEFAULT ' ',
	"tid"	INTEGER DEFAULT 0,
	PRIMARY KEY("btid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "category" (
	"cid"	INTEGER,
	"cname"	TEXT,
	PRIMARY KEY("cid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "cities" (
	"cid"	INTEGER,
	"name"	TEXT,
	PRIMARY KEY("cid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "countries" (
	"coid"	TEXT NOT NULL,
	"countryname"	TEXT NOT NULL,
	"code"	TEXT DEFAULT NULL,
	PRIMARY KEY("coid")
);
CREATE TABLE IF NOT EXISTS "inventory" (
	"tsid"	INTEGER,
	"pid"	INTEGER DEFAULT 0,
	"inqty"	NUMERIC DEFAULT 0.0,
	"outqty"	NUMERIC DEFAULT 0.0,
	"stock"	NUMERIC DEFAULT 0.0,
	"uid"	INTEGER DEFAULT 0,
	"pmin"	NUMERIC DEFAULT 0.0,
	"pmax"	NUMERIC DEFAULT 0.0,
	"last_updated"	TEXT DEFAULT ' ',
	"costprice"	NUMERIC DEFAULT 0.0,
	PRIMARY KEY("tsid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "params" (
	"parid"	INTEGER,
	"name"	TEXT DEFAULT ' ',
	"phone"	TEXT DEFAULT ' ',
	"email"	TEXT DEFAULT ' ',
	"country"	TEXT DEFAULT ' ',
	"City"	TEXT DEFAULT ' ',
	"address"	TEXT DEFAULT ' ',
	"postcode"	TEXT DEFAULT ' ',
	"currentyear"	INTEGER,
	"inprefix"	TEXT DEFAULT ' ',
	"innumber"	INTEGER DEFAULT 0,
	"outprefix"	TEXT DEFAULT ' ',
	"outnumber"	INTEGER DEFAULT 0,
	"bankaccount"	TEXT DEFAULT ' ',
	PRIMARY KEY("parid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "partner" (
	"parid"	INTEGER,
	"name"	TEXT DEFAULT ' ',
	"gender"	TEXT DEFAULT ' ',
	"dob"	TEXT DEFAULT ' ',
	"phone"	TEXT DEFAULT ' ',
	"email"	TEXT DEFAULT ' ',
	"regdate"	TEXT DEFAULT ' ',
	"photo"	BLOB DEFAULT ' ',
	"country"	TEXT DEFAULT ' ',
	"cid"	INTEGER DEFAULT 0,
	"address"	TEXT DEFAULT ' ',
	"postcode"	TEXT DEFAULT ' ',
	"role"	TEXT DEFAULT ' ',
	"remark"	TEXT DEFAULT ' ',
	"bankaccount"	TEXT DEFAULT ' ',
	PRIMARY KEY("parid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "product" (
	"pid"	INTEGER,
	"productcode"	TEXT,
	"pname"	TEXT,
	"uid"	INTEGER DEFAULT 0,
	"scid"	INTEGER DEFAULT 0,
	"description"	TEXT,
	"costprice"	NUMERIC DEFAULT 0,
	"sellprice"	NUMERIC DEFAULT 0,
	"discountp"	NUMERIC DEFAULT 0,
	"vatp"	NUMERIC DEFAULT 0,
	"openstock"	NUMERIC DEFAULT 0,
	"pmin"	NUMERIC DEFAULT 0,
	"pmax"	NUMERIC DEFAULT 0,
	"pic"	BLOB,
	PRIMARY KEY("pid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS sqlite_stat4(tbl,idx,neq,nlt,ndlt,sample);
CREATE TABLE IF NOT EXISTS "stransaction" (
	"tid"	INTEGER,
	"tnumber"	TEXT DEFAULT ' ',
	"incomeno"	TEXT DEFAULT ' ',
	"transaction_type"	INTEGER,
	"tdate"	TEXT DEFAULT ' ',
	"ptype"	TEXT,
	"parid"	INTEGER,
	"subtotal"	NUMERIC DEFAULT 0,
	"vat"	NUMERIC DEFAULT 0,
	"grandtotal"	NUMERIC DEFAULT 0,
	"totalpaid"	NUMERIC DEFAULT 0,
	"balance"	NUMERIC DEFAULT 0,
	"discount"	NUMERIC DEFAULT 0,
	"remark"	TEXT DEFAULT ' ',
	PRIMARY KEY("tid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "stransaction_detail" (
	"dtid"	INTEGER,
	"tid"	INTEGER DEFAULT 0,
	"pid"	INTEGER DEFAULT 0,
	"transaction_type"	INTEGER DEFAULT 0,
	"qty"	NUMERIC DEFAULT 0,
	"uid"	INTEGER DEFAULT 0,
	"price"	NUMERIC DEFAULT 0,
	"discountprice"	NUMERIC DEFAULT 0,
	"amount"	NUMERIC DEFAULT 0,
	"vatp"	NUMERIC DEFAULT 0,
	"vat"	NUMERIC DEFAULT 0,
	"discountp"	NUMERIC DEFAULT 0,
	"discount"	NUMERIC DEFAULT 0,
	"totalamount"	NUMERIC DEFAULT 0,
	"tdate"	TEXT DEFAULT ' ',
	PRIMARY KEY("dtid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "subcategory" (
	"scid"	INTEGER,
	"sname"	TEXT,
	"cid"	INTEGER DEFAULT 0,
	PRIMARY KEY("scid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "unities" (
	"uid"	INTEGER,
	"uname"	TEXT DEFAULT ' ',
	PRIMARY KEY("uid" AUTOINCREMENT)
);
INSERT INTO "banks" ("bid","mark","code","bname","address","iban","bankaccountnumber") VALUES (1,'B','01','National Bank','Budapest Roosevelt square 10.','11111 222222 555555','12345678-54323333-56666655');
INSERT INTO "banktransactions" ("btid","bid","mark","code","indb","register","btnumber","bdate","btype","claim","parid","receipt","referenc","amount","description","account_no","tid") VALUES (22,1,'B','01',1,1,'B0125-0908/001','2025-09-08','Deposit','Customer bill payment',5,'INV-2025/00009','BI-10000',4400,'',' ',55);
INSERT INTO "category" ("cid","cname") VALUES (1,'vegtables');
INSERT INTO "category" ("cid","cname") VALUES (2,'Fruits');
INSERT INTO "cities" ("cid","name") VALUES (4,'Budapest');
INSERT INTO "cities" ("cid","name") VALUES (5,'London');
INSERT INTO "cities" ("cid","name") VALUES (9,'Paris');
INSERT INTO "cities" ("cid","name") VALUES (13,'San Jose CA');
INSERT INTO "cities" ("cid","name") VALUES (14,'Vienna');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AFG','Afghanistan','AF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ALA','Åland','AX');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ALB','Albania','AL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('DZA','Algeria','DZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ASM','American Samoa','AS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AND','Andorra','AD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AGO','Angola','AO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AIA','Anguilla','AI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ATA','Antarctica','AQ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ATG','Antigua and Barbuda','AG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ARG','Argentina','AR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ARM','Armenia','AM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ABW','Aruba','AW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AUS','Australia','AU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AUT','Austria','AT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('AZE','Azerbaijan','AZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BHS','Bahamas','BS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BHR','Bahrain','BH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BGD','Bangladesh','BD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BRB','Barbados','BB');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BLR','Belarus','BY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BEL','Belgium','BE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BLZ','Belize','BZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BEN','Benin','BJ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BMU','Bermuda','BM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BTN','Bhutan','BT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BOL','Bolivia','BO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BES','Bonaire','BQ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BIH','Bosnia and Herzegovina','BA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BWA','Botswana','BW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BVT','Bouvet Island','BV');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BRA','Brazil','BR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IOT','British Indian Ocean Territory','IO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VGB','British Virgin Islands','VG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BRN','Brunei','BN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BGR','Bulgaria','BG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BFA','Burkina Faso','BF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BDI','Burundi','BI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KHM','Cambodia','KH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CMR','Cameroon','CM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CAN','Canada','CA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CPV','Cape Verde','CV');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CYM','Cayman Islands','KY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CAF','Central African Republic','CF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TCD','Chad','TD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CHL','Chile','CL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CHN','China','CN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CXR','Christmas Island','CX');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CCK','Cocos [Keeling] Islands','CC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('COL','Colombia','CO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('COM','Comoros','KM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('COK','Cook Islands','CK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CRI','Costa Rica','CR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('HRV','Croatia','HR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CUB','Cuba','CU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CUW','Curacao','CW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CYP','Cyprus','CY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CZE','Czech Republic','CZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('COD','Democratic Republic of the Congo','CD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('DNK','Denmark','DK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('DJI','Djibouti','DJ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('DMA','Dominica','DM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('DOM','Dominican Republic','DO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TLS','East Timor','TL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ECU','Ecuador','EC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('EGY','Egypt','EG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SLV','El Salvador','SV');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GNQ','Equatorial Guinea','GQ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ERI','Eritrea','ER');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('EST','Estonia','EE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ETH','Ethiopia','ET');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('FLK','Falkland Islands','FK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('FRO','Faroe Islands','FO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('FJI','Fiji','FJ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('FIN','Finland','FI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('FRA','France','FR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GUF','French Guiana','GF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PYF','French Polynesia','PF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ATF','French Southern Territories','TF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GAB','Gabon','GA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GMB','Gambia','GM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GEO','Georgia','GE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('DEU','Germany','DE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GHA','Ghana','GH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GIB','Gibraltar','GI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GRC','Greece','GR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GRL','Greenland','GL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GRD','Grenada','GD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GLP','Guadeloupe','GP');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GUM','Guam','GU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GTM','Guatemala','GT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GGY','Guernsey','GG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GIN','Guinea','GN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GNB','Guinea-Bissau','GW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GUY','Guyana','GY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('HTI','Haiti','HT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('HMD','Heard Island and McDonald Islands','HM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('HND','Honduras','HN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('HKG','Hong Kong','HK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('HUN','Hungary','HU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ISL','Iceland','IS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IND','India','IN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IDN','Indonesia','ID');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IRN','Iran','IR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IRQ','Iraq','IQ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IRL','Ireland','IE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('IMN','Isle of Man','IM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ISR','Israel','IL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ITA','Italy','IT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CIV','Ivory Coast','CI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('JAM','Jamaica','JM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('JPN','Japan','JP');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('JEY','Jersey','JE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('JOR','Jordan','JO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KAZ','Kazakhstan','KZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KEN','Kenya','KE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KIR','Kiribati','KI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('XKX','Kosovo','XK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KWT','Kuwait','KW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KGZ','Kyrgyzstan','KG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LAO','Laos','LA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LVA','Latvia','LV');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LBN','Lebanon','LB');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LSO','Lesotho','LS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LBR','Liberia','LR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LBY','Libya','LY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LIE','Liechtenstein','LI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LTU','Lithuania','LT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LUX','Luxembourg','LU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MAC','Macao','MO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MKD','Macedonia','MK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MDG','Madagascar','MG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MWI','Malawi','MW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MYS','Malaysia','MY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MDV','Maldives','MV');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MLI','Mali','ML');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MLT','Malta','MT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MHL','Marshall Islands','MH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MTQ','Martinique','MQ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MRT','Mauritania','MR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MUS','Mauritius','MU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MYT','Mayotte','YT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MEX','Mexico','MX');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('FSM','Micronesia','FM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MDA','Moldova','MD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MCO','Monaco','MC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MNG','Mongolia','MN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MNE','Montenegro','ME');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MSR','Montserrat','MS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MAR','Morocco','MA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MOZ','Mozambique','MZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MMR','Myanmar [Burma]','MM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NAM','Namibia','NA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NRU','Nauru','NR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NPL','Nepal','NP');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NLD','Netherlands','NL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NCL','New Caledonia','NC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NZL','New Zealand','NZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NIC','Nicaragua','NI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NER','Niger','NE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NGA','Nigeria','NG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NIU','Niue','NU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NFK','Norfolk Island','NF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PRK','North Korea','KP');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MNP','Northern Mariana Islands','MP');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('NOR','Norway','NO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('OMN','Oman','OM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PAK','Pakistan','PK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PLW','Palau','PW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PSE','Palestine','PS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PAN','Panama','PA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PNG','Papua New Guinea','PG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PRY','Paraguay','PY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PER','Peru','PE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PHL','Philippines','PH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PCN','Pitcairn Islands','PN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('POL','Poland','PL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PRT','Portugal','PT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('PRI','Puerto Rico','PR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('QAT','Qatar','QA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('COG','Republic of the Congo','CG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('REU','Réunion','RE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ROU','Romania','RO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('RUS','Russia','RU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('RWA','Rwanda','RW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('BLM','Saint Barthélemy','BL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SHN','Saint Helena','SH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KNA','Saint Kitts and Nevis','KN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LCA','Saint Lucia','LC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('MAF','Saint Martin','MF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SPM','Saint Pierre and Miquelon','PM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VCT','Saint Vincent and the Grenadines','VC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('WSM','Samoa','WS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SMR','San Marino','SM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('STP','São Tomé and Príncipe','ST');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SAU','Saudi Arabia','SA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SEN','Senegal','SN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SRB','Serbia','RS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SYC','Seychelles','SC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SLE','Sierra Leone','SL');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SGP','Singapore','SG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SXM','Sint Maarten','SX');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SVK','Slovakia','SK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SVN','Slovenia','SI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SLB','Solomon Islands','SB');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SOM','Somalia','SO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ZAF','South Africa','ZA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SGS','South Georgia and the South Sandwich Islands','GS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('KOR','South Korea','KR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SSD','South Sudan','SS');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ESP','Spain','ES');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('LKA','Sri Lanka','LK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SDN','Sudan','SD');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SUR','Suriname','SR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SJM','Svalbard and Jan Mayen','SJ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SWZ','Swaziland','SZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SWE','Sweden','SE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('CHE','Switzerland','CH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('SYR','Syria','SY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TWN','Taiwan','TW');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TJK','Tajikistan','TJ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TZA','Tanzania','TZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('THA','Thailand','TH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TGO','Togo','TG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TKL','Tokelau','TK');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TON','Tonga','TO');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TTO','Trinidad and Tobago','TT');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TUN','Tunisia','TN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TUR','Turkey','TR');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TKM','Turkmenistan','TM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TCA','Turks and Caicos Islands','TC');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('TUV','Tuvalu','TV');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('UMI','U.S. Minor Outlying Islands','UM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VIR','U.S. Virgin Islands','VI');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('UGA','Uganda','UG');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('UKR','Ukraine','UA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ARE','United Arab Emirates','AE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('GBR','United Kingdom','GB');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('USA','United States','US');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('URY','Uruguay','UY');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('UZB','Uzbekistan','UZ');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VUT','Vanuatu','VU');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VAT','Vatican City','VA');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VEN','Venezuela','VE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('VNM','Vietnam','VN');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('WLF','Wallis and Futuna','WF');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ESH','Western Sahara','EH');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('YEM','Yemen','YE');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ZMB','Zambia','ZM');
INSERT INTO "countries" ("coid","countryname","code") VALUES ('ZWE','Zimbabwe','ZW');
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (3,11,110,5,105,14,10,200,'2025-09-02',200);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (4,12,50,0,50,14,10,200,'2025-08-31',100);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (5,13,30,0,30,14,20,50,'2025-09-01',200);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (6,14,10,0,10,14,5,20,'2025-09-01',200);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (7,15,20,0,20,15,'','','2025-09-01',200);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (8,16,20,0,20,14,10,30,'2025-09-01',200);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (9,17,15,0,15,14,10,30,'2025-09-01',200);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (10,18,40,0,40,14,20,50,'2025-09-02',200);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (11,19,20,0,20,14,10,50,'2025-09-01',300);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (12,20,25,0,25,14,10,30,'2025-09-01',100);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (13,21,20,0,20,14,10,50,'2025-09-01',50);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (14,22,20,0,20,14,10,30,'2025-09-01',50);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (15,23,15,0,15,15,10,20,'2025-09-01',50);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (16,24,15,0,15,15,10,20,'2025-09-01',50);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (17,25,5,0,5,14,10,20,'2025-09-01',100);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (18,26,15,10,5,14,10,30,'2025-09-01',100);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (19,27,5,11,-6,14,10,20,'2025-09-01',100);
INSERT INTO "inventory" ("tsid","pid","inqty","outqty","stock","uid","pmin","pmax","last_updated","costprice") VALUES (20,28,15,0,15,14,10,20,'2025-09-02',100);
INSERT INTO "params" ("parid","name","phone","email","country","City","address","postcode","currentyear","inprefix","innumber","outprefix","outnumber","bankaccount") VALUES (1,'Vector Company',' +36 1 222-4355','vector@gmail.com','Hungary','Budapest','Roosevelt street 22.',' H-6000',2025,'PUR',2,'INV',9,' 11111111-22222222');
INSERT INTO "partner" ("parid","name","gender","dob","phone","email","regdate","photo","country","cid","address","postcode","role","remark","bankaccount") VALUES (3,'Josef Heller','Male','','+36 1 243-3564','josef.heller@gmail.com','2025-01-01',' ','Hungary',4,'Margaret Thather street 10.','1200','Customer','','');
INSERT INTO "partner" ("parid","name","gender","dob","phone","email","regdate","photo","country","cid","address","postcode","role","remark","bankaccount") VALUES (4,'Christian Horner','Male','','+36 20 111-2345','christian.horner@gmail.com','2025-01-01',' ','Hungary',4,'Zsigmond Széchenyi street 20.','1100','Supplier','','');
INSERT INTO "partner" ("parid","name","gender","dob","phone","email","regdate","photo","country","cid","address","postcode","role","remark","bankaccount") VALUES (5,'Eva Mendes','Female','','+36 1 111-1233','eva.mendes@gmail.com','2025-03-12',' ','Hungary',4,'Laszló fulop street 7.','1250','User','','');
INSERT INTO "partner" ("parid","name","gender","dob","phone","email","regdate","photo","country","cid","address","postcode","role","remark","bankaccount") VALUES (6,'Robert Pattinson','Male','','+36 20 2542-334','robert.pattinson@gmail.com','2025-06-05',' ','Hungary',4,'Liberty street 70.','1345','Customer','','');
INSERT INTO "partner" ("parid","name","gender","dob","phone","email","regdate","photo","country","cid","address","postcode","role","remark","bankaccount") VALUES (7,'Alisen Down ','Female','','+36 1 234-5678','alisen.down@gmail.com','2025-06-01',' ','Hungary',4,'Laszlo kelemen street 65.','1500','Customer','','');
INSERT INTO "partner" ("parid","name","gender","dob","phone","email","regdate","photo","country","cid","address","postcode","role","remark","bankaccount") VALUES (8,'Gerard Buttler','Male','','+36 1234-4556','gerard.buttler@gmail.com','2025-04-04',' ','Hungary',4,'Mihaly Babits street 15.','1300','Customer','','');
INSERT INTO "partner" ("parid","name","gender","dob","phone","email","regdate","photo","country","cid","address","postcode","role","remark","bankaccount") VALUES (9,'Ivan Drago','Male','','+36 1 234-7788','ivan.drago@gmail.com','2025-05-05',' ','Hungary',4,'Lajos Kossuth square 10.','1600','Supplier','','');
INSERT INTO "partner" ("parid","name","gender","dob","phone","email","regdate","photo","country","cid","address","postcode","role","remark","bankaccount") VALUES (10,'Ludwig van Beethoven','Male','1770-12-16','+43 21117414','ludwig@gmail.com','2025-06-30',' ','Austria',14,'Schenkenstrasse 3.','1010','Customer','Famous','');
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (11,'A100','Red apple',14,1,'',100,200,10,20,100,10,200,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (12,'A101','Golden apples',14,1,'',100,200,'',20,50,10,200,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (13,'T100','Turkish apricot',14,12,'',200,300,'',10,30,20,50,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (14,'M100','Sweet melon',15,10,'',200,300,'',10,10,5,20,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (15,'M101','Yellow melon',15,10,'',200,400,'',10,20,'','',NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (16,'A102','Pink lady apple',14,1,'',200,300,'',10,20,10,30,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (17,'O100','Greek orange',14,6,'',200,250,'',10,15,10,30,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (18,'B100','Family banana',14,9,'',100,150,'',10,20,20,50,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (19,'B101','Big banana',14,9,'',300,400,'',10,20,10,50,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (20,'T100','Jam tomato',14,7,'',100,200,'',10,25,10,30,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (21,'P100','Sweet potato',14,8,'',50,100,'',10,20,10,50,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (22,'P101','Large potato',14,8,'',50,100,'',10,20,10,30,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (23,'C100','Green cabbage',15,14,'',50,100,'',10,15,10,20,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (24,'C101','Red cabbage',15,14,'',50,100,'',10,15,10,20,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (25,'O200','Green onion',14,16,'',100,200,'',10,5,10,20,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (26,'C100','Carrot',14,13,'',100,200,'',10,15,10,30,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (27,'G100','Garlic',14,17,'',100,200,'',10,5,10,20,NULL);
INSERT INTO "product" ("pid","productcode","pname","uid","scid","description","costprice","sellprice","discountp","vatp","openstock","pmin","pmax","pic") VALUES (28,'C150','Cucumber',14,23,'',100,200,'',10,10,10,20,NULL);
INSERT INTO "stransaction" ("tid","tnumber","incomeno","transaction_type","tdate","ptype","parid","subtotal","vat","grandtotal","totalpaid","balance","discount","remark") VALUES (53,'PUR-2025/00002','IN2025-0001',1,'2025-09-02','By cash',9,6500,400,7350,4400,0,400,'Important');
INSERT INTO "stransaction" ("tid","tnumber","incomeno","transaction_type","tdate","ptype","parid","subtotal","vat","grandtotal","totalpaid","balance","discount","remark") VALUES (54,'INV-2025/00008','',2,'2025-09-02','By cash',3,1548,19.8,1837.8,4400,0,19.8,'');
INSERT INTO "stransaction" ("tid","tnumber","incomeno","transaction_type","tdate","ptype","parid","subtotal","vat","grandtotal","totalpaid","balance","discount","remark") VALUES (55,'INV-2025/00009','BI-10000',2,'2025-09-08','By cheque',5,4000,200,4400,4400,0,200,'');
INSERT INTO "stransaction_detail" ("dtid","tid","pid","transaction_type","qty","uid","price","discountprice","amount","vatp","vat","discountp","discount","totalamount","tdate") VALUES (155,53,11,1,10,14,200,0,2000,20,400,'',0,2400,'2025-09-02');
INSERT INTO "stransaction_detail" ("dtid","tid","pid","transaction_type","qty","uid","price","discountprice","amount","vatp","vat","discountp","discount","totalamount","tdate") VALUES (156,53,28,1,5,14,100,0,500,10,50,'',0,550,'2025-09-02');
INSERT INTO "stransaction_detail" ("dtid","tid","pid","transaction_type","qty","uid","price","discountprice","amount","vatp","vat","discountp","discount","totalamount","tdate") VALUES (157,53,18,1,20,14,200,0,4000,10,400,'',0,4400,'2025-09-02');
INSERT INTO "stransaction_detail" ("dtid","tid","pid","transaction_type","qty","uid","price","discountprice","amount","vatp","vat","discountp","discount","totalamount","tdate") VALUES (158,54,11,2,5,14,300,270,1350,20,270,10,150,1620,'2025-09-02');
INSERT INTO "stransaction_detail" ("dtid","tid","pid","transaction_type","qty","uid","price","discountprice","amount","vatp","vat","discountp","discount","totalamount","tdate") VALUES (159,54,27,2,1,14,220,198,198,10,19.8,10,22,217.8,'2025-09-02');
INSERT INTO "stransaction_detail" ("dtid","tid","pid","transaction_type","qty","uid","price","discountprice","amount","vatp","vat","discountp","discount","totalamount","tdate") VALUES (160,55,27,2,10,14,200,0,2000,10,200,'',0,2200,'2025-09-08');
INSERT INTO "stransaction_detail" ("dtid","tid","pid","transaction_type","qty","uid","price","discountprice","amount","vatp","vat","discountp","discount","totalamount","tdate") VALUES (161,55,26,2,10,14,200,0,2000,10,200,'',0,2200,'2025-09-08');
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (1,'Apples',2);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (6,'Oranges',2);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (7,'Tomatoes',1);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (8,'Potatoes',1);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (9,'Bananas',2);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (10,'Melons',2);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (11,'Beans',1);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (12,'Apricots',2);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (13,'Carrots',1);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (14,'Cabbages',1);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (15,'Grapes',2);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (16,'Onions',1);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (17,'Garlics',1);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (18,'Lemons',2);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (19,'Mandarins',2);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (20,'Grapefruits',2);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (21,'Pears',2);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (22,'Spinach',1);
INSERT INTO "subcategory" ("scid","sname","cid") VALUES (23,'Cucumbers',1);
INSERT INTO "unities" ("uid","uname") VALUES (2,'Package');
INSERT INTO "unities" ("uid","uname") VALUES (3,'g');
INSERT INTO "unities" ("uid","uname") VALUES (6,'Litre');
INSERT INTO "unities" ("uid","uname") VALUES (7,'Bag');
INSERT INTO "unities" ("uid","uname") VALUES (8,'Milliliter');
INSERT INTO "unities" ("uid","uname") VALUES (9,'Glass');
INSERT INTO "unities" ("uid","uname") VALUES (10,'Pounds');
INSERT INTO "unities" ("uid","uname") VALUES (11,'Ounce');
INSERT INTO "unities" ("uid","uname") VALUES (12,'Pint');
INSERT INTO "unities" ("uid","uname") VALUES (13,'Gallon');
INSERT INTO "unities" ("uid","uname") VALUES (14,'Kg');
INSERT INTO "unities" ("uid","uname") VALUES (15,'Piece');
INSERT INTO "unities" ("uid","uname") VALUES (16,'Slice');
INSERT INTO "unities" ("uid","uname") VALUES (18,'Dag');
INSERT INTO "unities" ("uid","uname") VALUES (19,'ml');
INSERT INTO "unities" ("uid","uname") VALUES (20,'Pairs');
INSERT INTO "unities" ("uid","uname") VALUES (21,'m');
INSERT INTO "unities" ("uid","uname") VALUES (23,'cloves');
COMMIT;

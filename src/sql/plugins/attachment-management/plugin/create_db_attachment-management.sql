--
-- Table attachment_management_request
--
DROP TABLE IF EXISTS attachment_management_request;
CREATE TABLE attachment_management_request (
	id_request int AUTO_INCREMENT,
	task varchar(250) NOT NULL,
	refusal_reason long varchar,
	agent varchar(250) default '',
	date_treatment timestamp,
	PRIMARY KEY (id_demand)	
);
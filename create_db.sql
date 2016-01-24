--CREATE JDBCREALM_USER VIEW
create view jdbcrealm_user (username, password) as
select username, password
from ACCOUNT;

--CREATE JDBCREALM_GROUP VIEW
create view jdbcrealm_group (username, groupname) as
select username, groupname
from ACCOUNT;

-- Insert initial admin account and user acccount for test
INSERT INTO AIP.ACCOUNT (ID, EMAIL, FIRSTNAME, GENDER, GROUPNAME, LASTNAME, PASSWORD, PHONENUMBER, USERNAME, CARDTOKEN) 
	VALUES (2, 'admin@admin.com', 'Administrator', 'Male', 'Administrator', 'Li', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '1234567890', 'admin', NULL);
INSERT INTO AIP.ACCOUNT (ID, EMAIL, FIRSTNAME, GENDER, GROUPNAME, LASTNAME, PASSWORD, PHONENUMBER, USERNAME, CARDTOKEN) 
	VALUES (51, 'user@user.com', 'User', 'Male', 'Users', 'Li', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', '123456789', 'user', NULL);

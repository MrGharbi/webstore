DROP DATABASE IF EXISTS WebStore;
CREATE DATABASE WebStore;
USE WebStore;

CREATE TABLE T_Users (
	IdUser				numeric(4)		PRIMARY KEY,
	Login				varchar(20)	NOT NULL,
	Password			varchar(20)	NOT NULL,
	ConnectionNumber	numeric(4)		NOT NULL DEFAULT 0
)

INSERT INTO T_Users (IdUser, Login, Password) VALUES ( 1, 'Anderson',	'Neo' );
INSERT INTO T_Users (IdUser, Login, Password) VALUES ( 2, 'Skywalker',	'Luke' );
INSERT INTO T_Users (IdUser, Login, Password) VALUES ( 3, 'Plissken',	'Snake' );
INSERT INTO T_Users (IdUser, Login, Password) VALUES ( 4, 'Ripley',		'Ellen' );
INSERT INTO T_Users (IdUser, Login, Password) VALUES ( 5, 'Bond',		'James' );

SELECT * FROM T_Users;

CREATE TABLE T_Admins (
	IdUser				numeric(4)		NOT NULL REFERENCES T_Users(IdUser),
	Rights				varchar(10) NOT NULL
);

INSERT INTO T_Admins VALUES ( 1, '-RWX------' );
INSERT INTO T_Admins VALUES ( 5, '-RWK------' );

-- -----------------------------------------------------------------------------
-- - Construction de la tables des articles en vente                         ---
-- -----------------------------------------------------------------------------
CREATE TABLE T_Articles (
	IdArticle			SMALLSERIAL		PRIMARY KEY ,
	Description			varchar(30)	NOT NULL,
	Brand				varchar(30)	NOT NULL,
	UnitaryPrice		float(8)	NOT NULL
);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ( 'Souris',					'Logitoch',				65 );
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ( 'Clavier',					'Microhard',			49.5 );
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ( 'Systeme d''exploitation',	'Fenetres Vistouille',	150 );
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ( 'Tapis souris', 			'Evolution MM',			5 );
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ( 'Cle USB 8 To', 			'Syno',					8 );
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ( 'Laptop', 					'PH',					1199 );
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ( 'CD x 500', 				'CETME',				250 );
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ( 'DVD-R x 100', 			'CETME',				99 );
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ( 'DVD+R x 100', 			'CETME',				105 );
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ( 'Batterie Laptop', 		'PH',					80 );
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ( 'Casque Audio', 			'Syno',					105 );
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ( 'WebCam', 					'Logitoch',				755 );

SELECT * FROM T_Articles;

-- -----------------------------------------------------------------------------
-- - Construction de la tables des commandes                                 ---
-- -----------------------------------------------------------------------------
CREATE TABLE T_Commands (
	IdCommand		SMALLSERIAL		PRIMARY KEY ,
	IdUser			numeric(4)		NOT NULL REFERENCES T_Users(IdUser),
    CommandDate     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE T_CommandLines (
	IdCommandLine	SMALLSERIAL		PRIMARY KEY ,
	IdCommand		SMALLSERIAL		NOT NULL REFERENCES T_Commands(IdCommand),
	IdArticle		SMALLSERIAL		NOT NULL REFERENCES T_Articles(IdArticle),
	Quantity		numeric(4) 		NOT NULL
);

-- Une première commande
INSERT INTO T_Commands (IdUser, CommandDate) VALUES ( 2, now() ); 
INSERT INTO T_CommandLines (IdCommand, IdArticle, Quantity) VALUES (1, 1, 5); 
INSERT INTO T_CommandLines (IdCommand, IdArticle, Quantity) VALUES (1, 3, 3);

-- Une seconde commande, pour un admin
INSERT INTO T_Commands (IdUser, CommandDate) VALUES ( 1, now() ); 
INSERT INTO T_CommandLines (IdCommand, IdArticle, Quantity) VALUES (2, 2, 4); 
INSERT INTO T_CommandLines (IdCommand, IdArticle, Quantity) VALUES (2, 3, 1);
INSERT INTO T_CommandLines (IdCommand, IdArticle, Quantity) VALUES (2, 4, 1);
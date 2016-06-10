-- Encoding via TomCat VM Options: -Dfile.encoding=UTF-8
-- Falls du eine andere Lösung herausfindest, gerne! :)

INSERT INTO STATE (STATE_ID, TITLE) VALUES (1, 'Angelegt');
INSERT INTO STATE (STATE_ID, TITLE) VALUES (2, 'In Bearbeitung');
INSERT INTO STATE (STATE_ID, TITLE) VALUES (3, 'Behoben');
INSERT INTO STATE (STATE_ID, TITLE) VALUES (4, 'Abgelehnt');
INSERT INTO STATE (STATE_ID, TITLE) VALUES (5, 'Wiedereröffnet');
INSERT INTO STATE (STATE_ID, TITLE) VALUES (6, 'Geschlossen');

insert into STATE_TOSTATE (STATE_ID, TOSTATE_ID) values (1, 2); -- Angelegt->In Bearbeitung
insert into STATE_TOSTATE (STATE_ID, TOSTATE_ID) values (2, 3); -- In Bearbeitung->Behoben
insert into STATE_TOSTATE (STATE_ID, TOSTATE_ID) values (2, 4); -- In Bearbeitung->Abgelehnt
insert into STATE_TOSTATE (STATE_ID, TOSTATE_ID) values (3, 5); -- Behoben->Wiedereröffnet
insert into STATE_TOSTATE (STATE_ID, TOSTATE_ID) values (3, 6); -- Behoben->Geschlossen
insert into STATE_TOSTATE (STATE_ID, TOSTATE_ID) values (4, 5); -- Abgelehnt->Wiedereröffnet
insert into STATE_TOSTATE (STATE_ID, TOSTATE_ID) values (4, 6); -- Abgelehnt->Geschlossen
insert into STATE_TOSTATE (STATE_ID, TOSTATE_ID) values (5, 2); -- Wiedereröffnet->In Bearbeitung

INSERT INTO USER (ID, EMAIL, FIRSTNAME, LASTNAME, PASSWORD) VALUES (1, 'otto-wagner@gmx.net', 'Otto', 'Wagner', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.');

INSERT INTO BUG (ID, CREATIONDATE, DESCRIPTION, LASTUPDATEDATE, TITLE, AUTHOR_ID, DEVELOPER_ID, STATE_STATE_ID) VALUES (1, '2015-10-22 21:32:26.379000000', 'Die Beschreibung des Fehlers!', NULL, 'Ein Fehler!', 1, NULL, 1);

INSERT INTO COMMENT (ID, CREATIONDATE, DESCRIPTION, TITLE, AUTHOR_ID, BUG_ID) VALUES (1, '2015-10-23 14:41:54.984000000', 'Dies ist ein Kommentar! :)', 'Ein Kommentar!', 1, 1);

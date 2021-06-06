INSERT INTO authorities (id, role_type)
VALUES (1,'ADMIN')
ON DUPLICATE KEY UPDATE role_type = role_type;

INSERT INTO authorities (id, role_type)
VALUES (2,'COOK')
ON DUPLICATE KEY UPDATE role_type = role_type;

INSERT INTO authorities (id, role_type)
VALUES (3,'BASIC_USER')
ON DUPLICATE KEY UPDATE role_type = role_type;

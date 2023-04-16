-- Dummy data for Lobby table
INSERT INTO Lobby (id, MAX_PLAYERS, TURN_TIMER, GAME_MODE, name, password, created, status, WHOSE_TURN)
VALUES (1, 4, 30, 1, 'Ludo Masters', '1234', '2022-12-01 10:00:00', 'ACTIVE', 1),
       (2, 2, 20, 2, 'Ludo Challenge', NULL, '2023-02-15 14:30:00', 'INACTIVE', 2),
       (3, 3, 40, 1, 'Ludo Pro', NULL, '2023-03-12 16:00:00', 'ACTIVE', 2),
       (4, 2, 30, 2, 'Ludo Fun', '5678', '2023-03-22 20:00:00', 'ACTIVE', 3),
       (5, 4, 20, 1, 'Ludo Madness', NULL, '2023-04-01 09:45:00', 'INACTIVE', 1);

-- Dummy data for User table
INSERT INTO "user" (id, email, password, username, COUNTRY_CODE, picture)
VALUES (1, 'john@gmail.com', 'pass123', 'john123', 'US', 'profile.jpg'),
       (2, 'emma@yahoo.com', 'password', 'emma89', 'UK', NULL),
       (3, 'jane@hotmail.com', 'jane123', 'jane.doe', 'AU', 'avatar.png'),
       (4, 'mike@gmail.com', 'password', 'mike_t', 'US', NULL),
       (5, 'lisa@yahoo.com', 'lisa123', 'lisa_w', 'CA', 'avatar.png');

-- Dummy data for UserLobby table
INSERT INTO USER_LOBBY (user_id, lobby_id, PAWN_POSITION1, PAWN_POSITION2, PAWN_POSITION3, PAWN_POSITION4)
VALUES (1, 1, 1, 1, 1, 1),
       (2, 1, 1, 1, 1, 1),
       (3, 1, 1, 1, 1, 1),
       (4, 2, 1, 1, 1, 1),
       (5, 2, 1, 1, 1, 1),
       (1, 3, 1, 1, 1, 1),
       (3, 3, 1, 1, 1, 1),
       (5, 3, 1, 1, 1, 1),
       (2, 4, 1, 1, 1, 1),
       (4, 4, 1, 1, 1, 1),
       (1, 5, 1, 1, 1, 1),
       (2, 5, 1, 1, 1, 1),
       (3, 5, 1, 1, 1, 1),
       (4, 5, 1, 1, 1, 1),
       (5, 5, 1, 1, 1, 1);
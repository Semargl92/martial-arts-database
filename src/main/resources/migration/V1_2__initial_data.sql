insert into public.martial_arts (id, name, type, origin, foundation_date, description, weapon_technics_available)
values  (2, 'kashima shin ryu', 'TRADITIONAL', 'Japan', '1600-01-01', 'traditional japanese martial art', true),
        (5, 'taekwondo', 'COMBAT_SPORT', 'Korea', '1940-01-01', 'korean martial art, characterized by punching and kicking techniques, with emphasis on head-height kicks, jumping spinning kicks, and fast kicking techniques.', false),
        (4, 'muay thai', 'COMBAT_SPORT', 'Thailand', '1750-01-01', 'martial art and combat sport that uses stand-up striking along with various clinching techniques', false),
        (1, 'aikido', 'TRADITIONAL', 'Japan', '1940-01-01', 'art of peace', true),
        (3, 'classic boxing', 'COMBAT_SPORT', 'Great Britain', '1867-01-01', 'combat sport in which two people, usually wearing protective gloves and other protective equipment such as hand wraps and mouthguards, throw punches at each other for a predetermined amount of time in a boxing ring.', false),
        (6, 'Hapkido', 'TRADITIONAL', 'Korea', null, 'test', true),
        (7, 'Krav Maga', 'SELF_DEFENSE', 'Israel', '1940-07-01', 'military self defense and fighting system', true);

insert into public.users (id, name, surname, login, gender, weight, is_deleted, created, changed, birth_date, password)
values  (8, 'Sakura', 'Haruno', 'notUselessAtAll', 'FEMALE', 50, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1997-12-11', null),
        (12, 'Marina', 'Narrow', 'marillin', 'FEMALE', 77, false, null, null, '1978-01-26', null),
        (6, 'Samuel', 'Jackson', 'Godlike', 'MALE', 100, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1995-06-21', null),
        (3, 'Sarah', 'Smith', 'smitty', 'FEMALE', 65, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1992-07-10', null),
        (10, 'Naruto', 'Uzumaki', 'theseventhhokage', 'MALE', 85, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1995-07-10', null),
        (7, 'Samanta', 'Starlight', 'superStar', 'FEMALE', 57, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '2001-08-08', null),
        (4, 'Deborah', 'Harbringer', 'habringer_1', 'FEMALE', 75, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1995-12-01', null),
        (14, 'Anastasia', 'Fancy', 'nastygirl', 'FEMALE', 67, false, '2021-05-31 00:00:00.000000', '2021-05-31 00:00:00.000000', '1997-11-13', null),
        (11, 'Sasuke', 'Uchiha', 'chidori', 'MALE', 84, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1995-08-11', null),
        (9, 'Alex', 'Namikaze', 'naminami111', 'MALE', 90, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1997-09-21', null),
        (1, 'Alex', 'Skywalker', 'ChosenOne', 'MALE', 95, false, '2020-01-01 00:00:00.000000', '2021-08-09 16:53:49.394662', '1999-09-01', 'force_with_me'),
        (5, 'Otto', 'Bismark', 'IronMan', 'MALE', 85, false, '2020-01-01 00:00:00.000000', '2021-08-09 16:56:23.995812', '1989-12-11', 'stark'),
        (17, 'Zirael', 'Queen', 'embi', 'FEMALE', 60, false, '2021-08-09 17:17:46.039218', '2021-08-09 17:19:05.384536', '2021-08-09', 'iron_maiden'),
        (2, 'Geralt', 'Of Rivia', 'White_wolf_of_Rivia', 'MALE', 85, false, '2021-08-09 17:15:37.922777', '2021-08-09 17:29:45.018145', '2021-08-09', 'caerme'),
        (18, 'Markus', 'Blitz', 'thundergod', 'MALE', 90, false, '2021-08-09 17:38:39.239460', '2021-08-09 17:38:39.239460', '2021-08-09', 'raiden_111'),
        (19, 'Varvara', 'Smirnova', 'starly', 'FEMALE', 65, false, '2021-08-09 17:45:21.482272', '2021-08-09 17:47:20.580812', '1990-08-09', 'bad_girl');

insert into public.grades (id, name, duration_for_next_lvl, number_of_trainings_days, exam_description, martial_art_id)
values  (1, '6 kyu', 30, 25, 'kata', 1),
        (2, '5 kyu', 60, 30, 'kata', 1),
        (3, '4 kyu', 90, 40, 'kata', 1),
        (4, '3 kyu', 90, 50, 'kata', 1),
        (5, '2 kyu', 180, 50, 'kata', 1),
        (6, '1 kyu', 180, 60, 'kata', 1),
        (7, 'shodan', 365, 100, 'kata', 1),
        (8, 'shoden', null, null, 'kata', 2),
        (9, 'chuden', null, null, 'kata', 2),
        (10, 'okuden', null, null, 'kata', 2),
        (11, '3 rank', 365, null, 'sparring', 4),
        (12, '2 rank', 365, null, 'sparring', 4),
        (13, '1 rank', 365, null, 'sparring', 4),
        (14, 'novice', null, 30, 'sparring', 1);

insert into public.students (id, user_id, grade_id, teachers_user_id, last_exam_date, numbers_of_trainings_days, start_date, is_deleted, created, changed)
values  (1, 1, 3, 5, '1973-11-27', 123, null, false, '2021-06-04 00:00:00.000000', '2021-06-04 00:00:00.000000'),
        (3, 7, 7, 5, '2009-01-19', 123, null, false, '2021-06-04 00:00:00.000000', '2021-06-04 00:00:00.000000');

insert into public.exercises (id, name, grade_id, description, resources, type, is_weapon_technik)
values  (3, 'Shomen uchi', 1, 'vertical cut', null, 'ATTACK', true),
        (7, 'Kokyu nage', 3, 'throwing opponent with whole body', null, 'DEFENSE', false),
        (4, 'Irimi nage', 2, 'throwing opponent with hed control', null, 'ATTACK', false),
        (6, 'Kesa giri', 7, 'diagonal cut with sword', null, 'ATTACK', true),
        (9, 'Gyaku kesa giri', 7, 'diagonal cut with sword from bottom', null, 'ATTACK', true),
        (8, 'Yokomen uchi', 7, 'diagonal cut with sword into the head', null, 'ATTACK', true),
        (5, 'Shomen uchi', 7, 'vertical cut with sword', null, 'ATTACK', true),
        (1, 'Kotegaeshi', 1, 'throw with the wrist', null, 'DEFENSE', false);

insert into public.roles (id, role_name)
values  (1, 'ADMIN'),
        (2, 'USER');

insert into public.user_roles (id, role_id, user_id)
values  (1, 1, 1),
        (2, 2, 1);




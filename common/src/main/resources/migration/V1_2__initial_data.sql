insert into public.martial_arts (name, type, origin, foundation_date, description, weapon_technics_available)
values  ('aikido', 'TRADITIONAL', 'Japan', '1940-01-01', 'art of peace', true),
        ('kashima shin ryu', 'TRADITIONAL', 'Japan', '1600-01-01', 'traditional japanese martial art', true),
        ('taekwondo', 'COMBAT_SPORT', 'Korea', '1940-01-01', 'korean martial art, characterized by punching and kicking techniques, with emphasis on head-height kicks, jumping spinning kicks, and fast kicking techniques.', false),
        ('muay thai', 'COMBAT_SPORT', 'Thailand', '1750-01-01', 'martial art and combat sport that uses stand-up striking along with various clinching techniques', false),
        ('classic boxing', 'COMBAT_SPORT', 'Great Britain', '1867-01-01', 'combat sport in which two people, usually wearing protective gloves and other protective equipment such as hand wraps and mouthguards, throw punches at each other for a predetermined amount of time in a boxing ring.', false),
        ('Hapkido', 'TRADITIONAL', 'Korea', null, 'test', true),
        ('Krav Maga', 'SELF_DEFENSE', 'Israel', '1940-07-01', 'military self defense and fighting system', true);


insert into public.users (name, surname, login, gender, weight, is_deleted, created, changed, birth_date, password)
values  ('Alex', 'Skywalker', 'ChosenOne', 'MALE', 95, false, '2020-01-01 00:00:00.000000', '2021-08-09 16:53:49.394662', '1999-09-01', 'force_with_me'),
        ('Otto', 'Bismark', 'IronMan', 'MALE', 85, false, '2020-01-01 00:00:00.000000', '2021-08-09 16:56:23.995812', '1989-12-11', 'stark'),
        ('Geralt', 'Of Rivia', 'White_wolf_of_Rivia', 'MALE', 85, false, '2021-08-09 17:15:37.922777', '2021-08-09 17:29:45.018145', '2021-08-09', 'caerme'),
        ('Markus', 'Blitz', 'thundergod', 'MALE', 90, false, '2021-08-09 17:38:39.239460', '2021-08-09 17:38:39.239460', '2021-08-09', 'raiden_111'),
        ('Varvara', 'Smirnova', 'varWar', 'FEMALE', 100, true, '2021-08-09 17:45:21.482272', '2021-08-22 15:55:02.120076', '1990-08-09', 'bad_girl'),
        ('Sarah', 'Smith', 'smitty', 'FEMALE', 65, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1992-07-10', 'class'),
        ('Sakura', 'Haruno', 'notUselessAtAll', 'FEMALE', 50, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1997-12-11', 'sakura'),
        ('Samuel', 'Jackson', 'Godlike', 'MALE', 100, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1995-06-21', 'godlike'),
        ('Deborah', 'Harbringer', 'habringer_1', 'FEMALE', 75, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1995-12-01', 'daemon'),
        ('Samanta', 'Starlight', 'superStar', 'FEMALE', 57, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '2001-08-08', 'star'),
        ('Naruto', 'Uzumaki', 'theseventhhokage', 'MALE', 85, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1995-07-10', 'kurama'),
        ('Alex', 'Namikaze', 'naminami111', 'MALE', 90, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1997-09-21', 'thunder'),
        ('Anastasia', 'Fancy', 'nastygirl', 'FEMALE', 67, false, '2021-05-31 00:00:00.000000', '2021-05-31 00:00:00.000000', '1997-11-13', 'nasty'),
        ('Sasuke', 'Uchiha', 'chidori', 'MALE', 84, true, '2020-01-01 00:00:00.000000', '2021-08-11 04:04:58.070961', '1995-08-11', 'bad_boi'),
        ('Marina', 'Narrow', 'marillin', 'FEMALE', 77, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1978-01-26', 'narrow'),
        ('Sarah', 'Baskoff', 'severalTimes', 'FEMALE', 55, false, '2021-08-24 19:57:24.314584', '2021-08-24 19:57:24.314584', '1997-08-10', 'several');

insert into public.grades (name, duration_for_next_lvl, number_of_trainings_days, exam_description, martial_art_id)
values  ('6 kyu', 30, 25, 'kata', 1),
        ('5 kyu', 60, 30, 'kata', 1),
        ('4 kyu', 90, 40, 'kata', 1),
        ('3 kyu', 90, 50, 'kata', 1),
        ('2 kyu', 180, 50, 'kata', 1),
        ('1 kyu', 180, 60, 'kata', 1),
        ('shodan', 365, 100, 'kata', 1),
        ('shoden', null, null, 'kata', 2),
        ('chuden', null, null, 'kata', 2),
        ('okuden', null, null, 'kata', 2),
        ('3 rank', 365, null, 'sparring', 4),
        ('2 rank', 365, null, 'sparring', 4),
        ('1 rank', 365, null, 'sparring', 4),
        ('novice', null, 30, 'sparring', 1),
        ('string123', 900, 80, 'test', 1);

insert into public.students (user_id, grade_id, teachers_user_id, last_exam_date, numbers_of_trainings_days, start_date, is_deleted, created, changed)
values  (1, 3, 5, '1973-11-27', 123, null, false, '2021-06-04 00:00:00.000000', '2021-06-04 00:00:00.000000'),
        (7, 7, 5, '2009-01-19', 123, null, false, '2021-06-04 00:00:00.000000', '2021-06-04 00:00:00.000000'),
        (12, 11, 5, null, 560, null, false, null, null),
        (6, 11, 5, null, 500, null, false, null, '2021-08-17 16:34:36.053347'),
        (8, 8, 5, null, 80, null, false, null, '2021-08-17 16:35:01.975622'),
        (1, 3, 7, '2021-08-17', null, null, false, null, '2021-08-17 16:42:34.964999'),
        (6, 10, null, '2021-08-03', 89, '2021-08-03', false, null, '2021-08-17 16:47:40.795857'),
        (1, 1, 1, '2021-08-21', 0, '2021-08-21', false, '2021-08-21 17:03:03.378204', '2021-08-21 17:03:03.378204');


insert into public.exercises (name, grade_id, description, resources, type, is_weapon_technik)
values  ('Shomen uchi', 1, 'vertical cut', null, 'ATTACK', true),
        ('Kokyu nage', 3, 'throwing opponent with whole body', null, 'DEFENSE', false),
        ('Irimi nage', 2, 'throwing opponent with hed control', null, 'ATTACK', false),
        ('Kesa giri', 7, 'diagonal cut with sword', null, 'ATTACK', true),
        ('Gyaku kesa giri', 7, 'diagonal cut with sword from bottom', null, 'ATTACK', true),
        ('Yokomen uchi', 7, 'diagonal cut with sword into the head', null, 'ATTACK', true),
        ('Shomen uchi', 7, 'vertical cut with sword', null, 'ATTACK', true),
        ('Kotegaeshi', 1, 'throw with the wrist', null, 'DEFENSE', false),
        ('Uppercut', 11, 'Upwards hand strike', null, 'ATTACK', false),
        ('Low kick', 11, '11', null, 'ATTACK', false),
        ('sokui tsuke', 8, 'deflecting of tsuki with sticking sword', 'string', 'DEFENSE', true);

insert into public.roles (role_name)
values  ('ROLE_ADMIN'),
        ('ROLE_USER'),
        ('ROLE_TEACHER');

insert into public.user_roles (role_id, user_id)
values  (1, 1),
        (2, 1),
        (3, 11),
        (2, 2),
        (2, 3),
        (2, 4),
        (2, 5),
        (2, 6),
        (2, 7),
        (2, 8),
        (2, 9),
        (2, 10),
        (2, 11),
        (2, 12),
        (2, 13),
        (2, 14),
        (2, 15),
        (2, 16);




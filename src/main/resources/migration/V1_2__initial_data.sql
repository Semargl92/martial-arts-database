insert into public.martial_arts (id, name, type, origin, foundation_date, description, weapon_technics_available)
values  (1, 'aikido', 'traditional', 'Japan', '1940-01-01', 'art of peace', true),
        (2, 'kashima shin ryu', 'traditional', 'Japan', '1600-01-01', 'traditional japanese martial art', true),
        (3, 'classic boxing', 'combat sport', 'Great Britain', '1867-01-01', 'combat sport in which two people, usually wearing protective gloves and other protective equipment such as hand wraps and mouthguards, throw punches at each other for a predetermined amount of time in a boxing ring.', false),
        (4, 'muay thai', 'combat sport', 'Thailand
', '1750-01-01', 'martial art and combat sport that uses stand-up striking along with various clinching techniques', false),
        (5, 'taekwondo', 'combat sport', 'Korea', '1940-01-01', 'korean martial art, characterized by punching and kicking techniques, with emphasis on head-height kicks, jumping spinning kicks, and fast kicking techniques.', false),
        (6, 'Hapkido', 'traditional', 'Korea', null, 'test', true);

insert into public.users (id, name, surname, login, gender, weight, is_deleted, created, changed, birth_date)
values  (1, 'Alex', 'Skywalker', 'ChosenOne', 'male', 95, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1999-09-01'),
        (3, 'Sarah', 'Smith', 'smitty', 'female', 65, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1992-07-10'),
        (4, 'Deborah', 'Harbringer', 'habringer_1', 'female', 75, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1995-12-01'),
        (5, 'Otto', 'Bismark', 'IronMan', 'male', 85, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1989-12-11'),
        (6, 'Samuel', 'Jackson', 'Godlike', 'male', 100, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1995-06-21'),
        (7, 'Samanta', 'Starlight', 'superStar', 'female', 57, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '2001-08-08'),
        (8, 'Sakura', 'Haruno', 'notUselessAtAll', 'female', 50, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1997-12-11'),
        (9, 'Alex', 'Namikaze', 'naminami111', 'male', 90, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1997-09-21'),
        (10, 'Naruto', 'Uzumaki', 'theseventhhokage', 'male', 85, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1995-07-10'),
        (11, 'Sasuke', 'Uchiha', 'chidori', 'male', 84, false, '2020-01-01 00:00:00.000000', '2020-01-01 00:00:00.000000', '1995-08-11'),
        (12, 'Marina', 'Narrow', 'marillin', 'female', 77, false, null, null, '1978-01-26'),
        (14, 'Anastasia', 'Fancy', 'nastygirl', 'female', 67, false, '2021-05-31 00:00:00.000000', '2021-05-31 00:00:00.000000', '1997-11-13'),
        (16, 'Anastasia', 'Fancy', 'nastygirl12345', 'female', 67, false, '2021-06-03 00:00:00.000000', '2021-06-03 00:00:00.000000', '1997-11-13');

insert into public.grades (id, name, duration_for_next_lvl, number_of_trainings_days, exam_type, martial_art_id)
values  (1, '6 kyu', 30, 25, 'kata', 1),
        (2, '5 kyu', 60, 30, 'kata', 1),
        (3, '4 kyu', 90, 40, 'kata', 1),
        (4, '3 kyu
', 90, 50, 'kata', 1),
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
values  (1, 'Kotegaeshi', 1, 'test', null, 'defense', false),
        (3, 'Shomen uchi', 1, 'test', null, 'attack', true),
        (4, 'Irimi nage', 2, 'throwing opponent with hed control', null, 'defense', false),
        (5, 'Shomen uchi', 7, 'vertical cut with sword', null, 'attack', true),
        (6, 'Kesa giri', 7, 'diagonal cut with sword', null, 'attack', true),
        (7, 'Kokyu nage', 3, 'throwing opponent with whole body', null, 'defense', false),
        (8, 'Yokomen uchi', 7, 'diagonal cut with sword into the head', null, 'attack', true),
        (9, 'Gyaku kesa giri', 7, 'diagonal cut with sword from bottom', null, 'attack', true);

insert into public.security (id, role, user_id, is_deleted, created, changed)
values  (1, 'Admin', 1, false, '2021-06-04 00:00:00.000000', '2021-06-04 00:00:00.000000'),
        (3, 'Admin', 1, false, '2021-06-04 00:00:00.000000', '2021-06-04 00:00:00.000000'),
        (4, 'Student', 3, false, null, '2021-06-04 00:00:00.000000');
-- Person (group admins)
INSERT INTO person (id, name, weight, passport_id, location_x, location_y, location_name) VALUES
                                                                                              (1, 'Ivan Petrov', 78, 'AB123456', 100, 200, 'Almaty'),
                                                                                              (2, 'Maria Sidorova', 65, 'CD654321', 50, 75, 'Astana'),
                                                                                              (3, 'Sergey Volkov', 90, 'EF112233', -10, 40, 'Karaganda'),
                                                                                              (4, 'Olga Kuznetsova', 55, 'GH998877', 300, 150, 'Shymkent'),
                                                                                              (5, 'Dmitry Orlov', 82, 'IJ445566', 0, 0, NULL);

ALTER TABLE person ALTER COLUMN id RESTART WITH 6;

-- StudyGroup
INSERT INTO study_group (id, name, coord_x, coord_y, creation_date, students_count, expelled_students, form_of_education, semester, group_admin_id) VALUES
                                                                                                                                                        (1, 'Math-101', 500, 120, '2024-09-01', 25, 2, 'DISTANCE_EDUCATION', 'FIRST', 1),
                                                                                                                                                        (2, 'Physics-202', 300, 80, '2024-09-05', 18, 0, 'DISTANCE_EDUCATION', 'SEVENTH', 2),
                                                                                                                                                        (3, 'Chemistry-303', 700, 189, '2023-02-10', 30, 5, 'EVENING_CLASSES', 'FIRST', 3),
                                                                                                                                                        (4, 'History-404', 12, 45, '2024-01-15', 15, 1, 'FULL_TIME_EDUCATION', 'SEVENTH', 4),
                                                                                                                                                        (5, 'Biology-505', 150, 60, '2022-11-20', 22, 3, 'DISTANCE_EDUCATION', 'FIRST', 5),
                                                                                                                                                        (6, 'CompSci-606', 726, 189, '2024-06-01', 40, 0, 'FULL_TIME_EDUCATION', 'FIRST', NULL),
                                                                                                                                                        (7, 'Economics-707', 200, 99, '2023-08-30', 12, 4, 'EVENING_CLASSES', 'THIRD', NULL);

ALTER TABLE study_group ALTER COLUMN id RESTART WITH 8;
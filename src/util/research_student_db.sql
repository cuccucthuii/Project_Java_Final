create table Admin
(
    admin_id       serial primary key,
    admin_user     varchar(50)  not null unique,
    admin_password varchar(255) not null
);

create table Student
(
    student_id        serial primary key,
    student_name      varchar(100) not null,
    student_dob       date         not null,
    student_email     varchar(100) not null unique,
    student_sex       boolean      not null,
    student_phone     varchar(200),
    student_password  varchar(255) not null,
    student_create_at date default now()
);
create table Course
(
    course_id         serial primary key,
    course_name       varchar(100) not null,
    course_duration   int          not null,
    course_instructor varchar(100) not null,
    course_create_at  date default now()
);

create TYPE enrollment_status as ENUM ('WAITING','DENIED','CANCER','CONFIRM');

create table Enrollment
(
    enrollment_id            serial primary key,
    student_id               int not null,
    cource_id                int not null,
    enrollment_registered_at date                                                                            default current_date,
    status                   enrollment_status check ( status in ('WAITING', 'DENIED', 'CANCER', 'CONFIRM')) default 'WAITING'
);

INSERT INTO Admin (admin_user, admin_password)
VALUES ('admin01', 'password_hash_01'),
       ('admin02', 'password_hash_02'),
       ('admin03', 'password_hash_03'),
       ('admin04', 'password_hash_04'),
       ('admin05', 'password_hash_05');


-- =====================
-- Student (5 records)
-- student_sex: true = Nam, false = Nữ
-- =====================
INSERT INTO Student (student_name, student_dob, student_email,
                     student_sex, student_phone, student_password)
VALUES ('Nguyen Van A', '2000-05-10', 'a@gmail.com', true, '0901111111', 'passA'),
       ('Tran Thi B', '2001-08-22', 'b@gmail.com', false, '0902222222', 'passB'),
       ('Le Van C', '1999-12-01', 'c@gmail.com', true, '0903333333', 'passC'),
       ('Pham Thi D', '2002-03-15', 'd@gmail.com', false, '0904444444', 'passD'),
       ('Hoang Van E', '2000-07-30', 'e@gmail.com', true, '0905555555', 'passE');


-- =====================
-- Course (5 records)
-- =====================
INSERT INTO Course (course_name, course_duration, course_instructor)
VALUES ('Java Core', 60, 'Mr. Nam'),
       ('Spring Boot', 45, 'Ms. Hoa'),
       ('PostgreSQL', 30, 'Mr. Long'),
       ('HTML & CSS', 25, 'Ms. Lan'),
       ('JavaScript', 40, 'Mr. Minh');

-- =====================
-- Enrollment (5 records)
-- =====================
INSERT INTO Enrollment (student_id, cource_id, status)
VALUES (1, 1, 'WAITING'),
       (2, 2, 'CONFIRM'),
       (3, 3, 'DENIED'),
       (4, 1, 'CONFIRM'),
       (5, 4, 'CANCER');

-- API Login with Admin
create or replace function log_in_with_admin_account(
    user_in varchar(50),
    password_in varchar(255))
    returns boolean
    language plpgsql
as
$$
begin
return exists(select 1
              from Admin a
              where a.admin_user = user_in
                and a.admin_password = password_in);
end;
$$;
create or replace function func_get_all_course()
    returns table
            (
                course_id         int,
                course_name       varchar(100),
                course_duration   int,
                course_instructor varchar(100),
                course_create_at  date
            )
    language plpgsql
as
$$
begin
return query
select c.course_id, c.course_name, c.course_duration, c.course_instructor, c.course_create_at from Course c;
end;
$$;

create or replace procedure proc_create_course(
    name_in varchar(100),
    duration_in int,
    instructor_in varchar(100)
)
    language plpgsql
as
$$
begin
insert into Course (course_name, course_duration, course_instructor)
values (name_in, duration_in, instructor_in);
end;
$$;

create or replace procedure proc_update_course(
    id_in int,
    name_in varchar(100),
    duration_in int,
    instructor_in varchar(100)
)
    language plpgsql
as
$$
begin
update Course
set course_name       = name_in,
    course_duration   = duration_in,
    course_instructor = instructor_in
where course_id = id_in;
end;
$$;

create or replace function func_search_course_by_id(id_in int)
    returns table
            (
                course_id         int,
                course_name       varchar(100),
                course_duration   int,
                course_instructor varchar(100),
                course_create_at  date
            )
    language plpgsql
as
$$
begin
return query
select * from Course c where c.course_id = id_in;
end;
$$;

create or replace procedure proc_delete_course(id_in int)
    language plpgsql
as
$$
begin
delete FROM Course c where c.course_id = id_in;
end;
$$;

create or replace function func_search_course_by_name(name_in varchar(100))
    returns table
            (
                course_id         int,
                course_name       varchar(100),
                course_duration   int,
                course_instructor varchar(100),
                course_create_at  date
            )
    language plpgsql
as
$$
begin
return query
select * from Course c where c.course_name ilike '%' || name_in || '%';
end;
$$;

create or replace function func_soft_sourse_by_id_desc()
    returns table
            (
                course_id         int,
                course_name       varchar(100),
                course_duration   int,
                course_instructor varchar(100),
                course_create_at  date
            )
    language plpgsql
as
$$
begin
return query
select * from Course
ORDER BY course_id desc;
end;
$$;

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
    course_id                int not null,
    enrollment_registered_at date                                                                            default current_date,
    status                   enrollment_status check ( status in ('WAITING', 'DENIED', 'CANCER', 'CONFIRM')) default 'WAITING',
    foreign key (student_id) references Student (student_id),
    foreign key (course_id) references Course (course_id)
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
INSERT INTO Enrollment (student_id, course_id, status)
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
        select *
        from Course
        ORDER BY course_id desc;
end;
$$;

-- ***************** API MANAGEMENT STUDENT *******************

-- GET ALL STUDENT
create or replace function func_get_list_student()
    returns table
            (
                student_id        int,
                student_name      varchar(100),
                student_dob       date,
                student_email     varchar(100),
                student_sex       boolean,
                student_phone     varchar(200),
                student_password  varchar(255),
                student_create_at date
            )
    language plpgsql
as
$$
begin
    return query
        select * from Student;
end;
$$;

select *
from func_get_list_student();

-- ADD STUDENT
create or replace procedure proc_add_student(
    name_in varchar(100),
    dob_in date,
    email_in varchar(100),
    sex_in boolean,
    phone_in varchar(200),
    password_in varchar(255)
)
    language plpgsql
as
$$
begin
    insert into Student (student_name, student_dob, student_email, student_sex, student_phone, student_password)
    values (name_in, dob_in, email_in, sex_in, phone_in, password_in);
end;
$$;

-- SEARCH STUDENT BY ID
create or replace function func_search_student_by_id(id_in int)
    returns table
            (
                student_id        int,
                student_name      varchar(100),
                student_dob       date,
                student_email     varchar(100),
                student_sex       boolean,
                student_phone     varchar(200),
                student_password  varchar(255),
                student_create_at date
            )
    language plpgsql
as
$$
begin
    return query
        select * from Student s where s.student_id = id_in;
end;
$$;

-- UPDATE STUDENT
create or replace procedure proc_update_student_by_id(
    id_in int,
    name_in varchar(100),
    dob_in date,
    email_in varchar(100),
    sex_in boolean,
    phone_in varchar(200),
    password_in varchar(255)
)
    language plpgsql
as
$$
begin
    update Student
    SET student_name     = name_in,
        student_dob      = dob_in,
        student_email    = email_in,
        student_sex      = sex_in,
        student_phone    = phone_in,
        student_password = password_in
    WHERE student_id = id_in;
end;
$$;

-- DELETE STUDENT
create or replace procedure proc_delete_student_by_student_id(id_in int)
    language plpgsql
as
$$
begin
    delete from Student where student_id = id_in;
end
$$;

-- SEARCH STUDENT
create or replace function func_search_student_by_email(email_in varchar(100))
    returns table
            (
                student_id        int,
                student_name      varchar(100),
                student_dob       date,
                student_email     varchar(100),
                student_sex       boolean,
                student_phone     varchar(200),
                student_password  varchar(255),
                student_create_at date
            )
    language plpgsql
as
$$
begin
    return query
        select * from Student s where s.student_email like '%' || email_in || '%';
end;
$$;

-- SORT STUDENT
create or replace function func_sort_student_by_id()
    returns table
            (
                student_id        int,
                student_name      varchar(100),
                student_dob       date,
                student_email     varchar(100),
                student_sex       boolean,
                student_phone     varchar(200),
                student_password  varchar(255),
                student_create_at date
            )
    language plpgsql
as
$$
begin
    return query
        select *
        from Student s
        order by s.student_id desc;
end;
$$;

select *
from func_sort_student_by_id();
select *
from Admin;

-- ***************** API STUDENT *******************

create or replace function func_login_student(
    email_in varchar(100),
    pass_in varchar(255)
)
    returns table
            (
                student_id        int,
                student_name      varchar(100),
                student_dob       date,
                student_email     varchar(100),
                student_sex       boolean,
                student_phone     varchar(200),
                student_password  varchar(255),
                student_create_at date
            )
    language plpgsql
as
$$
begin
    return query
        select *
        from Student s
        where s.student_email = email_in
          and s.student_password = pass_in;
end;
$$;

select *
from func_login_student('a@gmail.com', 'passA');

-- Xem danh sách khoá học đang có
create or replace function func_get_course()
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

-- tìm kiếm khoá học theo tên
create or replace function search_course_by_name(name_in varchar(100))
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
        select c.course_id, c.course_name, c.course_duration, c.course_instructor, c.course_create_at
        from Course c
        where c.course_name ilike '%' || name_in || '%';
end;
$$;

-- Đăng ký khoá học
create or replace procedure proc_sub_course(
    student_id_in int,
    cource_id_in int,
    enrollment_registered_at_in date
)
    language plpgsql
as
$$
begin
    insert into Enrollment (student_id, course_id, enrollment_registered_at)
    values (student_id_in, cource_id_in, enrollment_registered_at_in);
end;
$$;

select *
from Enrollment;

-- Tìm kiếm khoá học theo ID để check khi student nhập

-- Xem khoá học đã đăng ký
create or replace function func_view_registered_courses(id_student_in int)
    returns table
            (
                enrollment_id            int,
                course_id                int,
                course_name              varchar(100),
                course_duration          int,
                course_instructor        varchar(100),
                course_create_at         date,
                enrollment_registered_at date,
                status                   enrollment_status
            )
    language plpgsql
as
$$
begin
    return query
        select e.enrollment_id,
               c.course_id,
               c.course_name,
               c.course_duration,
               c.course_instructor,
               c.course_create_at,
               e.enrollment_registered_at,
               e.status
        from Enrollment e
                 join Course c on c.course_id = e.course_id
        where e.student_id = id_student_in;
end;
$$;

select *
from func_view_registered_courses(1);

-- sắp xếp khoá học theo Ngày đăng ký giảm dần
create or replace function func_view_registered_courses_desc(id_student_in int)
    returns table
            (
                enrollment_id            int,
                course_id                int,
                course_name              varchar(100),
                course_duration          int,
                course_instructor        varchar(100),
                course_create_at         date,
                enrollment_registered_at date,
                status                   enrollment_status
            )
    language plpgsql
as
$$
begin
    return query
        select e.enrollment_id,
               c.course_id,
               c.course_name,
               c.course_duration,
               c.course_instructor,
               c.course_create_at,
               e.enrollment_registered_at,
               e.status
        from Enrollment e
                 join Course c on c.course_id = e.course_id
        where e.student_id = id_student_in
        order by e.enrollment_registered_at desc;
end;
$$;

-- Huỷ đăng ký khoá học nếu chưa xác nhận
-- Hiển thị thông tin khoá học để confirm
create or replace function func_view_enrollment_by_id_and_student_id(id_enrollment_in int, id_student_in int)
    returns table
            (
                enrollment_id            int,
                student_id               int,
                course_id                int,
                enrollment_registered_at date,
                status                   enrollment_status
            )
    language plpgsql
as
$$
begin
    return query
        select * from Enrollment e where e.enrollment_id = id_enrollment_in and e.student_id = id_student_in;
end;
$$;



create or replace procedure proc_cancel_course(id_enrollment_in int, id_student_in int)
    language plpgsql
as
$$
begin
    update Enrollment
    set status = 'CANCER'
    where enrollment_id = id_enrollment_in
      and student_id = id_student_in
      and status = 'WAITING';
end;
$$;

create or replace function func_get_enrollment_by_id(id_enrollment int)
    returns table
            (
                enrollment_id            int,
                student_id               int,
                course_id                int,
                enrollment_registered_at date,
                status                   enrollment_status
            )
    language plpgsql
as
$$
begin
    return query
        select * from Enrollment e where e.enrollment_id = id_enrollment;
end;
$$;

-- Cập nhật mật khẩu sinh viên
create or replace procedure update_password_student(id_student_in int, pass_in varchar(255))
    language plpgsql
as
$$
begin
    update Student set student_password = pass_in where student_id = id_student_in;
end;
$$;


call proc_cancel_course(1);
select *
from Enrollment;

select *
from Student;

-- QUẢN LÝ ĐĂNG KÝ KHOÁ HỌC

-- Hiển thị danh sách sinh viên đăng ký theo từng khóa học
CREATE OR REPLACE FUNCTION func_get_students_by_course(
    course_id_in INT
)
    RETURNS TABLE
            (
                student_id    INT,
                student_name  VARCHAR,
                student_email VARCHAR,
                status        enrollment_status
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT s.student_id,
               s.student_name,
               s.student_email,
               e.status
        FROM Enrollment e
                 JOIN Student s ON e.student_id = s.student_id
        WHERE e.course_id = course_id_in
        ORDER BY s.student_id;
END;
$$;
select *
from func_get_students_by_course(1);

select *
from Admin;

-- Cập nhật trạng thái đăng ký khoá học
create or replace function func_view_enrollment_waiting()
    returns table
            (
                enrollment_id            int,
                student_id               int,
                course_id                int,
                enrollment_registered_at date,
                status                   enrollment_status
            )
    language plpgsql
as
$$
begin
    return query
    select * from Enrollment e where e.status = 'WAITING';
end;
$$;

select * from func_view_enrollment_waiting();

create or replace procedure proc_update_status_course(id_enrollment_in int, status_in varchar)
    language plpgsql
as
$$
begin
    update Enrollment set status = status_in::enrollment_status where enrollment_id = id_enrollment_in;
end;
$$;

select * from Enrollment;

create or replace procedure proc_delete_enrollment(
    enrollment_id_in int
)
    language plpgsql
as
$$
begin
    delete from Enrollment
    where enrollment_id = enrollment_id_in;
end;
$$;

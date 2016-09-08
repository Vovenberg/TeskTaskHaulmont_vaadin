CREATE TABLE gangs (
	id_gang bigint NOT NULL,
	number bigint NOT NULL,
	fac varchar(20) NOT NULL,
	CONSTRAINT gang_pk PRIMARY KEY (id_gang)
	)

CREATE TABLE students (
	id_student bigint NOT NULL,
	id_gang bigint NOT NULL,
	name varchar(20) NOT NULL,
	surname varchar(20) NOT NULL,
	age date NOT NULL,	
	CONSTRAINT student_pk PRIMARY KEY (id_student),  
	CONSTRAINT student_fk Foreign key (id_gang) references "gangs" (id_gang)
)

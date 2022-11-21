CREATE TABLE  IF NOT EXISTS patient
(
    patientid   BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255),
    surname     VARCHAR(255),
    email       VARCHAR(255),
    phone       VARCHAR(255),
    pesel       VARCHAR(255),
    city        VARCHAR(255),
    country     VARCHAR(255),
    postal_code VARCHAR(255),
    CONSTRAINT pk_patient PRIMARY KEY (patientid)
    );

CREATE TABLE IF NOT EXISTS doctor
(
    doctorid   BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255),
    surname    VARCHAR(255),
    email      VARCHAR(255),
    phone      VARCHAR(255),
    speciality VARCHAR(255),
    CONSTRAINT pk_doctor PRIMARY KEY (doctorid)
    );

CREATE TABLE  IF NOT EXISTS visit
(
    visitid    BIGINT AUTO_INCREMENT NOT NULL,
    visit_date date,
    type_visit VARCHAR(255),
    patient_id BIGINT,
    doctor_id  BIGINT,
    CONSTRAINT pk_visit PRIMARY KEY (visitid)
    );

ALTER TABLE visit
    ADD CONSTRAINT FK_VISIT_ON_DOCTOR FOREIGN KEY (doctor_id) REFERENCES doctor (doctorid) ON DELETE CASCADE;

ALTER TABLE visit
    ADD CONSTRAINT FK_VISIT_ON_PATIENT FOREIGN KEY (patient_id) REFERENCES patient (patientid) ON DELETE CASCADE;
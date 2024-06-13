CREATE TABLE `policyholder` (
  `id` int NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `information_provider_code` varchar(255) DEFAULT NULL,
  `company_registration_codes` varchar(255) DEFAULT NULL,
  `okved_code` varchar(255) DEFAULT NULL,
  `policyholder_company_email` varchar(255) DEFAULT NULL,
  `company_status` TEXT DEFAULT NULL,
  `owner_information` TEXT DEFAULT NULL,
  `contact_person_details` TEXT DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `contract` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `insurance_contract_number` VARCHAR(255) DEFAULT NULL,
  `insurer` VARCHAR(255) DEFAULT NULL,
  `status` ENUM('prospect', 'signed_contract') DEFAULT 'prospect',
  `start_date_of_insurance_coverage` DATE DEFAULT NULL,
  `end_date_of_insurance_coverage` DATE DEFAULT NULL,
  `supervising_underwriter` INT DEFAULT NULL, 
  `supervising_UOPB_employee` INT DEFAULT NULL, 
  `underwriter_one` INT DEFAULT NULL, 
  `underwriter_two` INT DEFAULT NULL, 
  `policyholder` INT DEFAULT NULL, 
  `covered_countries` TEXT DEFAULT NULL,
  `covered_risks` ENUM('political', 'commercial', 'both') DEFAULT NULL,
  `insured_share_political` VARCHAR(255) DEFAULT NULL,
  `waiting_period_political` INT DEFAULT 0,
  `max_political_credit_period` INT DEFAULT 0,
  `insured_share_commercial` VARCHAR(255) DEFAULT NULL,
  `waiting_period_commercial` INT DEFAULT 0,
  `max_commercial_credit_period` INT DEFAULT 0,
  `client_name` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT FK_supervising_underwriter FOREIGN KEY (`supervising_underwriter`) REFERENCES `user`(`id`), 
  CONSTRAINT FK_supervising_UOPB_employee FOREIGN KEY (`supervising_UOPB_employee`) REFERENCES `user`(`id`),
  CONSTRAINT FK_policyholder FOREIGN KEY (`policyholder`) REFERENCES `policyholder`(`id`)
);


CREATE TABLE `debtors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `insurance_contract_number` INT DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `information_provider_code` varchar(255) DEFAULT NULL,
  `company_registration_codes` varchar(255) DEFAULT NULL,
  `okved_code` varchar(255) DEFAULT NULL,
  `debtor_company_email` varchar(255) DEFAULT NULL,
  `company_status` TEXT DEFAULT NULL,
  `owner_information` TEXT DEFAULT NULL,
  `contact_person_details` TEXT DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT FK_debtor_insurance_contract_number FOREIGN KEY (`insurance_contract_number`) REFERENCES `contract`(`id`)
);

CREATE TABLE `request` (
    `id` int NOT NULL AUTO_INCREMENT,
    `insurance_contract_number` INT DEFAULT NULL,
    `debitors_country` varchar(255) DEFAULT NULL,
    `registration_code` varchar(255) DEFAULT NULL,
    `cl_amount` DECIMAL(10,2) DEFAULT 0.00,
    `cl_currency` varchar(255) DEFAULT NULL,
    `cl_terms_conditions` TEXT DEFAULT NULL,
    `adjustment_possibility` TEXT DEFAULT NULL,
    `status` ENUM('pending', 'accepted', 'declined') DEFAULT NULL,
    `debtor` INT DEFAULT NULL, 
    `comment` TEXT DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT FK_debtor_request FOREIGN KEY (`debtor`) REFERENCES `debtors`(`id`),
    CONSTRAINT FK_request_insurance_contract_number FOREIGN KEY (`insurance_contract_number`) REFERENCES `contract`(`id`)
);


CREATE TABLE `debtors_contract` (
  `debtor_id` int DEFAULT NULL,
  `contract_id` int DEFAULT NULL,
  CONSTRAINT FK_debtor_id FOREIGN KEY (`debtor_id`) REFERENCES `debtors`(`id`), 
  CONSTRAINT FK_contract_id FOREIGN KEY (`contract_id`) REFERENCES `contract`(`id`)
);

CREATE TABLE `country` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` TEXT NOT NULL,
    `code` CHAR(3) NOT NULL
);

-- CREATE TABLE `sample_person` (
--     `id` INT NOT NULL AUTO_INCREMENT,
--     `first_name` VARCHAR(255) DEFAULT NULL,
--     `last_name` VARCHAR(255) DEFAULT NULL,
--     `email` VARCHAR(255) DEFAULT NULL,
--     `phone` VARCHAR(255) DEFAULT NULL,
--     `date_of_birth` DATE DEFAULT NULL,
--     `occupation` VARCHAR(255) DEFAULT NULL,
--     `role` VARCHAR(255) DEFAULT NULL,
--     `important` BOOLEAN DEFAULT FALSE,
--     PRIMARY KEY (id)
-- );




-- CREATE TABLE `Client` (
--     `id` int NOT NULL AUTO_INCREMENT,
--     `name` varchar(255) DEFAULT NULL,
--     `insurance_contract_id` varchar(255) DEFAULT NULL,
--     `number_of_current_limits` INT DEFAULT NULL,
--     -- `CL_number_under_consideration` INT DEFAULT NULL,
--     -- `total_active_exposure_sum` DECIMAL(20,2) DEFAULT 0.00,
--     `insurance_contract_periods` DATE DEFAULT NULL,
--     `supervising_underwriter_and_UOPB_employee` varchar(255) DEFAULT NULL,
--     `phone_number` varchar(255) DEFAULT NULL,

--     PRIMARY KEY (`id`),
--     CONSTRAINT FK_insurance_contract_id FOREIGN KEY (`insurance_contract_id`) REFERENCES `Contract`(`id`), 
-- )







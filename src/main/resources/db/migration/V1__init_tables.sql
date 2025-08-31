CREATE TABLE IF NOT EXISTS job_data (
                                        id IDENTITY PRIMARY KEY,
                                        employer                   VARCHAR(512),
    location                   VARCHAR(512),
    job_title                  VARCHAR(512),
    years_at_employer          DECIMAL(17,2),
    years_of_experience        DECIMAL(17,2),
    salary                     DECIMAL(17,2),
    signing_bonus              DECIMAL(17,2),
    annual_bonus               DECIMAL(17,2),
    annual_stock_value_bonus   DECIMAL(17,2),
    gender                     VARCHAR(32),
    source_timestamp           VARCHAR(255),
    additional_comments        TEXT
    );

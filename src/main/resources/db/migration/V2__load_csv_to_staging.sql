-- V2__load_job_data_from_csv.sql  (for job_data_clean.csv)

-- Optional: clear table first
-- TRUNCATE TABLE job_data;

INSERT INTO job_data (
    employer,
    location,
    job_title,
    years_at_employer,
    years_of_experience,
    salary,
    signing_bonus,
    annual_bonus,
    annual_stock_value_bonus,
    gender,
    source_timestamp,
    additional_comments
)
SELECT
    employer,
    location,
    job_title,

    /* years_at_employer -> DECIMAL(17,2) */
    CAST(
            NULLIF(
                    NULLIF(
                            REGEXP_REPLACE(REPLACE(LOWER(TRIM(years_at_employer)), ',', '.'), '[^0-9.]', ''),
                            '.'
                    ),
                    ''
            ) AS DECIMAL(17,2)
    ) AS years_at_employer,

    /* years_of_experience -> DECIMAL(17,2) */
    CAST(
            NULLIF(
                    NULLIF(
                            REGEXP_REPLACE(REPLACE(LOWER(TRIM(years_of_experience)), ',', '.'), '[^0-9.]', ''),
                            '.'
                    ),
                    ''
            ) AS DECIMAL(17,2)
    ) AS years_of_experience,

    /* salary -> DECIMAL(17,2) */
    CAST(
            NULLIF(
                    NULLIF(
                            REGEXP_REPLACE(REPLACE(LOWER(TRIM(salary)), ',', '.'), '[^0-9.]', ''),
                            '.'
                    ),
                    ''
            ) AS DECIMAL(17,2)
    ) AS salary,

    /* signing_bonus -> DECIMAL(17,2) */
    CAST(
            NULLIF(
                    NULLIF(
                            REGEXP_REPLACE(REPLACE(LOWER(TRIM(signing_bonus)), ',', '.'), '[^0-9.]', ''),
                            '.'
                    ),
                    ''
            ) AS DECIMAL(17,2)
    ) AS signing_bonus,

    /* annual_bonus -> DECIMAL(17,2) */
    CAST(
            NULLIF(
                    NULLIF(
                            REGEXP_REPLACE(REPLACE(LOWER(TRIM(annual_bonus)), ',', '.'), '[^0-9.]', ''),
                            '.'
                    ),
                    ''
            ) AS DECIMAL(17,2)
    ) AS annual_bonus,

    /* annual_stock_value_bonus -> DECIMAL(17,2) */
    CAST(
            NULLIF(
                    NULLIF(
                            REGEXP_REPLACE(REPLACE(LOWER(TRIM(annual_stock_value_bonus)), ',', '.'), '[^0-9.]', ''),
                            '.'
                    ),
                    ''
            ) AS DECIMAL(17,2)
    ) AS annual_stock_value_bonus,

    gender,
    timestamp     AS source_timestamp,
    additional_comments
FROM CSVREAD(
    'classpath:/data/job_data_clean.csv',
    -- IMPORTANT: list *all* columns in the file, including 'id' first
    'timestamp,employer,location,job_title,years_at_employer,years_of_experience,salary,signing_bonus,annual_bonus,annual_stock_value_bonus,gender,additional_comments',
    'charset=UTF-8 fieldSeparator=,'
    )
-- If the header slipped through as data:
WHERE timestamp IS NOT NULL AND LOWER(timestamp) <> 'timestamp';

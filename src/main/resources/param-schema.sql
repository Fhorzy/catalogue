DROP TABLE IF EXISTS ms_param;

CREATE TABLE ms_param(
    param_key VARCHAR(15) NOT NULL,
    param_value VARCHAR(15) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT NOW(),
    creator_id VARCHAR(15) NOT NULL DEFAULT 0::INT4,
    updated_date TIMESTAMP NULL,
    updater_id VARCHAR(15) NULL,
    deleted_date VARCHAR(15) NULL,
    deleter_id TIMESTAMP NULL,
    rec_status VARCHAR(1) NULL DEFAULT 'N'::VARCHAR
) WITH (
    OIDS=FALSE
);

SELECT * FROM ms_param;

INSERT INTO ms_param(param_key, param_value)
VALUES('CRON_10_Seconds', '0/10 * * * * ?');

UPDATE ms_param
SET param_value = '0/10 * * * * ?'
WHERE param_key = 'CRON_10_Seconds';
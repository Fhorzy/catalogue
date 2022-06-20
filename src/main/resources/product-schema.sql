-- Delete ms_product table if exist
DROP TABLE IF EXISTS ms_product;

-- Create ms_product table
CREATE TABLE ms_product(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    quantity INT4 NULL,
    category_id VARCHAR(50) NULL,
    created_date TIMESTAMP NOT NULL,
    creator_id INT4 NOT NULL,
    updated_date TIMESTAMP NULL,
    updater_id INT4 NULL,
    deleted_date TIMESTAMP NULL,
    deleter_id INT4 NULL,
    rec_status VARCHAR(1) NULL DEFAULT 'N'::VARCHAR
)
WITH (
    OIDS=FALSE
) ;

-- Query All data in ms_product table
SELECT * FROM ms_product;
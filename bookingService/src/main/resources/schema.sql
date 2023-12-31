DROP TABLE IF EXISTS customers, products, employees;

CREATE TABLE IF NOT EXISTS customers (
    customer_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(250)                            NOT NULL,
    phone       VARCHAR(11)                             NOT NULL,
    e_mail      VARCHAR(254),
    CONSTRAINT pk_customer PRIMARY KEY (customer_id),
    CONSTRAINT uq_customer UNIQUE (phone)
);

CREATE TABLE IF NOT EXISTS products (
    product_id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name           VARCHAR(250)                            NOT NULL,
    price          BIGINT                                  NOT NULL,
    cooking_time   BIGINT                                  NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (product_id),
    CONSTRAINT uq_product UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS employees (
    employee_id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name              VARCHAR(250)                            NOT NULL,
    position          VARCHAR(50)                             NOT NULL,
    phone             VARCHAR(11)                             NOT NULL,
    on_shift          BOOLEAN DEFAULT FALSE                   NOT NULL,
    count_order       BIGINT,
    time_to_available TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_employee PRIMARY KEY (employee_id),
    CONSTRAINT uq_employee_phone UNIQUE (phone)
);
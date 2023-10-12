DROP TABLE IF EXISTS events;

CREATE TABLE IF NOT EXISTS events (
    event_id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    order_id                BIGINT                                  NOT NULL,
    customer_id             BIGINT,
    employee_id             BIGINT                                  NOT NULL,
    product_id              BIGINT,
    price                   BIGINT,
    status                  VARCHAR(20)                             NOT NULL,
    timestamp               TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    expected_time_of_issue  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_events PRIMARY KEY (event_id)
);
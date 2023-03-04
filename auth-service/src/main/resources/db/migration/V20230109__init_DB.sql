CREATE TABLE IF NOT EXISTS user_profile
(
    id_customer           UUID PRIMARY KEY,
    push_notification     BOOLEAN                  NOT NULL,
    password_encoded      VARCHAR(255)             NOT NULL,
    email                 VARCHAR(50)              NOT NUll,
    created               TIMESTAMP WITH TIME ZONE NOT NULL,
    updated               TIMESTAMP WITH TIME ZONE NOT NULL,
    UNIQUE(email)
);

CREATE TYPE account_status AS ENUM (
    'ACTIVE',
    'INACTIVE',
    'SUSPENDED'
);

CREATE TABLE accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID UNIQUE NOT NULL,
    agency VARCHAR(255) NOT NULL,
    number VARCHAR(255) UNIQUE NOT NULL,
    bank_code VARCHAR(255) NOT NULL,
    status account_status NOT NULL,
    balance NUMERIC(9, 2) NOT NULL,
    created_at TIMESTAMPTZ(6) NOT NULL,
    updated_at TIMESTAMPTZ(6),
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255)
);
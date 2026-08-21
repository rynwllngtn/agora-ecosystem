CREATE TYPE transaction_type AS ENUM (
    'DEBIT',
    'CREDIT'
);

CREATE TABLE transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID NOT NULL,
    correlation_id UUID NOT NULL,
    amount NUMERIC(9, 2) NOT NULL,
    type transaction_type NOT NULL,
    created_at TIMESTAMPTZ(6) NOT NULL
);
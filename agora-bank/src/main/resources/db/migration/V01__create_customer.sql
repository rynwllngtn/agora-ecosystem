CREATE TYPE customer_status AS ENUM (
    'PENDING_REGISTRATION',
    'REGISTERED'
);

CREATE TABLE customers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    identity_id UUID UNIQUE NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    status customer_status NOT NULL,
    created_at TIMESTAMPTZ(6) NOT NULL,
    updated_at TIMESTAMPTZ(6),
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255)
);
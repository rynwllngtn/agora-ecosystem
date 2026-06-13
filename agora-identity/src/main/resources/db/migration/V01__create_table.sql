CREATE TYPE identity_status AS ENUM (
    'ACTIVE',
    'DEACTIVATED',
    'SUSPENDED'
);

CREATE TABLE identities (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cpf VARCHAR(11) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    status identity_status NOT NULL,
    created_at TIMESTAMPTZ(6) NOT NULL,
    updated_at TIMESTAMPTZ(6),
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255)
);
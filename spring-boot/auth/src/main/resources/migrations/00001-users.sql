CREATE TYPE iam.EnumUserStatus AS ENUM (
	'ACTIVATED',
	'DEACTIVATED',
	'INACTIVE',
	'DELETING'
	);

-- Tabela users
CREATE TABLE iam.users
(
	cdUser        TEXT                      NOT NULL,
	flStatus      iam.EnumUserStatus        NOT NULL,
	deEmail       TEXT                      NOT NULL,
	nmName        TEXT                      NOT NULL,
	pwPassword    TEXT                      NOT NULL,
	nuPhone       TEXT                      NOT NULL,
	flActivated   BOOLEAN     DEFAULT FALSE NOT NULL,
	dtCreatedAt   TIMESTAMPTZ DEFAULT NOW() NOT NULL,
	dtActivatedAt TIMESTAMPTZ,

	CONSTRAINT pk_users PRIMARY KEY (cdUser)
);

CREATE UNIQUE INDEX uq_users_email ON iam.users (deEmail);

COMMENT ON TABLE iam.users IS 'Tabela que armazena informações sobre os usuários do sistema.';

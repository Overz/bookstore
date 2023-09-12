CREATE TYPE identity.EnumUserStatus AS ENUM (
	'INACTIVE',
	'ACTIVE',
	'DISABLED'
	);

COMMENT ON TYPE identity.EnumUserStatus IS 'User status';

CREATE TABLE identity."user"
(
	cdUser     TEXT                    NOT NULL,
	nmEmail    TEXT                    NOT NULL,
	nuPhone    TEXT                    NOT NULL,
	flStatus   identity.EnumUserStatus NOT NULL,
	pwPassword TEXT                    NOT NULL,

	CONSTRAINT pk_user PRIMARY KEY (cdUser),
	CONSTRAINT uq_user_email UNIQUE (nmEmail)
);

CREATE UNIQUE INDEX uq_idx_user_email ON identity."user" (nmEmail);
COMMENT ON INDEX identity.uq_idx_user_email IS 'Unique index for user email';

COMMENT ON TABLE identity."user" IS 'User data';
COMMENT ON COLUMN identity."user".cdUser IS 'ID';
COMMENT ON COLUMN identity."user".nmEmail IS 'Email';
COMMENT ON COLUMN identity."user".nuPhone IS 'Phone number';
COMMENT ON COLUMN identity."user".flStatus IS 'Account status';
COMMENT ON COLUMN identity."user".pwPassword IS 'Encrypted password';

COMMENT ON CONSTRAINT pk_user ON identity."user" IS 'Primary Key';
COMMENT ON CONSTRAINT uq_user_email ON identity."user" IS 'Unique constraint for user email';

CREATE TYPE iam.EnumStatusUserSession AS ENUM
	(
		'ACTIVE',
		'REVOGATED',
		'EXPIRED'
		);

COMMENT ON TYPE iam.EnumStatusUserSession IS 'Status from user sessions';

CREATE TABLE iam.user_session
(
	cdUserSession TEXT                      NOT NULL,
	cdUser        TEXT                      NOT NULL,
	deToken       TEXT                      NOT NULL,
	deMetadata    TEXT                      NOT NULL,
	dtExpiresAt   BIGINT                    NOT NULL,
	flStatus      iam.EnumStatusUserSession NOT NULL,
	dtLastLogin   TIMESTAMPTZ               NOT NULL DEFAULT NOW(),

	CONSTRAINT pk_usersession_cdusersession PRIMARY KEY (cdUserSession),
	CONSTRAINT fk_usersession_cduser FOREIGN KEY (cdUser) REFERENCES iam.users (cdUser)
);

COMMENT ON TABLE iam.user_session IS 'Sessions that user has logged';
COMMENT ON COLUMN iam.user_session.cdUserSession IS 'ID';
COMMENT ON COLUMN iam.user_session.cdUser IS 'User ID (identity schema)';
COMMENT ON COLUMN iam.user_session.deToken IS 'Token that user has logged';
COMMENT ON COLUMN iam.user_session.deMetadata IS 'Request metadata';
COMMENT ON COLUMN iam.user_session.dtExpiresAt IS 'Session expiration';
COMMENT ON COLUMN iam.user_session.flStatus IS 'Session status';
COMMENT ON COLUMN iam.user_session.dtLastLogin IS 'Last user login';

COMMENT ON CONSTRAINT pk_usersession_cdusersession ON iam.user_session IS 'Primary key constraint';
COMMENT ON CONSTRAINT fk_usersession_cduser ON iam.user_session IS 'Foreign Key Constraint (UserSession - User)';

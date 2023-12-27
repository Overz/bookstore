CREATE TYPE iam.EnumOrganizationStatus AS ENUM (
	'ACTIVATED',
	'DEACTIVATED'
	);

-- Tabela organization
CREATE TABLE organizations
(
	cdOrganization TEXT                       NOT NULL,
	cdOwner        TEXT                       NOT NULL,
	nmOrganization TEXT                       NOT NULL,
	deEmail        TEXT                       NOT NULL,
	dtCreatedAt    TIMESTAMPTZ DEFAULT NOW()  NOT NULL,
	flStatus       iam.EnumOrganizationStatus NOT NULL,

	CONSTRAINT pk_organization PRIMARY KEY (cdOrganization),
	CONSTRAINT fk_organization_user
		FOREIGN KEY (cdOwner) REFERENCES iam.users (cdUser)
);

COMMENT ON TABLE organizations IS 'Tabela que armazena informações sobre organizações no sistema.';

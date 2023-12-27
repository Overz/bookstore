CREATE TYPE iam.EnumPermission AS ENUM (
	'ENABLED',
	'DISABLED'
	);

-- Tabela permissions
CREATE TABLE iam.permissions
(
	cdPermission TEXT               NOT NULL,
	nmPermission TEXT               NOT NULL,
	dePermission TEXT               NOT NULL,
	flStatus     iam.EnumPermission NOT NULL,

	CONSTRAINT pk_permissions PRIMARY KEY (cdPermission)
);

COMMENT ON TABLE iam.permissions IS 'Tabela que armazena informações sobre as permissões dos recursos do sistema.';

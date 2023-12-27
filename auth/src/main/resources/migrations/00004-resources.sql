CREATE TYPE iam.EnumResource AS ENUM (
	'ACTIVATED',
	'DEACTIVATED'
	);

-- Tabela resources
CREATE TABLE iam.resources
(
	cdResource TEXT             NOT NULL,
	nmResource TEXT             NOT NULL,
	deResource TEXT             NOT NULL,
	flResource iam.EnumResource NOT NULL,

	CONSTRAINT pk_resources PRIMARY KEY (cdResource)
);

COMMENT ON TABLE iam.resources IS 'Tabela que armazena informações sobre os recursos do sistema.';

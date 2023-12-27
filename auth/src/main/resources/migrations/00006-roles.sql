-- Tabela roles
CREATE TABLE iam.roles
(
	cdRole     TEXT NOT NULL,
	cdResource TEXT NOT NULL,
	nmRole     TEXT NOT NULL,
	deRole     TEXT NOT NULL,

	CONSTRAINT pk_roles PRIMARY KEY (cdRole),
	CONSTRAINT fk_roles_resources
		FOREIGN KEY (cdResource) REFERENCES iam.resources (cdResource)
);

CREATE UNIQUE INDEX uq_roles_nmroles ON iam.roles (nmRole);

COMMENT ON TABLE iam.roles IS 'Tabela que armazena informações sobre os grupos de permissões.';

-- Tabela context
CREATE TABLE iam.contexts
(
	cdContext      TEXT NOT NULL,
	cdOrganization TEXT NOT NULL,
	cdEntity       TEXT NOT NULL,

	CONSTRAINT pk_contexts PRIMARY KEY (cdContext, cdOrganization, cdEntity),
	CONSTRAINT fk_contexts_organization
	  FOREIGN KEY (cdOrganization) REFERENCES iam.organizations (cdOrganization),
	CONSTRAINT fk_contexts_user
		FOREIGN KEY (cdEntity) REFERENCES iam.users (cdUser)
);

COMMENT ON TABLE iam.contexts IS 'Tabela que representa a abstração para troca de contextos ou associações no sistema.';

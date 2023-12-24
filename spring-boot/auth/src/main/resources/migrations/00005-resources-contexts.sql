-- Tabela entity_resources
CREATE TABLE iam.contexts_resources
(
	cdEntity   TEXT NOT NULL,
	cdResource TEXT NOT NULL,
	CONSTRAINT pk_contextsresources PRIMARY KEY (cdEntity, cdResource),
	CONSTRAINT fk_contextsresources_users
		FOREIGN KEY (cdEntity) REFERENCES iam.users (cdUser),
	CONSTRAINT fk_contextsresources_resources
		FOREIGN KEY (cdResource) REFERENCES iam.resources (cdResource)
);

COMMENT ON TABLE iam.contexts_resources IS 'Tabela que representa o relacionamento n:n entre entidades e recursos.';

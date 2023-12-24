-- Tabela roles_permissions
CREATE TABLE roles_permissions
(
	cdRole       TEXT NOT NULL,
	cdPermission TEXT NOT NULL,

	CONSTRAINT pk_roles_permissions PRIMARY KEY (cdRole, cdPermission),

	CONSTRAINT fk_rolespermissions_roles
		FOREIGN KEY (cdRole) REFERENCES iam.roles (cdRole),

	CONSTRAINT fk_rolespermissions_permissions
		FOREIGN KEY (cdPermission) REFERENCES iam.permissions (cdPermission)
);

COMMENT ON TABLE roles_permissions IS 'Tabela que representa o relacionamento n:n entre grupos de permissões e permissões.';

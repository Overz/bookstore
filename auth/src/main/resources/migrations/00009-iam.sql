-- Enumerações
CREATE TYPE EnumUserStatus AS ENUM ('ACTIVATED', 'DEACTIVATED', 'INACTIVE', 'DELETING');
CREATE TYPE EnumContextType AS ENUM ('NORMAL', 'USER_ASSOCIATION', 'ORGANIZATION');
CREATE TYPE EnumSessionStatus AS ENUM ('ACTIVE', 'EXPIRED', 'REVOGATED');
CREATE TYPE EnumOrganizationStatus AS ENUM ('ACTIVATED', 'DEACTIVATED');
CREATE TYPE EnumPermission AS ENUM ('ENABLED', 'DISABLED');

-- Tabela organization
CREATE TABLE organization
(
	cdOrganization TEXT PRIMARY KEY               NOT NULL,
	cdOwner        TEXT REFERENCES users (cdUser) NOT NULL,
	nmOrganization TEXT                           NOT NULL,
	deEmail        TEXT                           NOT NULL,
	dtCreatedAt    TIMESTAMPTZ DEFAULT NOW()      NOT NULL,
	flStatus       EnumOrganizationStatus         NOT NULL
);

COMMENT ON TABLE organization IS 'Tabela que armazena informações sobre organizações no sistema.';

-- Tabela context
CREATE TABLE context
(
	cdContext      TEXT PRIMARY KEY               NOT NULL,
	cdOrganization TEXT REFERENCES organization (cdOrganization),
	cdEntity       TEXT REFERENCES users (cdUser) NOT NULL
);

COMMENT ON TABLE context IS 'Tabela que representa a abstração para troca de contextos ou associações no sistema.';

-- Tabela users
CREATE TABLE users
(
	cdUser        TEXT PRIMARY KEY          NOT NULL,
	flStatus      EnumUserStatus            NOT NULL,
	deEmail       TEXT                      NOT NULL,
	nmName        TEXT                      NOT NULL,
	pwPassword    TEXT                      NOT NULL,
	nuPhone       TEXT                      NOT NULL,
	flActivated   BOOLEAN     DEFAULT FALSE NOT NULL,
	dtCreatedAt   TIMESTAMPTZ DEFAULT NOW() NOT NULL,
	dtActivatedAt TIMESTAMPTZ,
	CONSTRAINT fk_context_user FOREIGN KEY (cdUser) REFERENCES context (cdEntity)
);

COMMENT ON TABLE users IS 'Tabela que armazena informações sobre os usuários do sistema.';

-- Tabela entity_resources
CREATE TABLE entity_resources
(
	cdEntity   TEXT REFERENCES context (cdEntity)     NOT NULL,
	cdResource TEXT REFERENCES resources (cdResource) NOT NULL,
	PRIMARY KEY (cdEntity, cdResource)
);

COMMENT ON TABLE entity_resources IS 'Tabela que representa o relacionamento n:n entre entidades e recursos.';

-- Tabela resources
CREATE TABLE resources
(
	cdResource TEXT PRIMARY KEY NOT NULL,
	nmResource TEXT             NOT NULL,
	deResource TEXT             NOT NULL,
	flResource EnumResource     NOT NULL
);

COMMENT ON TABLE resources IS 'Tabela que armazena informações sobre os recursos do sistema.';

-- Tabela roles
CREATE TABLE roles
(
	cdRole     TEXT PRIMARY KEY                       NOT NULL,
	cdResource TEXT REFERENCES resources (cdResource) NOT NULL,
	nmRole     TEXT                                   NOT NULL,
	deRole     TEXT                                   NOT NULL
);

COMMENT ON TABLE roles IS 'Tabela que armazena informações sobre os grupos de permissões.';

-- Tabela roles_permissions
CREATE TABLE roles_permissions
(
	cdRole       TEXT REFERENCES roles (cdRole)             NOT NULL,
	cdPermission TEXT REFERENCES permissions (cdPermission) NOT NULL,
	PRIMARY KEY (cdRole, cdPermission)
);

COMMENT ON TABLE roles_permissions IS 'Tabela que representa o relacionamento n:n entre grupos de permissões e permissões.';

-- Tabela permissions
CREATE TABLE permissions
(
	cdPermission TEXT PRIMARY KEY NOT NULL,
	nmPermission TEXT             NOT NULL,
	dePermission TEXT             NOT NULL,
	flStatus     EnumPermission   NOT NULL
);

COMMENT ON TABLE permissions IS 'Tabela que armazena informações sobre as permissões dos recursos do sistema.';

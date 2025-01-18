# Keycloak

- [Documentation](https://www.keycloak.org/docs-api/23.0.3/rest-api/index.html)

## Using

- Configure the `hosts` file in your machine to the `KC_HOSTNAME` value used in [.env.keycloak](.env.keycloak) or [dockerfile](Dockerfile)
- Access with: `http://localhost[:port]/[context-path/]admin/[realm]/console` or `https://localhost/kc` if the docker-compose is running
- [Which OAuth flow should i use?](https://auth0.com/docs/get-started/authentication-and-authorization-flow/which-oauth-2-0-flow-should-i-use)
- [Configure different flows](https://www.thomasvitale.com/keycloak-authentication-flow-sso-client/)

## Config

Keycloak requires the `SSL Certificate` are included to the `keycloak` user with `keycloak` own.

We can change environments variables [here](.env.keycloak)

## Criando com a API

To avoid problems of manually creating realms every time when we drop the data,
keycloak has an automatic import tool, however, the import tool has some problems.

In order to avoid these import problems, an [api](func/src) was made in `python`
to facilitate management of the realms.

Realms need to have some data removed before being sent for creation by [api](func/src), these are:

```json
{
	"roles": {
		"realms": [
			{
				"id": ".....",
				"name": "uma_authorization",
				"description": "",
				"composite": false,
				"clientRole": false,
				"containerId": "....",
				"attributes": {}
			},
			{
				"id": ".....",
				"name": "offline_access",
				"description": "",
				"composite": false,
				"clientRole": false,
				"containerId": "....",
				"attributes": {}
			}
		]
	},
	"clients": {
		"authorizationSettings": {
			"policies": [
				{
					"id": ".....",
					"name": "Default Policy",
					"description": "A policy that grants access only for users within this realm",
					"type": "js",
					"logic": "POSITIVE",
					"decisionStrategy": "AFFIRMATIVE",
					"config": {
						"code": "// by default, grants any permission associated with this policy\n$evaluation.grant();\n"
					}
				},
				{
					"id": ".....",
					"name": "Default Permission",
					"description": "A permission that applies to the default resource type",
					"type": "resource",
					"logic": "POSITIVE",
					"decisionStrategy": "UNANIMOUS",
					"config": {
						"defaultResourceType": "urn:20e0f234-913e-41c0-878b-53d933697fbb:resources:default",
						"applyPolicies": "[\"Default Policy\"]"
					}
				}
			]
		}
	}
}
```

---

## Email

https://www.hostinger.com.br/tutoriais/aprenda-a-utilizar-o-smtp-google


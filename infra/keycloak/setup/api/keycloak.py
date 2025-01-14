import json

import requests
from requests import Response

from definitions import RealmException, ValidationException, UserException, Realm, AuthenticationException

NOT_IMPLEMENTED = ValidationException('não implementado')

# cache
c: dict[str, any] = {}
s = requests.session()
s.verify = False
s.headers['Content-Type'] = 'application/json'


def auth(o: dict[str, any]):
	print("autenticando...")
	r = requests.post(
		f'{c["url"]}/realms/master/protocol/openid-connect/token',
		o,
		headers={'Content-Type': 'application/x-www-form-urlencoded'},
		verify=False
	)

	if r.status_code != 200:
		raise AuthenticationException('erro realizando autenticação/revalidação')

	body = r.json()
	c['access_token'] = body['access_token']
	c['refresh_token'] = body['refresh_token']
	s.headers['Authorization'] = f'Bearer {body["access_token"]}'


def handle_unauthorized(r: Response, *args, **kwargs):
	if r.status_code == 401:
		if c.get('refresh_token') is not None:
			auth({
				'client_id': c['client_id'],
				'grant_type': 'refresh_token',
				'refresh_token': c['refresh_token']
			})
		else:
			auth({
				'client_id': c['client_id'],
				'username': c['username'],
				'password': c['password'],
				'grant_type': 'password',
			})

		r.request.headers['Authorization'] = f'Bearer {c["access_token"]}'
		return s.send(r.request)


s.hooks['response'] = [handle_unauthorized]


def create_realm(o: Realm) -> None:
	if o.is_master:
		return

	print(f'criando realm {o.name}')

	r = s.post(f'{c["url"]}/admin/realms', json.dumps(o.data))

	if r.status_code != 201:
		raise RealmException(f'não foi possível criar o realm, status: {r.status_code}, realm: {o.name}', r.json())

	print(f'realm "{o.name}" criado com sucesso, status: {r.status_code}')


def update_realm(o: Realm) -> None:
	print(f'atualizando realm {o.name}')

	r = s.put(f'{c["url"]}/admin/realms/{o.data["realm"]}', json.dumps(o.data))

	if r.status_code != 204:
		raise RealmException(f'não foi possível atualizar o realm, status: {r.status_code}, realm: {o.name}', r.json())

	print(f'realm "{o.name}" atualizado com sucesso, status: {r.status_code}')


def create_user(o: Realm) -> None:
	if o.is_master:
		return

	for u in o.users:
		print(f'criando usuário {u["username"]} no realm {o.name}')

		r = s.post(f'{c["url"]}/admin/realms/{o.name}/users', json.dumps(u))

		if r.status_code != 201:
			raise UserException(
				f'não foi possível criar usuário "{u["username"]}", status: {r.status_code}, realm: {o.name}', r.json()
			)

		print(f'usuário "{u["username"]}" criado com sucesso, realm: {o.name}, status: {r.status_code}')

		u["id"] = get_user_id_by_username(u["username"], o.name)

		r = s.put(
			f'{c["url"]}/admin/realms/{o.name}/users/{u["id"]}/reset-password',
			json.dumps({'type': 'password', 'temporary': False, 'value': '123456'})
		)

		if r.status_code != 204:
			raise UserException(
				f'não foi possível criar a senha do usuário "{u["username"]}", status: {r.status_code}, realm: {o.name}'
			)


def update_user(o: Realm) -> None:
	for u in o.users:
		print(f'atualizando usuário {u["username"]} no realm {o.name}')

		u["id"] = get_user_id_by_username(u['username'], o.name)

		r = s.put(f'{c["url"]}/admin/realms/{o.name}/users/{u["id"]}', json.dumps(u))

		if r.status_code != 204:
			raise UserException(f'não foi possível atualizar usuário no realm: {o.name}', r.json())

		print(f'usuário "{u["username"]}" atualizado com sucesso, realm: {o.name}, status: {r.status_code}')


def get_user_id_by_username(username: str, realm: str) -> str:
	r = s.get(f'{c["url"]}/admin/realms/{realm}/users?username={username}')

	if r.status_code == 404:
		raise ValidationException(f"nenhum usuário '{username}' encontrado")

	body = r.json()
	if len(body) > 1:
		raise ValidationException(
			f'mais de um usuário {username} encontrado, não é possível atualizar o id em memória, realm: {realm}'
		)

	return body[0]["id"]

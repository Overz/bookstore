import json
import os
import re
import sys
from os import path
from typing import List, Callable

import urllib3
from dotenv import load_dotenv

import keycloak
from definitions import Realm, ValidationException, Action, SetupException

urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

BASE_DIR = path.abspath(path.dirname(path.dirname(__file__)))

if not load_dotenv(f'{BASE_DIR}/.env', verbose=True):
	raise SetupException('erro carregando variáveis de ambiente')

DATA_DIR = path.join(BASE_DIR, "data")
NOT_IMPLEMENTED = SetupException("não implementado")
is_master_pattern = re.compile("^.*(master|admin).*$", re.IGNORECASE)


def setup_data_get_name(root: str, r: Realm):
	parts = root.split("/")
	i = parts.index("data") if "data" in parts else -1
	if i != -1 and i < len(parts) - 1:
		r.name = parts[i + 1]
		r.is_master = bool(is_master_pattern.match(r.name))

	if r.name == "":
		raise ValidationException(f"não foi possível encontrar o nome do realm em: {root}")


def setup_data_get_content(root: str, file: str, r: Realm):
	with open(path.join(root, file), 'r') as f:
		content = json.load(f)
		f.close()

		if file == 'realm.json':
			r.data = content
		elif root.endswith("clients"):
			return
		elif root.endswith("roles"):
			return
		elif root.endswith("users"):
			r.users.append(content)

		if root.endswith("users") and r.is_master and len(r.users) > 0:
			r.users[0]["email"] = os.getenv("KEYCLOAK_SMTP_EMAIL")


def setup_save_data(realm: Realm, data: List[Realm]):
	existing_realm = next((item for item in data if item.name == realm.name), None)

	if existing_realm:
		if not existing_realm.data:
			existing_realm.data = realm.data

		for item in realm.users:
			if item not in existing_realm.users:
				existing_realm.users.append(item)
	else:
		data.append(realm)


def setup_data() -> List[Realm]:
	data: List[Realm] = []
	for root, dirs, files in os.walk(DATA_DIR):
		for file in files:
			if file.endswith('.json'):
				realm = Realm('', False, {}, [])
				setup_data_get_name(root, realm)
				setup_data_get_content(root, file, realm)
				setup_save_data(realm, data)

	return data


def main():
	try:
		keycloak.c["url"] = os.getenv("KEYCLOAK_URL")
		keycloak.c["client_id"] = os.getenv('KEYCLOAK_CLIENT_ID')
		keycloak.c["username"] = os.getenv('KEYCLOAK_USER')
		keycloak.c["password"] = os.getenv('KEYCLOAK_PASSWORD')

		data = setup_data()

		def process(func: Callable[[Realm], None]):
			for realm in data:
				func(realm)

		for arg in sys.argv[1:]:
			key = Action.cast(arg)
			if key == Action.CREATE:
				process(keycloak.create_realm)
				process(keycloak.create_user)
			elif key == Action.UPDATE:
				process(keycloak.update_realm)
				process(keycloak.update_user)
			elif key == Action.DELETE:
				raise NOT_IMPLEMENTED
			elif key == Action.EXPORT:
				raise NOT_IMPLEMENTED

	except Exception as e:
		print(f"Erro[{e.__class__.__name__}]: \n", e)
		sys.exit(1)


if __name__ == "__main__":
	main()
	print("Processamento finalizado!")

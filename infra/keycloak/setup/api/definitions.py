from dataclasses import dataclass
from enum import Enum
from typing import List, Dict, Any


class AuthenticationException(Exception):
	pass


class SetupException(Exception):
	pass


class RealmException(Exception):
	pass


class UserException(Exception):
	pass


class ValidationException(Exception):
	pass


@dataclass
class Realm:
	name: str
	is_master: bool
	data: Dict[str, Any]
	users: List[Dict[str, Any]]


class Action(Enum):
	CREATE = 'create'
	UPDATE = 'update'
	DELETE = 'delete'
	EXPORT = 'export'

	@classmethod
	def values(cls):
		data = []
		for v in list(cls):
			data.append(v.value)

		return data

	@classmethod
	def cast(cls, value: str):
		value = value.lower()
		for action in list(cls):
			if action.value == value:
				return action

		raise ValidationException(f"argumento não encontrado, aceitos: {cls.values()}")

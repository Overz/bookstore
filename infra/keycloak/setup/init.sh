#!/bin/bash

# Verificar se o comando python3 -m venv --help existe
if ! command -v python3 -m venv --help &> /dev/null; then
	echo "Please, install virtual env (venv)"
fi

pip install pipenv


rm -rfv ./tmp/venv

echo "Criando virtual env"
python -m venv ./tmp/venv

echo "Ativando virtual env"
source ./tmp/venv/bin/activate

#pip install --upgrade pip

echo "Instalando dependências"
pip install -r ./requirements.txt

#echo "Execute: "
#echo "source ./venv/bin/activate"
#echo "pip install -r ./requirements.txt"
#echo ""
#echo "Para sair:"
#echo "deactivate"

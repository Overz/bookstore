#!/bin/bash

# Verificar se o comando python3 -m venv --help existe
if ! command -v python3 -m venv --help &> /dev/null
then
    echo "Comando python3 -m venv --help não encontrado. Instalando python3.10-venv..."
    sudo apt-get install python3.10-venv
fi

python3 -m venv venv

echo "Execute: "
echo "source ./venv/bin/activate"
echo "pip install -r ./requirements.txt"
echo ""
echo "Para sair:"
echo "deactivate"

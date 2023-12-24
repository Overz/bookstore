import psycopg2

# Parâmetros de conexão
host = 'localhost'
port = 5432  # Porta padrão do PostgreSQL
database_name = 'bookstore'
user = 'postgres'
password = 'postgres'

# Construir a string de conexão
connection_string = f"host={host} port={port} dbname={database_name} user={user} password={password}"

# Conectar ao banco de dados
connection = psycopg2.connect(connection_string)

# Exemplo: Executar uma consulta
with connection.cursor() as cursor:
    cursor.execute("select now()")
    result = cursor.fetchall()
    print(result)

# Fechar a conexão
connection.close()

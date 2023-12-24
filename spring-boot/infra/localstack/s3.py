import boto3

# Configurar o cliente do S3 com o endpoint do LocalStack
s3 = boto3.client('s3', endpoint_url='http://localhost:4566')

# Exemplo: Listar os buckets
response = s3.list_buckets()
print("Buckets disponíveis:", response)

package com.bookstore.infra.cloud.aws;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.DecryptRequest;
import software.amazon.awssdk.services.kms.model.DecryptResponse;
import software.amazon.awssdk.services.kms.model.EncryptRequest;
import software.amazon.awssdk.services.kms.model.EncryptResponse;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Teste {

	private static final String KMS_KEY_ARN = "arn:aws:kms:us-east-1:YOUR_ACCOUNT_ID:key/YOUR_KEY_ID";
	private static final String PLAIN_TEXT = "Hello, World!";
	private static final String SIGNING_ALGORITHM = "SHA256withRSA";

	public void teste() throws Exception {
		// Configurar as credenciais da AWS
		DefaultCredentialsProvider credentialsProvider = DefaultCredentialsProvider.create();

		// Criar uma instância do cliente AWS KMS
		KmsClient kmsClient = KmsClient.builder()
			.region(Region.US_EAST_1)
			.credentialsProvider(credentialsProvider)
			.build();

		// Criptografar texto usando o AWS KMS
		EncryptRequest encryptRequest = EncryptRequest.builder()
			.keyId(KMS_KEY_ARN)
			.plaintext(SdkBytes.fromByteArray(PLAIN_TEXT.getBytes(StandardCharsets.UTF_8)))
			.build();

		EncryptResponse encryptResponse = kmsClient.encrypt(encryptRequest);
		String encryptedData = Base64.getEncoder().encodeToString(encryptResponse.ciphertextBlob().asByteArray());

		System.out.println("Texto criptografado: " + encryptedData);

		// Descriptografar texto usando o AWS KMS
		DecryptRequest decryptRequest = DecryptRequest.builder()
			.keyId(KMS_KEY_ARN)
			.ciphertextBlob(encryptResponse.ciphertextBlob())
			.build();

		DecryptResponse decryptResponse = kmsClient.decrypt(decryptRequest);
		String decryptedData = new String(decryptResponse.plaintext().asByteArray(), StandardCharsets.UTF_8);

		System.out.println("Texto descriptografado: " + decryptedData);

		// Assinar e verificar JWT usando chave RSA do KMS
		String privateKeyPem = "<private_key_pem>"; // Substitua pelo valor real da chave privada em formato PEM
		String publicKeyPem = "<public_key_pem>"; // Substitua pelo valor real da chave pública em formato PEM

		// Carregar a chave privada
		byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPem);
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		// Assinar JWT usando a chave privada
		Signature signer = Signature.getInstance(SIGNING_ALGORITHM);
		signer.initSign(privateKey);
		signer.update(PLAIN_TEXT.getBytes(StandardCharsets.UTF_8));
		byte[] signatureBytes = signer.sign();
		String signature = Base64.getEncoder().encodeToString(signatureBytes);

		System.out.println("Assinatura JWT: " + signature);

		// Carregar a chave pública
		byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPem);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		// Verificar a assinatura JWT usando a chave pública
		Signature verifier = Signature.getInstance(SIGNING_ALGORITHM);
		verifier.initVerify(publicKey);
		verifier.update(PLAIN_TEXT.getBytes(StandardCharsets.UTF_8));
		boolean verified = verifier.verify(Base64.getDecoder().decode(signature));

		System.out.println("Assinatura verificada: " + verified);
	}
}

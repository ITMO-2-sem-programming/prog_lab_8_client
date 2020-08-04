package ru.itmo.core.encryption;

public class Encryptor {
    private EncryptionAlgorithm algorithm;


    public Encryptor(EncryptionAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String encrypt(String string) {
        return algorithm.encrypt(string);
    }


    public EncryptionAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(EncryptionAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}

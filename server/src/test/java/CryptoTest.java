import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CryptoTest extends BaseTest {

    @Autowired
    private CryptoSuite cryptoSuite;

    @Test
    public void hash() {
        String data = "123456";
        assertEquals(cryptoSuite.getHashImpl().hash(data), "207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb");
        assertEquals(cryptoSuite.getHashImpl().hashBytes(data.getBytes()), "207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb");
    }

    @Test
    public void sign() {
        String privateKey = "80407410d609df74fd97be77ff1a10b8b80821a7fa3bd1eb773d5d8067f9cb8a";
        CryptoKeyPair keyPair = cryptoSuite.createKeyPair(privateKey);
        assertEquals(keyPair.getAddress(), "0x9bd3ae811324424bf3a29fcca7a9a317b735e869");
        assertEquals(keyPair.getHexPrivateKey(), "80407410d609df74fd97be77ff1a10b8b80821a7fa3bd1eb773d5d8067f9cb8a");
        assertEquals(keyPair.getHexPublicKey(), "04fed66716505ae8b1454683dfa3cdbe4ea14c02bd1199badb9e501195e11056dc433e88dc82934dd6be12bf93537cf8a8547fb2adc507862362db60b5fe8cab6a");

        String data = "123456";
        String dataSign = cryptoSuite.sign(data, keyPair).convertToString();
        boolean verifyResult = cryptoSuite.verify(keyPair.getHexPublicKey(), data, dataSign);
        assertTrue(verifyResult);
    }
}

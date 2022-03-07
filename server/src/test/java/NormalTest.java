import cn.edu.ipfs.components.IPFSService;
import cn.hutool.http.HttpUtil;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class NormalTest extends BaseTest {

    @Autowired
    private CryptoSuite cryptoSuite;

    @Autowired
    private IPFSService ipfsService;

    @Test
    public void ipfs() {
        String hash = ipfsService.addFile("hello, world".getBytes());
        assertEquals(new String(ipfsService.catFile(hash)), "hello, world");
    }

    @Test
    public void download() {
        String url = ipfsService.getGateway() + "/" + "QmWWzUvnm39fH3oxgh4YnCAPZFfStmtkRo9FrizjMqoY52";
        byte[] content = HttpUtil.downloadBytes(url);
        assertEquals(cryptoSuite.getHashImpl().hashBytes(content), "98b8eb3e45aad032494f124699f1388e485a5f0bfb31dbf259d9db6b655221f8");
        assertEquals(ipfsService.addFile(content), "QmWWzUvnm39fH3oxgh4YnCAPZFfStmtkRo9FrizjMqoY52");
    }
}

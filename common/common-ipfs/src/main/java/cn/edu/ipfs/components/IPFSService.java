package cn.edu.ipfs.components;

import cn.edu.core.base.GlobalException;
import cn.edu.core.base.Status;
import cn.edu.ipfs.config.IPFSConfig;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@AllArgsConstructor
public class IPFSService {

    private final IPFSConfig ipfsConfig;

    /**
     * 上传文件 File
     */
    public String addFile(File content) {
        try {
            IPFS ipfs = new IPFS(ipfsConfig.getHost(), ipfsConfig.getPort());
            NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(content);
            MerkleNode node = ipfs.add(file).get(0);
            return node.hash.toBase58();
        } catch (IOException e) {
            throw new GlobalException(Status.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 上传文件 byte[]
     */
    public String addFile(byte[] content) {
        try {
            IPFS ipfs = new IPFS(ipfsConfig.getHost(), ipfsConfig.getPort());
            NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(content);
            MerkleNode node = ipfs.add(file).get(0);
            return node.hash.toBase58();
        } catch (IOException e) {
            throw new GlobalException(Status.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 下载文件
     */
    public byte[] catFile(String hash) {
        try {
            IPFS ipfs = new IPFS(ipfsConfig.getHost(), ipfsConfig.getPort());
            Multihash CID = Multihash.fromBase58(hash);
            return ipfs.cat(CID);
        } catch (IOException e) {
            throw new GlobalException(Status.FILE_DOWNLOAD_ERROR);
        }
    }

    /**
     * 获取网关
     */
    public String getGateway() {
        return ipfsConfig.getGateway();
    }
}

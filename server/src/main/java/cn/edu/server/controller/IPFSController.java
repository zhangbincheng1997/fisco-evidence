package cn.edu.server.controller;

import cn.edu.core.base.Result;
import cn.edu.core.utils.Constants;
import cn.edu.ipfs.components.IPFSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Api(tags = "【IPFS存储服务】")
@RestController
@RequestMapping(Constants.API + "/ipfs")
@AllArgsConstructor
public class IPFSController {

    private final IPFSService ipfsService;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return Result.success(ipfsService.addFile(file.getBytes()));
    }

    @ApiOperation("下载文件")
    @GetMapping("/{hash}")
    public void download(@PathVariable("hash") String hash, HttpServletResponse response) throws IOException {
        byte[] content = ipfsService.catFile(hash);
        OutputStream stream = response.getOutputStream();
        stream.write(content);
        stream.flush();
        stream.close();
    }

    @ApiOperation("获取IPFS网关")
    @GetMapping("/gateway")
    public Result<String> gateway() {
        return Result.success(ipfsService.getGateway());
    }
}

package cn.edu.server.controller;

import cn.edu.core.base.GlobalException;
import cn.edu.core.base.Result;
import cn.edu.core.base.Status;
import cn.edu.core.utils.Constants;
import cn.edu.mbg.entity.App;
import cn.edu.mbg.entity.AppUser;
import cn.edu.mbg.service.AppService;
import cn.edu.mbg.service.AppUserService;
import cn.edu.redis.component.RedisService;
import cn.edu.server.pojo.dto.AppDTO;
import cn.edu.server.pojo.dto.TokenDTO;
import cn.edu.server.pojo.dto.UserDTO;
import cn.edu.server.pojo.model.KeyModel;
import cn.edu.server.pojo.model.TokenModel;
import cn.edu.server.service.EvidenceService;
import cn.edu.server.utils.AppUtils;
import cn.edu.web.context.AppContext;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Api(tags = "【应用服务】")
@RestController
@RequestMapping(Constants.API + "/app")
@AllArgsConstructor
public class AppController {

    private final AppService appService;
    private final AppUserService appUserService;
    private final CryptoSuite cryptoSuite;
    private final EvidenceService evidenceService;
    private final RedisService redisService;

    @ApiOperation("获取AccessToken")
    @PostMapping("/token")
    public Result<TokenModel> token(@RequestBody @Valid TokenDTO DTO) {
        String appId = DTO.getAppId();
        String appSecret = DTO.getAppSecret();
        App app = appService.getOne(Wrappers.<App>lambdaQuery().eq(App::getAppId, appId));
        if (app == null) {
            throw new GlobalException(Status.APP_ID_ERROR);
        }
        if (!app.getAppSecret().equals(appSecret)) {
            throw new GlobalException(Status.APP_SECRET_ERROR);
        }
        if (!app.getEnabled()) {
            throw new GlobalException(Status.APP_DISABLE);
        }
        String accessToken = UUID.randomUUID().toString().replace("-", "");
        redisService.set(Constants.TOKEN_NAME + accessToken, appId, Constants.TOKEN_EXPIRES);
        return Result.success(new TokenModel(accessToken, Constants.TOKEN_EXPIRES));
    }

    @ApiOperation("申请应用")
    @PostMapping("/create/app")
    public Result<App> app(@RequestBody @Valid AppDTO DTO) {
        // 创建AppId、AppSecret
        String appId = AppUtils.getAppId();
        String appSecret = AppUtils.getAppSecret();
        // 保存到数据库
        String appName = DTO.getAppName();
        String ext = DTO.getExt();
        App app = new App();
        app.setAppId(appId);
        app.setAppSecret(appSecret);
        app.setAppName(appName);
        app.setExt(ext);
        app.setEnabled(true);
        appService.save(app);
        // 注册到区块链
        evidenceService.createApp(appId);
        return Result.success(app);
    }

    @ApiOperation("创建用户")
    @PostMapping("/create/user")
    public Result<KeyModel> user(@RequestBody @Valid UserDTO DTO) {
        // 创建密钥对
        CryptoKeyPair keyPair = cryptoSuite.createKeyPair();
        String userAddr = keyPair.getAddress();
        // 保存到数据库
        String appId = AppContext.get();
        String userName = DTO.getUserName();
        String ext = DTO.getExt();
        AppUser appUser = new AppUser();
        appUser.setAppId(appId);
        appUser.setUserName(userName);
        appUser.setUserAddr(userAddr);
        appUser.setExt(ext);
        appUserService.save(appUser);
        // 注册到区块链
        evidenceService.createUser(appId, userAddr);
        return Result.success(new KeyModel(userAddr, keyPair.getHexPrivateKey(), keyPair.getHexPublicKey()));
    }
}

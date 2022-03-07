package cn.edu.mbg.service.impl;

import cn.edu.mbg.entity.AppUser;
import cn.edu.mbg.mapper.AppUserMapper;
import cn.edu.mbg.service.AppUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements AppUserService {

}

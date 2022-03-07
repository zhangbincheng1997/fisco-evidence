package cn.edu.mbg.service.impl;

import cn.edu.mbg.entity.App;
import cn.edu.mbg.mapper.AppMapper;
import cn.edu.mbg.service.AppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

}

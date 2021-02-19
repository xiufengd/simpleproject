package com.xiufengd.service.impl;

import com.xiufengd.domain.SysUser;
import com.xiufengd.mapper.SysUserMapper;
import com.xiufengd.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getUser(String id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }
}

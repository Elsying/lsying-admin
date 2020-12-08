package io.geekidea.springbootplus.test;

import cn.hutool.core.lang.Console;
import io.geekidea.springbootplus.config.properties.SpringBootPlusProperties;
import io.geekidea.springbootplus.framework.util.ExcelUtil;
import io.geekidea.springbootplus.system.entity.SysUser;
import io.geekidea.springbootplus.system.service.IMailService;
import io.geekidea.springbootplus.system.service.SysUserService;
import io.geekidea.springbootplus.system.vo.SysUserQueryVo;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    /**
     * 注入发送邮件的接口
     */
    @Autowired
    private IMailService mailService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SpringBootPlusProperties springBootPlusProperties;

    /**
     * 测试发送文本邮件
     */
    @Test
    public void sendmail() throws Exception {
        StringBuilder abc= new StringBuilder();
        for (int a=0;a<100;a++){
            abc.append("tiaxia");
        }
        ExcelUtil.writeToExcel(springBootPlusProperties.getUploadPath()+"data.xlsx", SysUser.class, sysUserService.getSysUserList());

        mailService.sendAttachmentsMail("1352665867@qq.com","data", "数据",springBootPlusProperties.getUploadPath()+"data.xlsx");
    }
}

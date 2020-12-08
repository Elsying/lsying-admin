/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.geekidea.springbootplus.scheduled;

import io.geekidea.springbootplus.config.properties.SpringBootPlusProperties;
import io.geekidea.springbootplus.framework.util.ExcelUtil;
import io.geekidea.springbootplus.system.entity.SysUser;
import io.geekidea.springbootplus.system.service.IMailService;
import io.geekidea.springbootplus.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author geekidea
 * @date 2020/3/16
 **/
@Slf4j
@Component
public class HelloScheduled {
    @Autowired
    private IMailService mailService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SpringBootPlusProperties springBootPlusProperties;

    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void hello1() throws Exception {
        //每两小时发送邮箱
        ExcelUtil.writeToExcel(springBootPlusProperties.getUploadPath()+"data.xlsx", SysUser.class, sysUserService.getSysUserList());
        mailService.sendAttachmentsMail("1352665867@qq.com","data", "数据",springBootPlusProperties.getUploadPath()+"data.xlsx");
    }



}

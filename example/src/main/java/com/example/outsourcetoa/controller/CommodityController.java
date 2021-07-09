package com.example.outsourcetoa.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import com.example.outsourcetoa.entity.Commodity;
import com.example.outsourcetoa.service.CommodityService;
import com.example.outsourcetoa.vo.CommodityVo;
import io.geekidea.springbootplus.config.properties.SpringBootPlusProperties;
import io.geekidea.springbootplus.framework.util.DownloadUtil;
import io.geekidea.springbootplus.framework.util.ImageUtils;
import io.geekidea.springbootplus.framework.util.UploadUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import com.example.outsourcetoa.param.CommodityPageParam;
import io.geekidea.springbootplus.framework.common.controller.BaseController;
import io.geekidea.springbootplus.framework.common.api.ApiResult;
import io.geekidea.springbootplus.framework.core.pagination.Paging;
import io.geekidea.springbootplus.framework.common.param.IdParam;
import io.geekidea.springbootplus.framework.log.annotation.Module;
import io.geekidea.springbootplus.framework.log.annotation.OperationLog;
import io.geekidea.springbootplus.framework.log.enums.OperationLogType;
import io.geekidea.springbootplus.framework.core.validator.groups.Add;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *  控制器
 *
 * @author lsy
 * @since 2020-12-17
 */
@Slf4j
@RestController
@RequestMapping("/commodity")
@Module("outsourcetoa")
@Api(value = "API", tags = {"商品管理"})
public class CommodityController extends BaseController {

    @Autowired
    private CommodityService commodityService;
    @Autowired
    private SpringBootPlusProperties springBootPlusProperties;

    /**
     * 添加
     */
    @PostMapping("/add")
    @OperationLog(name = "添加", type = OperationLogType.ADD)
    @ApiOperation(value = "添加", response = ApiResult.class)
    public ApiResult<Boolean> addCommodity(@Validated(Add.class) @RequestBody Commodity commodity) throws Exception {
        Console.log(commodity.getDescription()+"-------------------------------");
        Console.log(StringEscapeUtils.unescapeHtml4(commodity.getDescription())+"-------------------------------");
        commodity.setDescription(StringEscapeUtils.unescapeHtml4(commodity.getDescription()));
        commodity.setName(StringEscapeUtils.unescapeHtml4(commodity.getName()));
        boolean flag = commodityService.saveCommodity(commodity);
        return ApiResult.result(flag);
    }


    /**
     * 添加
     */
    @PostMapping("/copy/{did}")
    @OperationLog(name = "复制", type = OperationLogType.ADD)
    @ApiOperation(value = "复制", response = ApiResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "did", required = true, value = "设备id"),
    })
    public ApiResult<Boolean> copyCommodity(@Validated(Add.class) @RequestBody Commodity commodity,@PathVariable("did") Integer did) throws Exception {
        commodity.setName(StringEscapeUtils.unescapeHtml4(commodity.getName()));
        commodity.setDescription(StringEscapeUtils.unescapeHtml4(commodity.getDescription()));
        boolean flag = commodityService.copyCommodity(commodity,did);
        return ApiResult.result(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @OperationLog(name = "修改", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改", response = ApiResult.class)
    public ApiResult<Boolean> updateCommodity(@Validated(Update.class) @RequestBody Commodity commodity) throws Exception {
        commodity.setName(StringEscapeUtils.unescapeHtml4(commodity.getName()));
        commodity.setDescription(StringEscapeUtils.unescapeHtml4(commodity.getDescription()));
        boolean flag = commodityService.updateCommodity(commodity);
        return ApiResult.result(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @OperationLog(name = "删除", type = OperationLogType.DELETE)
    @ApiOperation(value = "删除", response = ApiResult.class)
    public ApiResult<Boolean> deleteCommodity(@PathVariable("id") Long id) throws Exception {
        boolean flag = commodityService.deleteCommodity(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @OperationLog(name = "详情", type = OperationLogType.INFO)
    @ApiOperation(value = "详情", response = Commodity.class)
    public ApiResult<Commodity> getCommodity(@PathVariable("id") Long id) throws Exception {
        Commodity commodity = commodityService.getById(id);
        return ApiResult.ok(commodity);
    }

    /**
     * 列表-根据设备编号和排列号
     */
    @GetMapping("/getListBydnumber/{dnumber}/{index}")
    @OperationLog(name = "列表-根据设备编号和排列号", type = OperationLogType.PAGE)
    @ApiOperation(value = "列表-根据设备编号和排列号", response = Commodity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dnumber", required = true, value = "设备编号"),
            @ApiImplicitParam(name = "index", required = true, value = "排列号(1，2，3)"),
    })
    public ApiResult<Object> getCommodityPageListBydnumberandindex(@PathVariable("dnumber") String dnumber,@PathVariable("index")Integer index) throws Exception {
        List<Commodity> res=commodityService.getCommodityPageListBydnumber(dnumber);
        if(res==null){
            return ApiResult.ok("无设备号");
        }
        else if(res.size()==0||index>res.size()){
            return ApiResult.ok("无商品");
        }
        else {
            return ApiResult.ok(res.get(index-1));
        }

    }

    /**
     * 列表-根据设备编号
     */
    @GetMapping("/getListBydnumber/{dnumber}")
    @OperationLog(name = "列表-根据设备编号", type = OperationLogType.PAGE)
    @ApiOperation(value = "列表-根据设备编号", response = Commodity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dnumber", required = true, value = "设备编号"),
    })
    public ApiResult<List<Commodity>> getCommodityPageListBydnumber(@PathVariable("dnumber") String dnumber) throws Exception {
        return ApiResult.ok(commodityService.getCommodityPageListBydnumber(dnumber));
    }

    /**
     * 分页列表-根据设备id
     */
    @PostMapping("/getPageListBydid/{did}")
    @OperationLog(name = "分页列表-根据设备id", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表-根据设备id", response = Commodity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "did", required = true, value = "设备id"),
    })
    public ApiResult<Paging<CommodityVo>> getCommodityPageListBydid(@Validated @RequestBody CommodityPageParam commodityPageParam, @PathVariable("did") Integer did) throws Exception {
        Paging<CommodityVo> paging = commodityService.getCommodityPageListBydid(commodityPageParam,did);
        return ApiResult.ok(paging);
    }


    /**
     * 分页列表-获取所有
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("admin:commodity:page")
    @OperationLog(name = "分页列表-获取所有", type = OperationLogType.PAGE)
    @ApiOperation(value = "分页列表-获取所有", response = Commodity.class)
    public ApiResult<Paging<CommodityVo>> getCommodityPageList(@Validated @RequestBody CommodityPageParam commodityPageParam) throws Exception {
        Paging<CommodityVo> paging = commodityService.getCommodityPageList(commodityPageParam);
        return ApiResult.ok(paging);
    }

    /**
     * 上传商品图片
     * @return
     */
    @PostMapping("/upload")
    @OperationLog(name = "上传商品图片", type = OperationLogType.UPLOAD)
    @ApiOperation(value = "上传商品图片", response = ApiResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files",value = "多个文件，",paramType = "formData",allowMultiple=true,required = true,dataType = "file"),
            @ApiImplicitParam(name = "deviceid", value = "设备编号",required = true),
            @ApiImplicitParam(name = "upstart", value = "距上方随机数开始"),
            @ApiImplicitParam(name = "upend", value = "距上方随机数结束"),
            @ApiImplicitParam(name = "leftstart", value = "距左方随机数开始"),
            @ApiImplicitParam(name = "leftend", value = "距左方随机数结束")
    })
    public ApiResult<List<String>> uploads(@RequestParam("files") MultipartFile[] multipartFilelist,@RequestParam("deviceid") String deviceid,
                                           @RequestParam("upstart") Integer upstart, @RequestParam("upend") Integer upend,
                                           @RequestParam("leftstart") Integer leftstart, @RequestParam("leftend") Integer leftend) throws Exception {
        List<String> pathname=new ArrayList<>();
        for (MultipartFile multipartFile:multipartFilelist) {
            log.info("multipartFile = " + multipartFile);
            log.info("ContentType = " + multipartFile.getContentType());
            log.info("OriginalFilename = " + multipartFile.getOriginalFilename());
            log.info("Name = " + multipartFile.getName());
            log.info("Size = " + multipartFile.getSize());
            // 上传文件，返回保存的文件名称
            String saveFileName = UploadUtil.upload(springBootPlusProperties.getUploadPath(), multipartFile, originalFilename -> {
                // 文件后缀
                String fileExtension = FilenameUtils.getExtension(originalFilename);
                // 这里可自定义文件名称，比如按照业务类型/文件格式/日期
                String dateString = deviceid+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssS")) + RandomStringUtils.randomNumeric(6);
                String fileName = dateString + "." + fileExtension;
                return fileName;
            });
//            if(upstart == null && upend == null & leftstart == null && leftend == null){
//                //原图
//                Console.log("不裁剪");
//            }
//            else
//                ImageUtils.cut(springBootPlusProperties.getUploadPath()+saveFileName,
//                        springBootPlusProperties.getUploadPath()+saveFileName,
//                        ThreadLocalRandom.current().nextInt(leftstart==null?0:leftstart,leftend),ThreadLocalRandom.current().nextInt(upstart==null?0:upstart,upend==null?0:upend));
//
            // 上传成功之后，返回访问路径，请根据实际情况设置
            String fileAccessPath = springBootPlusProperties.getResourceAccessUrl() + saveFileName;
            log.info("fileAccessPath:{}", fileAccessPath);
            pathname.add(fileAccessPath);
        }
        return ApiResult.ok(pathname);
    }

    /**
     * 下载商品图片
     */
    @GetMapping("/download")
    @OperationLog(name = "下载商品图片", type = OperationLogType.download)
    @ApiOperation(value = "下载商品图片", notes = "下载文件", response = ApiResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "upstart", value = "裁剪范围距上方随机数开始"),
            @ApiImplicitParam(name = "upend", value = "裁剪范围距上方随机数结束"),
            @ApiImplicitParam(name = "leftstart", value = "裁剪范围距左方随机数开始"),
            @ApiImplicitParam(name = "leftend", value = "裁剪范围距左方随机数结束")
    })
    public void downloads(@RequestParam(required = true) String downloadFileName,@RequestParam("upstart") Integer upstart, @RequestParam("upend") Integer upend,
                          @RequestParam("leftstart") Integer leftstart, @RequestParam("leftend") Integer leftend, HttpServletResponse response) throws Exception {
        // 下载目录，既是上传目录
        String downloadDir = springBootPlusProperties.getUploadPath();
        String newfile="";
        // 允许下载的文件后缀
        List<String> allowFileExtensions = springBootPlusProperties.getAllowDownloadFileExtensions();
        Console.log(springBootPlusProperties.getUploadPath() + downloadFileName+"-------------------");
        if (upstart == null && upend == null & leftstart == null && leftend == null) {
            //原图
            Console.log("不裁剪");
            newfile=downloadFileName;
        } else{
            newfile="new"+downloadFileName;
            ImageUtils.cut(springBootPlusProperties.getUploadPath() + downloadFileName,
                    springBootPlusProperties.getUploadPath() + newfile,
                    ThreadLocalRandom.current().nextInt(leftstart == null ? 0 : leftstart, leftend== null ? 0 : leftend+1), ThreadLocalRandom.current().nextInt(upstart == null ? 0 : upstart, upend == null ? 0 : upend+1));

        }
        // 文件下载，使用默认下载处理器
        // 文件下载，使用自定义下载处理器
        DownloadUtil.download(downloadDir, newfile, allowFileExtensions, response, (dir, fileName, file, fileExtension, contentType, length) -> {
            // 下载自定义处理，返回true：执行下载，false：取消下载
            log.info("dir = " + dir);
            log.info("fileName = " + fileName);
            log.info("file = " + file);
            log.info("fileExtension = " + fileExtension);
            log.info("contentType = " + contentType);
            log.info("length = " + length);
            return true;
        });
        File file = new File(springBootPlusProperties.getUploadPath() + "new" +downloadFileName);
        // 判断目录或文件是否存在
        if (file.exists()) {  // 不存在返回 false
            FileUtil.del(file);
        }
    }
}


package com.kaituo.comparison.back.core.controller;

import com.kaituo.comparison.back.common.util.FileUtil;
import com.kaituo.comparison.back.common.util.FtpUtil;
import com.kaituo.comparison.back.core.config.ftp.FtpConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;


@RestController
@RequestMapping(value = "/ftp")
@Slf4j(topic = "请求ftp服务器")
public class FtpController {
    @Autowired
    FtpConfig ftpConfig;

    @GetMapping("/upload")
    public String upload() {

        File file = new File(ftpConfig.getLocalDir());
        String[] fileNames = file.list();
        for (String fileName : fileNames) {
            boolean result = FtpUtil.ftpUpload(fileName, ftpConfig.getUrl(), ftpConfig.getPort(), ftpConfig.getUsername(),
                    ftpConfig.getPassword(), ftpConfig.getLocalDir(), ftpConfig.getRemotePath());
            if (result) {
                log.info("=======上传文件" + fileName + "成功=======");
            } else {
                log.info("=======上传文件" + fileName + "失败=======");
            }
        }


//        FileUtil.deleteFile(file);

        return "success";

    }

    @GetMapping("/download")
    public String download() {
        String fileName = "welcome.txt";
        boolean result = FtpUtil.ftpDownload(ftpConfig.getRemotePath(), ftpConfig.getUrl(), ftpConfig.getPort(), ftpConfig.getUsername(),
                ftpConfig.getPassword(), ftpConfig.getRemotePath(), ftpConfig.getDownDir());
        if (result) {
            log.info("=======下载文件" + fileName + "成功=======");
        } else {
            log.info("=======下载文件" + fileName + "失败=======");
        }
        return result ? "下载成功" : "下载失败";
    }


}
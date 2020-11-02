package com.kaituo.comparison.back.core.task;

import com.kaituo.comparison.back.common.util.FileUtil;
import com.kaituo.comparison.back.common.util.FtpUtil;
import com.kaituo.comparison.back.core.config.ftp.FtpConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * @Description:
 * @Author: yedong
 * @Date: 2020/10/23 10:06
 * @Modified by:
 */
@Slf4j
//@Component
public class FtpSchedule {

    @Autowired
    FtpConfig ftpConfig;


    @Autowired
    RestTemplate restTemplate;

    /**
     * 定时调用海康接口  下载图片到本地
     */
    @Scheduled(cron = "${cron.getHkPicture}")
    public void getHkPicture() {
        log.info(ftpConfig.toString());

    }

    /**
     * 定时把本地图片上传到ftp  取完删除本地图片
     */
    @Scheduled(cron = "${cron.getPicToFtp}")
    public void getPicToFtp() {

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


        FileUtil.deleteFile(file);


        log.info(ftpConfig.toString());
    }

}

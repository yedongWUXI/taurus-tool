package com.kaituo.comparison.back.core.task;

import com.kaituo.comparison.back.common.util.FtpUtil;
import com.kaituo.comparison.back.common.util.PropertyUtilsOld;
import com.kaituo.comparison.back.core.config.ftp.FtpConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author: yedong
 * @Date: 2020/10/23 10:06
 * @Modified by:
 */
@Slf4j
@Component
public class SoundFtpSchedule {

    @Autowired
    FtpConfig ftpConfig;


    /**
     * 定时把语音上传到ftp  取完删除语音
     */
//    @Scheduled(cron = "${cron.getYuyinToFtp}")
    public void getYuyinToFtp() throws IOException {

        log.info("收集语音开始");

        String recordTimeBefore = PropertyUtilsOld.getPropertiesValue("recordTime", "recordTime.properties");
        log.info("上次记录时间为：{}", recordTimeBefore);

        File file = new File(ftpConfig.getLocalDir());
        File[] files = file.listFiles();


        List<Long> listTime = new ArrayList<>();
        String[] list = file.list();
        for (String s : list) {
            listTime.add(Long.valueOf(s.substring(s.length() - 19, s.length() - 5)));
        }

        listTime.sort(Long::compareTo);
        Collections.reverse(listTime);


        if (files.length != 0) {

            for (File file1 : files) {

                String name = file1.getName();
                String recordTime = name.substring(name.length() - 19, name.length() - 5);

                if (Long.valueOf(recordTime) > Long.valueOf(recordTimeBefore)) {
                    boolean result = FtpUtil.ftpUpload(name, ftpConfig.getUrl(), ftpConfig.getPort(), ftpConfig.getUsername(),
                            ftpConfig.getPassword(), ftpConfig.getLocalDir(), ftpConfig.getRemotePath());
                    if (result) {
                        log.info("=======上传文件" + name + "成功=======");
                    } else {
                        log.info("=======上传文件" + name + "失败=======");
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    file1.delete();
                }


            }

            if (!CollectionUtils.isEmpty(listTime)) {

                if (listTime.get(0) > Long.valueOf(recordTimeBefore)) {

                    log.info("设置记录时间为：{}", listTime.get(0) + "");
                    PropertyUtilsOld.setPropertiesValue("recordTime", listTime.get(0) + "", "recordTime.properties");
                }

            }


        }

        log.info("收集语音结束");

    }



}

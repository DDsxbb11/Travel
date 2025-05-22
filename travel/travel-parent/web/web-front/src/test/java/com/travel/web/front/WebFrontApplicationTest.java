package com.travel.web.front;

import com.travel.common.utilis.UploadImageToMinioUtility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: JYX
 * @description: TODO: 测试类
 * @date: 2025-03-16 17:39
 */

@SpringBootTest
public class WebFrontApplicationTest {
    @Autowired
    private UploadImageToMinioUtility minioUtility;

    @Test
    public void upload(){
        try {
            // 创建 File 对象
            File file = new File("C:\\Users\\15101\\Desktop\\data\\attraction\\dongfangmingzhu2.jpg");

            // 读取文件内容
            FileInputStream fis = new FileInputStream(file);
            byte[] content = new byte[(int) file.length()];
            fis.read(content);
            fis.close();

            // 创建 MockMultipartFile 对象
            MultipartFile multipartFile = new MockMultipartFile(
                    "file", // 文件名
                    file.getName(), // 原始文件名
                    "image/jpeg", // 文件类型
                    content // 文件内容
            );

            // 上传到服务器
            minioUtility.upload(multipartFile,"hotel/images");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

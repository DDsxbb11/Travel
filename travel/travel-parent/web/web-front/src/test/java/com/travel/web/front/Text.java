package com.travel.web.front;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.file.Files;

/**
 * @author: Amoretto
 * @description: TODO:
 * @date: 2025-04-19 13:14
 */

@SpringBootTest
public class Text {

    @Autowired
    private ObsClient obsClient;

    @Test
    public void test() {
        TableInfo tableInfo = TableInfoHelper.getTableInfo("attraction_info");
        System.out.println("表名: " + tableInfo.getTableName());
        System.out.println("字段信息：");
        tableInfo.getFieldList().forEach(field -> {
            System.out.println("字段名: " + field.getColumn());
            System.out.println("field = " + field);
        });
    }
    @Test
    public void test02() {
        //https://sit-dispatch-obs-osd.obs.cn-north-4.myhuaweicloud.com/picture/BJFD2.jpg
        String bucketName="sit-dispatch-obs-osd";
        String objectKey="picture/BJFD2.jpg";
        InputStream input = null;
        OutputStream output = null;
        try {

            // 流式下载
            ObsObject obsObject = obsClient.getObject(bucketName, objectKey);
            // 读取对象内容
            input = obsObject.getObjectContent();

            // 2. 创建目标文件及目录
            File targetFile = new File("E:\\data\\01\\01.jpg");
            File parentDir = targetFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs(); // 如果目录不存在，则创建
            }

            // 3. 写入文件
            output = Files.newOutputStream(targetFile.toPath());
            byte[] buffer = new byte[1024 * 8]; // 8KB 缓冲区
            int len;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 5. 关闭流
            if (output!= null) {
                try {
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
    public static void main(String[] args) throws Exception{
        String endPoint = "https://obs.cn-north-4.myhuaweicloud.com";
        String ak = "HPUAVZHHQAGGGTOPBR2C";
        String sk = "oQwENAG8T3UmRI82tJKeCD6RpB6s0kCz4BDiVvNV";
        String bucketName = "demo-0424";
        ObsClient obsClient = new ObsClient(ak, sk,endPoint);
        try {
            // 文件上传
            // localfile 为待上传的本地文件路径，需要指定到具体的文件名
            PutObjectRequest request = new PutObjectRequest();
            String localfile = "C:\\Users\\15101\\Desktop\\work\\hotel\\images\\BJFD.jpg";
            request.setBucketName(bucketName);
            request.setObjectKey("picture/BJFD.jpg");
            request.setFile(new File(localfile));
            obsClient.putObject(request);
            System.out.println("putObject successfully");
        } catch (ObsException e) {
            System.out.println("putObject failed");
            // 请求失败,打印http状态码
            System.out.println("HTTP Code:" + e.getResponseCode());
            // 请求失败,打印服务端错误码
            System.out.println("Error Code:" + e.getErrorCode());
            // 请求失败,打印详细错误信息
            System.out.println("Error Message:" + e.getErrorMessage());
            // 请求失败,打印请求id
            System.out.println("Request ID:" + e.getErrorRequestId());
            System.out.println("Host ID:" + e.getErrorHostId());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("putObject failed");
            // 其他异常信息打印
            e.printStackTrace();
        }
    }
}

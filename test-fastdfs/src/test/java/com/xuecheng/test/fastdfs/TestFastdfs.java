package com.xuecheng.test.fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author wu on 2020/2/13 0013
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFastdfs {
    //上传文件测试
    @Test
    public void testUpload() {
        try {
            //加载fastdfs-client.properties配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //定义trackerClient,用于请求TrackerClient
            TrackerClient trackerClient = new TrackerClient();
            //连接TrackerClient
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取stoarage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建StroageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
            //文件路径
            String filePath = "d:/memery.jpg";
            //文件上传成功后的文件id
            String fileId = storageClient1.upload_file1(filePath, "jpg", null);
            System.out.println(fileId);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    //    下载测试
    @Test
    public void testDownload() {
        try {
            //加载fastdfs-client.properties配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //定义trackerClient,用于请求TrackerClient
            TrackerClient trackerClient = new TrackerClient();
            //连接TrackerClient
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取stoarage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建StroageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
            //文件路径
            String fileId = "group1/M00/00/00/rBKZCF5E-7KAcpBhAALPz-EfPEk677.jpg";
            //文件上传成功后的文件id
            byte[] bytes = storageClient1.download_file1(fileId);
            FileOutputStream fileOutputStream = new FileOutputStream(new File("f:/memery.jpg"));
            fileOutputStream.write(bytes);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}

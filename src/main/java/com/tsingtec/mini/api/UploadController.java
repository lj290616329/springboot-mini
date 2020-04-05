package com.tsingtec.mini.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.tsingtec.mini.config.ConstantQiniu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author lj
 * @Date 2020/3/9 16:42
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private ConstantQiniu constantQiniu;

    /**
     * 上传文件到七牛云存储
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/qiniu")
    public JSONObject uploadImgQiniu(@RequestParam("file") MultipartFile multipartFile){
        FileInputStream inputStream = null;
        try {
            inputStream = (FileInputStream) multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject result = uploadQNImg(inputStream, UUID.randomUUID().toString()); // KeyUtil.genUniqueKey()生成图片的随机名
        return result;
    }

    /**
     * 将图片上传到七牛云
     */
    private JSONObject uploadQNImg(FileInputStream file, String key) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("msg","上传成功");
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传
        try {
            Auth auth = Auth.create(constantQiniu.getAccessKey(), constantQiniu.getSecretKey());
            String upToken = auth.uploadToken(constantQiniu.getBucket());
            try {
                Response response = uploadManager.put(file, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                String returnPath = constantQiniu.getPath() + "/" + putRet.key;

                JSONObject result = new JSONObject();
                result.put("src",returnPath);
                result.put("title","");
                jsonObject.put("data",result);
            } catch (QiniuException ex) {
                jsonObject.put("code",-1);
                jsonObject.put("msg","图片上传失败");
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            jsonObject.put("code",-1);
            jsonObject.put("msg","图片上传失败");
            e.printStackTrace();
        }
        return jsonObject;
    }

}

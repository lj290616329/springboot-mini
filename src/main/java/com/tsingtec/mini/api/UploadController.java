package com.tsingtec.mini.api;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.tsingtec.mini.config.qiniu.ConstantQiniu;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.vo.resp.file.FileRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @Author lj
 * @Date 2020/3/9 16:42
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private ConstantQiniu constantQiniu;

    @Value("${file-path}")
    private String docBase;

    /**
     * 文件上传到自己服务器
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "/file")
    public DataResult<FileRespVO> uploadCover(@RequestParam("file") MultipartFile multipartFile) {
        DataResult<FileRespVO> result = DataResult.success();
        /**
         * 文件保存路径按照日期进行保存
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //当前文件加
        String savePath = sdf.format(new Date());

        File saveFile = new File(docBase+"/"+savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        String fileName = "";
        String fileRandomName = "";
        try {

            FileRespVO fileRespVO = new FileRespVO();

            fileName = multipartFile.getOriginalFilename();

            String fileType = fileName.substring(fileName.lastIndexOf("."));

            fileRandomName = UUID.randomUUID().toString() + fileType;

            //使用绝对路径进行文件保存
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(docBase+"/"+savePath +"/"+ fileRandomName));

            fileRespVO.setSrc("/" + savePath + "/" + fileRandomName);
            fileRespVO.setName(fileName);
            result.setData(fileRespVO);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Message:文件:{}，保存失败", fileName);
            result.setCode(-1);
            result.setMsg("文件上传失败");
        }
        return result;
    }

    /**
     * base64 格式为:
     * @param json
     * @return
     */
    @PostMapping("/base64")
    public DataResult<FileRespVO> base64(@RequestBody String json){
        DataResult<FileRespVO> result = DataResult.success();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String savePath = sdf.format(new Date());

        File saveFile = new File(docBase+"/"+savePath);

        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }

        String[] strs = json.split(",");

        String fileRandomName = UUID.randomUUID().toString()+strs[2];

        File file = new File(docBase+"/"+saveFile, fileRandomName);
        byte[] fileBytes = Base64.getDecoder().decode(strs[1]);
        try {

            FileRespVO fileRespVO = new FileRespVO();

            FileUtils.writeByteArrayToFile(file, fileBytes);
            fileRespVO.setSrc("/" + savePath +"/"+ fileRandomName);
            fileRespVO.setName(fileRandomName);
            result.setData(fileRespVO);

        } catch (IOException e) {
            e.printStackTrace();
            log.error("Message:文件:{}，保存失败", fileRandomName);
            result.setCode(-1);
        }
        return result;
    }


    /**
     * base64 格式为:
     * @param json
     * @return
     */
    @PostMapping("/base64/qiniu")
    public DataResult<FileRespVO> qiniuBase64(@RequestBody String json){
        DataResult<FileRespVO> result = DataResult.success();

        result = uploadBase64(json); // KeyUtil.genUniqueKey()生成图片的随机名
        return result;
    }

    /**
     * 上传文件到七牛云存储
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/file/qiniu")
    public DataResult<FileRespVO> uploadImgQiniu(@RequestParam("file") MultipartFile multipartFile){
        DataResult<FileRespVO> result = DataResult.success();
        String fileName = multipartFile.getOriginalFilename();
        FileInputStream inputStream = null;
        try {
            inputStream = (FileInputStream) multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            result.setMsg("文件上传失败,请稍后再试!");
            result.setCode(-1);
            return result;
        }
        result = uploadQNImg(inputStream,fileName); // KeyUtil.genUniqueKey()生成图片的随机名
        return result;
    }

    /**
     * 将文件上传到七牛云
     */
    private DataResult<FileRespVO> uploadQNImg(FileInputStream file, String fileName) {
        DataResult<FileRespVO> result = DataResult.success();

        String fileType = fileName.substring(fileName.lastIndexOf("."));

        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.autoZone());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传
        try {
            Auth auth = Auth.create(constantQiniu.getAccessKey(), constantQiniu.getSecretKey());
            String upToken = auth.uploadToken(constantQiniu.getBucket());
            try {
                String key = UUID.randomUUID().toString() + fileType;

                Response response = uploadManager.put(file, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);

                String returnPath = constantQiniu.getPath() + "/" + putRet.key;

                FileRespVO fileRespVO = new FileRespVO();
                fileRespVO.setSrc(returnPath);
                fileRespVO.setName(fileName);

                result.setData(fileRespVO);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
                result.setCode(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(-1);
        }
        return result;
    }


    /**
     * 将文件上传到七牛云
     */
    private DataResult<FileRespVO> uploadBase64(String base64) {
        DataResult<FileRespVO> result = DataResult.success();
        String[] strs = base64.split(",");
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.autoZone());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传
        try {
            Auth auth = Auth.create(constantQiniu.getAccessKey(), constantQiniu.getSecretKey());
            String upToken = auth.uploadToken(constantQiniu.getBucket());
            try {
                String fileRandomName = UUID.randomUUID().toString()+strs[2];
                Response response = uploadManager.put(Base64.getDecoder().decode(strs[1]), fileRandomName, upToken);
                // 解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);

                String returnPath = constantQiniu.getPath() + "/" + putRet.key;

                FileRespVO fileRespVO = new FileRespVO();
                fileRespVO.setSrc(returnPath);
                fileRespVO.setName(fileRandomName);
                result.setData(fileRespVO);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
                result.setCode(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(-1);
        }
        return result;
    }

}

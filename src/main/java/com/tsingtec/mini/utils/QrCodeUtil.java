package com.tsingtec.mini.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@Component
public class QrCodeUtil {

    // base64编码集
    public static final String CHARSET = "UTF-8";
    // 二维码外边距
    public static final int MARGIN = 0;
    // 二维码图片格式
    private static final String FORMAT = "png";

    public static String path;

    @Value("${file-path}")
    public void setPath(String path){
        this.path = path;
    }

    /* 生成二维码
     * @explain
     * @param data 字符串（二维码实际内容）
     * @return
     */
    public static String createQRCode(String data,int width,int height) {
        return writeToString(createQRCode(data, width, height, MARGIN));
    }

    @SuppressWarnings("unused")
    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static String getQRCodeImage(String text, int width, int height) throws IOException {
        File file = new File(path+"/logo.png");
        if(!file.exists()){
            Files.copy(new ClassPathResource("static/images/logo.png").getInputStream(),Paths.get(path+"/logo.png"));
        }
        BufferedImage image = createQRCodeWithLogo(text, new File(path+"/logo.png"),width,height);
        String base64 = writeToString(image);
        return base64;
    }


    /**
     * 生成二维码
     * @explain
     * @param data 字符串（二维码实际内容）
     * @param width 宽
     * @param height 高
     * @param margin 外边距，单位：像素，只能为整数，否则：报错
     * @return BufferedImage
     */
    public static BufferedImage createQRCode(String data, int width, int height, int margin) {
        BitMatrix matrix;
        try {
            // 设置QR二维码参数
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>(2);
            // 纠错级别（H为最高级别）
            // L级：约可纠错7%的数据码字
            // M级：约可纠错15%的数据码字
            // Q级：约可纠错25%的数据码字
            // H级：约可纠错30%的数据码字
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 字符集
            hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
            // 边框，(num * 10)
            hints.put(EncodeHintType.MARGIN, 0);// num
            // 编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE,
                    width, height, hints);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return MatrixToImageWriter.toBufferedImage(matrix);
    }
    @SuppressWarnings("unused")
    private static BitMatrix renderResult(QRCode code, int width, int height, int quietZone){
        ByteMatrix input = code.getMatrix();
        if (input == null)
            throw new IllegalStateException();

        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + quietZone * 2;
        int qrHeight = inputHeight + quietZone * 2;
        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);

        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        // 有白色边框的罪魁祸首：leftPadding和topPadding
        int leftPadding = (outputWidth - inputWidth * multiple) / 2;
        int topPadding = (outputHeight - inputHeight * multiple) / 2;

        BitMatrix output = new BitMatrix(outputWidth, outputHeight);

        int inputY = 0;
        for (int outputY = topPadding; inputY < inputHeight; ){
            int inputX = 0;
            for (int outputX = leftPadding; inputX < inputWidth; ) {
                if (input.get(inputX, inputY) == 1)
                    output.setRegion(outputX, outputY, multiple, multiple);
                ++inputX; outputX += multiple;
            }
            ++inputY; outputY += multiple;
        }
        return output;
    }

    /**
     * 生成带logo的二维码
     * @explain 宽、高、外边距使用定义好的值
     * @param data 字符串（二维码实际内容）
     * @param logoFile logo图片文件对象
     * @return BufferedImage
     */
    public static BufferedImage createQRCodeWithLogo(String data, File logoFile,int width ,int height) {
        return createQRCodeWithLogo(data, width, height, MARGIN, logoFile);
    }

    /**
     * 生成带logo的二维码
     * @explain 自定义二维码的宽和高
     * @param data 字符串（二维码实际内容）
     * @param width 宽
     * @param height 高
     * @param logoFile logo图片文件对象
     * @return BufferedImage
     * @return
     */
    public static BufferedImage createQRCodeWithLogo(String data, int width, int height, int margin, File logoFile) {
        BufferedImage combined = null;
        try {
            BufferedImage qrcode = createQRCode(data, width, height, margin);
            BufferedImage logo = ImageIO.read(logoFile);
            int deltaHeight = height - logo.getHeight();
            int deltaWidth = width - logo.getWidth();
            combined = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) combined.getGraphics();
            g.drawImage(qrcode, 0, 0, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g.drawImage(logo, (int) Math.round(deltaWidth/2), (int) Math.round(deltaHeight/2), null);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return combined;
    }

    /**
     * 获取base64格式的二维码
     * @explain 图片类型：jpg
     *  展示：<img src="data:image/jpeg;base64,base64Str"/>
     * @param image
     * @return base64
     */
    public static String writeToString(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, FORMAT, baos);//写入流中
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();//转换成字节
        String png_base64 = new Base64().encodeToString(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");
        return png_base64;
    }

}
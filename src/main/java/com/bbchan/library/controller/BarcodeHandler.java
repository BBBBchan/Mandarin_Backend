package com.bbchan.library.controller;

import com.bbchan.library.service.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.bbchan.library.service.BarcodeService.getBarCode;
import static com.bbchan.library.service.BarcodeService.insertWords;

@RestController
public class BarcodeHandler {
    @Autowired
    private BarcodeService barcodeService;

    @GetMapping("/barcodeinfo")
    public ResponseEntity<Map<String, Object>> searchBook(@RequestParam(value = "bookid") String bookid) {
        Map<String, Object> res = new HashMap<>();
        try {
            BufferedImage image = insertWords(getBarCode(bookid), bookid);
            //ImageIO.write(image, "jpg", new File("D://abc.jpg"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            assert image != null;
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            //使用toByteArray()方法转换成字节数组
            byte[] imageInByte = baos.toByteArray();
            String msg = Base64.getEncoder().encodeToString(imageInByte);
            res.put("statues", 200);
            res.put("barcode", msg);
            baos.close();
        } catch (IOException e) {
            res.put("statues", 400);
            String message = "IO错误，barcode获取失败";
            res.put("message", message);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}

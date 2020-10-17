package com.bbchan.library.controller;

import com.bbchan.library.entity.User;
import com.bbchan.library.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reader")
public class ReaderPasswordRecoverHandler {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AccountService accountService;

    @PostMapping("/password-recovery")
    public ResponseEntity<Map<String, Object>> RecoverPassword(@RequestBody Map params) throws NoSuchAlgorithmException, MessagingException {
        Map<String, Object> res = new HashMap<>();
        String message;
        //忘记密码需要进行邮箱验证
        User user = accountService.findUser((String) params.get("username"));
        if (user == null) {
            message = "用户不存在";
            res.put("statues", 401);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        if (!user.getEmail().equals(params.get("email"))) {
            message = "认证失败，当前邮箱错误";
            res.put("statues", 402);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        if (user.getIdentity() != 3) {
            message = "认证失败，用户权限错误";
            res.put("statues", 403);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        //From：起始邮件地址 TO：目标邮件地址 Subject：邮件标题 Text：邮件内容
//        SimpleMailMessage passwordmessage = new SimpleMailMessage();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper passwordHelper = new MimeMessageHelper(mimeMessage, true);
        passwordHelper.setFrom("2951209135@qq.com");
        passwordHelper.setTo(user.getEmail());
        passwordHelper.setSubject("[Mandarin] Please reset your password\n");
        String password = user.getPassword();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] encode;
        encode = messageDigest.digest(password.getBytes());
        String temp = byte2hex(encode);
        System.out.println(temp);
        String url = "http://localhost:8080/#/change_password?username=" + user.getUsername() + "&detail=" + temp;
        passwordHelper.setText("We heard that you lost your Mandarin password. Sorry about that!<br>" +
                "<br>" +
                "But don’t worry! You can use the following link to reset your password:<br>" +
                "<br>" +
                "<a href=\"" + url + "\">" + url + "</a><br>" +
                "<br>" +
                "<br>" +
                "<br>" +
                "Thanks,<br>" +
                "The Mandarin Team B5", true);
        javaMailSender.send(mimeMessage);
        message = "邮件发送成功";
        res.put("statues", 200);
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private static String byte2hex(byte[] b) //二进制转字符串
    {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (byte value : b) {
            stmp = (Integer.toHexString(value & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString();
    }
}

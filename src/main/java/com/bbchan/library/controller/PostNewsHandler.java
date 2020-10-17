package com.bbchan.library.controller;

import com.bbchan.library.entity.Post_news;
import com.bbchan.library.service.PostNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostNewsHandler {
    @Autowired
    PostNewsService postNewsService;

    @PostMapping("postnews/add")
    public ResponseEntity<Map<String, Object>> addNews(
            @RequestBody Map params
    ) {

        Map<String, Object> res = new HashMap<>();
        Post_news post_news = new Post_news();

//        post_news.setPost_id((Integer) params.get("id"));
        post_news.setPost_detail((String) params.get("detail"));
        post_news.setPost_title((String) params.get("title"));
        post_news.setUsername((String) params.get("name"));
        //时间设置为当前的时间
        post_news.setPost_time(new Date());

        //将news添加到其中
        int isSuccess = postNewsService.addNews(post_news);

        String message;
        if (isSuccess == 1) {
            message = "成功添加news";
            res.put("statues", 200);
        } else {
            message = "fail";
            res.put("statues", 400);
        }
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping("postnews/get")
    public List<Post_news> getAll() {
        return postNewsService.getAll();
    }

    @RequestMapping("postnews/delete")
    public ResponseEntity<Map<String, Object>> deletenews(@RequestBody Map params) {

        Map<String, Object> res = new HashMap<>();

        Integer id = Integer.parseInt((String) params.get("id"));
        System.out.println(id);
        Post_news post_news = new Post_news();
        post_news.setPost_id(id);

        //如果返回为null，说明这个id的postnews不存在
        if (postNewsService.find(post_news, false) == null) {
            String message = "这个News不存在";
            res.put("statues", 400);
            res.put(message, message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        //        删除
        postNewsService.delNews(post_news);
//        使用id进行查找，如果找到了就删除失败，否则成功
//        返回null说明删除成功
        boolean result = postNewsService.find(post_news, false) == null;
        String message;
        if (!result) {
            message = "删除失败";
            res.put("statues", 400);
        } else {
            message = "删除成功";
            res.put("statues", 200);
        }
        res.put(message, message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping("postnews/edit")
    public ResponseEntity<Map<String, Object>> editNews(
            @RequestBody Map params
    ) {

        Map<String, Object> res = new HashMap<>();
        Post_news post_news = new Post_news();

        post_news.setPost_id(Integer.parseInt((String) params.get("id")));
        post_news.setPost_detail((String) params.get("detail"));
        post_news.setPost_title((String) params.get("title"));
        post_news.setUsername((String) params.get("name"));

        //设置为当前时间
        post_news.setPost_time(new Date());
        System.out.println(postNewsService.editNews(post_news));

        Post_news result_news = postNewsService.editNews(post_news);
        boolean result;
        result = result_news.getPost_id().equals(post_news.getPost_id())
                && result_news.getPost_title().equals(post_news.getPost_title())
                && result_news.getPost_detail().equals(post_news.getPost_detail())
                && result_news.getUsername().equals(post_news.getUsername());

        String message;
        if (result) {
            message = "修改成功";
            res.put("statues", 200);
        } else {
            message = "修改失败";
            res.put("statues", 400);
        }
        res.put(message, message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}


package com.bbchan.library.service;


import com.bbchan.library.entity.Post_news;
import com.bbchan.library.repository.Post_newsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostNewsService {
    @Autowired
    Post_newsRepository post_newsRepository;

    public List<Post_news> getAll() {
        return post_newsRepository.findAll();
    }

    public void delNews(Post_news post_news) {
        post_newsRepository.delete(post_news);
    }

    public int addNews(Post_news post_news) {
        Post_news post_news_return = post_newsRepository.save(post_news);
        if (post_news_return == null) return 0;
//        1成功
        else return 1;
    }

    public Post_news editNews(Post_news post_news) {
        return post_newsRepository.save(post_news);
    }

    public Post_news find(Post_news post_news, Boolean iscreate) {
        Optional<Post_news> postList = post_newsRepository.findById(post_news.getPost_id());

        //如果找到了那么就返回，否则就创建
        if (postList.isPresent()) {
            return postList.get();
        } else if (iscreate) {
            //如果iscreate为真才创建
            return post_newsRepository.save(post_news);
        } else {
            return null;
        }
    }
}

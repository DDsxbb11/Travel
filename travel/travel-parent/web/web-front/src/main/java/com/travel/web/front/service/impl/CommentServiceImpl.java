package com.travel.web.front.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.model.pojo.Comment;
import com.travel.web.front.mapper.CommentMapper;
import com.travel.web.front.service.CommentService;
import org.springframework.stereotype.Service;

/**
* @author 15101
* @description 针对表【comment(评论表)】的数据库操作Service实现
* @createDate 2025-04-06 10:07:36
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {

}





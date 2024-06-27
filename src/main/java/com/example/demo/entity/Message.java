package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @TableId(type = IdType.AUTO)
    private Integer mid;
    private Integer sender;
    private Integer receiver;
    private String content;
    private String time;
    private Integer status;
}

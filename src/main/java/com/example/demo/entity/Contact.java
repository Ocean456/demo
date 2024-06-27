package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @TableId(type = IdType.AUTO)
    public Integer cid;
    public int uid;
    public int fid;
    public String time;
    public String classify;
    public int status;

}

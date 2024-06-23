package com.example.baseEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.utils.Const;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {
    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /** 创建人 */
    private String createdBy ;
    /** 创建时间 */
//    @JSONField(format = "yyyy-MM-dd ")   //后台转换到页面显示
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime ;
    /** 更新人 */
    private String updatedBy ;
    /** 更新时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updatedTime ;
    /** 是否删除 Y/N */
    private String deleteMark;
    public void init(UserMsg createdBy) {
        this.createdBy = createdBy.getUsername();
        this.createdTime = new Date();
        this.updatedBy = createdBy.getUsername();
        this.updatedTime = new Date();
        this.deleteMark = Const.NO;
    }

    public void userInit(String createdBy) {
        this.createdBy = createdBy;
        this.createdTime = new Date();
        this.updatedBy = createdBy;
        this.updatedTime = new Date();
        this.deleteMark = Const.NO;
    }

    public void init(String updatedBy, Date updatedTime) {
        this.updatedBy = updatedBy;
        this.updatedTime = updatedTime;
    }
}

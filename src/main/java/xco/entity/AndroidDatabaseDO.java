package xco.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.Data;
import xco.handler.AndroidDecryptHandler;

@Data
//@TableName("mr_%s_%s_New")
@TableName("mr_troop_87A21D72BABAA8E3268998EF084C362E_New")
public class AndroidDatabaseDO {
    @TableId(value = "_id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "extStr")
    private String extra;
    @TableField(value = "frienduin")
    private String receiverId;
    @TableField(value = "issend")
    private Integer isSend;
    @TableField(value = "istroop")
    private Integer isGroup;
    @TableField(value = "msgData", typeHandler = AndroidDecryptHandler.class)
    private String msgData;
    @TableField(value = "msgtype")
    private Integer msgType;
    @TableField(value = "senderuin")
    private String senderId;
    @TableField(value = "shmsgseq")
    private Integer source;
    private Date time;
}

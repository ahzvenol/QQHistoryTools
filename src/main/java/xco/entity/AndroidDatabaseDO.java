package xco.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import xco.handler.AndroidDecryptHandler;
import xco.handler.IntUnixTimestampHandler;

@Data
@TableName("mr_%s_%s_New")
//@TableName("mr_troop_87A21D72BABAA8E3268998EF084C362E_New")
public class AndroidDatabaseDO {
    @TableId(value = "_id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "extStr", typeHandler = AndroidDecryptHandler.class)
    private byte[] extra;
    //@TableField(value = "frienduin")
    //private String receiverId;
    @TableField(value = "issend")
    private Integer isSent;
    @TableField(value = "msgData", typeHandler = AndroidDecryptHandler.class)
    private byte[] msgData;
    @TableField(value = "msgtype")
    private Integer msgType;
    @TableField(value = "senderuin", typeHandler = AndroidDecryptHandler.class)
    private byte[] senderId;
    @TableField(value = "shmsgseq")
    private Integer source;
    @TableField(value = "time", typeHandler = IntUnixTimestampHandler.class)
    private Date time;
}

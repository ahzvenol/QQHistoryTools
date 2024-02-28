package xco.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import xco.handler.ByteBlobHandler;
import xco.handler.IntUnixTimestampHandler;

import java.util.Date;

@Data
@TableName("%s_%s[Windows]")
public class WindowsMessageTableDO {
    @TableField(value = "Seq")
    private Integer seq;
    @TableField(value = "Time", typeHandler = IntUnixTimestampHandler.class)
    private Date time;
    @TableField(value = "SenderUin")
    private Integer senderUin;
    @TableField(value = "MsgContent", typeHandler = ByteBlobHandler.class)
    private byte[] msgContent;

    public Integer getSeq() {
        return seq;
    }

    public Date getTime() {
        return time;
    }

    public Integer getSenderUin() {
        return senderUin;
    }

    public byte[] getMsgContent() {
        return msgContent;
    }
}

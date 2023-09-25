package xco.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import xco.handler.ByteBlobHandler;
import xco.handler.IntUnixTimestampHandler;

import java.util.Date;

@Data
@TableName("%s_%s[Windows]")
public class WindowsMessageTableDO {
    @TableId(value = "Rand")
    private Integer id;
    @TableField(value = "Time", typeHandler = IntUnixTimestampHandler.class)
    private Date time;
    @TableField(value = "SenderUin")
    private Integer senderId;
    @TableField(value = "MsgContent", typeHandler = ByteBlobHandler.class)
    private byte[] msgContent;
}

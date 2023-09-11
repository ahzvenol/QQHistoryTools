package xco.record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("msg")
public class Msg {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private Integer source;
	private Integer quote;
	private Date timeStamp;
	private Integer senderId;
	private String senderName;
	private String msg;

	@NotNull
	public Msg setId(Integer id) {
		this.id = id;
		return this;
	}
	@NotNull
	public Msg setSource(Integer source) {
		this.source = source;
		return this;
	}
	@NotNull
	public Msg setQuote(Integer quote) {
		this.quote = quote;
		return this;
	}
	@NotNull
	public Msg setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
		return this;
	}
	@NotNull
	public Msg setSenderId(Integer senderId) {
		this.senderId = senderId;
		return this;
	}
	@NotNull
	public Msg setSenderName(String senderName) {
		this.senderName = senderName;
		return this;
	}
	@NotNull
	public Msg setMsg(String msg) {
		this.msg = msg;
		return this;
	}
}

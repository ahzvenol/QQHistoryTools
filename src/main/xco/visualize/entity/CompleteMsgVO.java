package xco.visualize.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(autoResultMap = true)
//这个类无法用于存储
public class CompleteMsgVO {
	private Integer id;
	private Integer source;
	private String qqCode;
	private Date msgSendTime;
	private String senderName;
	private String msgContent;
	private Integer quote;
	private Date quoteMsgSendTime;
	private String quoteName;
	private String quoteMsg;
	private Date recallTime;
	private String authorName;
	private String authorQQCode;
	private String operatorName;
	private String operatorQQCode;

	public CompleteMsgVO setId(Integer id) {
		this.id = id;
		return this;
	}

	public CompleteMsgVO setSource(Integer source) {
		this.source = source;
		return this;
	}

	public CompleteMsgVO setQqCode(String qqCode) {
		this.qqCode = qqCode;
		return this;
	}

	public CompleteMsgVO setMsgSendTime(Date msgSendTime) {
		this.msgSendTime = msgSendTime;
		return this;
	}

	public CompleteMsgVO setSenderName(String senderName) {
		this.senderName = senderName;
		return this;
	}

	public CompleteMsgVO setMsgContent(String msgContent) {
		this.msgContent = msgContent;
		return this;
	}

	public CompleteMsgVO setQuote(Integer quote) {
		this.quote = quote;
		return this;
	}

	public CompleteMsgVO setQuoteMsgSendTime(Date quoteMsgSendTime) {
		this.quoteMsgSendTime = quoteMsgSendTime;
		return this;
	}

	public CompleteMsgVO setQuoteName(String quoteName) {
		this.quoteName = quoteName;
		return this;
	}

	public CompleteMsgVO setQuoteMsg(String quoteMsg) {
		this.quoteMsg = quoteMsg;
		return this;
	}

	public CompleteMsgVO setRecallTime(Date recallTime) {
		this.recallTime = recallTime;
		return this;
	}

	public CompleteMsgVO setAuthorName(String authorName) {
		this.authorName = authorName;
		return this;
	}

	public CompleteMsgVO setAuthorQQCode(String authorQQCode) {
		this.authorQQCode = authorQQCode;
		return this;
	}

	public CompleteMsgVO setOperatorName(String operatorName) {
		this.operatorName = operatorName;
		return this;
	}

	public CompleteMsgVO setOperatorQQCode(String operatorQQCode) {
		this.operatorQQCode = operatorQQCode;
		return this;
	}
}

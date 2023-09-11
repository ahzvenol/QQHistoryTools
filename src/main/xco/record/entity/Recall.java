package xco.record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("recall")
public class Recall {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private Integer recallSource;
	private Date timeStamp;
	private Integer authorId;
	private String authorName;
	private Integer operatorId;
	private String operatorName;

	public Recall setId(Integer id) {
		this.id = id;
		return this;
	}

	public Recall setRecallSource(Integer recallSource) {
		this.recallSource = recallSource;
		return this;
	}

	public Recall setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
		return this;
	}

	public Recall setAuthorId(Integer authorId) {
		this.authorId = authorId;
		return this;
	}

	public Recall setAuthorName(String authorName) {
		this.authorName = authorName;
		return this;
	}

	public Recall setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
		return this;
	}

	public Recall setOperatorName(String operatorName) {
		this.operatorName = operatorName;
		return this;
	}
}

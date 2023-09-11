package xco.record.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xco.record.entity.Msg;

import java.util.Map;

@Mapper
public interface MsgSaveMapper extends BaseMapper<Msg> {
	void insertMember(Map<String, Object> map);

	Integer selectMemberIdByQQCode(Long code);

	// void insertRecallInfo(Map<String, Object> map);
}


package xco.visualize.mapper;

import org.apache.ibatis.annotations.Mapper;
import xco.visualize.entity.CompleteMsgVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface MsgSelectMapper {
    List<Map<String, Object>> selectMsgEx(Map<String, Integer> map);

    List<CompleteMsgVO> selectCompleteMsg(Map<String, Integer> map);
}

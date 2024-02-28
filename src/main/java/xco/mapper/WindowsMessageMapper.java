package xco.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xco.entity.WindowsMessageTableDO;

import java.util.List;

@Mapper
public interface WindowsMessageMapper {
    List<WindowsMessageTableDO> selectList(@Param("targetType") String targetType, @Param("targetNumber") Integer targetNumber);
}

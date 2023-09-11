package xco.visualize.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IPMapper {
    List<String> getIPlist();
}

package xco.visualize.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MsgService {

    List<Map<String, Object>> selectMsgEx(Map<String, Integer> map);

}

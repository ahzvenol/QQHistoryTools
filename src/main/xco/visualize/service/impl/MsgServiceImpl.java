package xco.visualize.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xco.visualize.mapper.MsgSelectMapper;
import xco.visualize.service.MsgService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MsgServiceImpl implements MsgService {
	@Autowired
	private MsgSelectMapper msgMapper;

	@Override
	public List<Map<String, Object>> selectMsgEx(Map<String, Integer> map) {
		if (map == null) map = new HashMap<String, Integer>();
		return msgMapper.selectMsgEx(map);
	}

}

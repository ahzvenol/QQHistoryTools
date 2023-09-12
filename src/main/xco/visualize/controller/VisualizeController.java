package xco.visualize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xco.visualize.service.MsgService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ab239136-7e95-4ba3-ab99-b20bc06115c5")
public class VisualizeController {
    @Autowired
    private MsgService msgService;
    
    @PostMapping("/getMsgEx")
    public List<Map<String, Object>> getMsgEx(@RequestBody(required = false) Map<String, Integer> map) {
        return msgService.selectMsgEx(map);
    }
}

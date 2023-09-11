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
@RequestMapping("/b3f767f7-91ce-4efb-b70e-559fded918c3")
public class VisualizeController {
    @Autowired
    private MsgService msgService;
    
    @PostMapping("/getMsgEx")
    public List<Map<String, Object>> getMsgEx(@RequestBody(required = false) Map<String, Integer> map) {
        return msgService.selectMsgEx(map);
    }
}

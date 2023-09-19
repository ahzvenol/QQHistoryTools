package xco.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.springframework.stereotype.Component
import xco.entity.WindowsMessageTableDO

@Component
trait WindowsMessageMapper extends BaseMapper[WindowsMessageTableDO]

package net.xbookmark.core.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xbookmark.core.NavFileService;
import net.xbookmark.dao.mapper.NavFileEntityMapper;
import net.xbookmark.dao.model.NavFileEntity;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【tb_nav_file(文件信息表)】的数据库操作Service实现
* @createDate 2023-07-29 17:04:43
*/
@Service
public class NavFileServiceImpl extends ServiceImpl<NavFileEntityMapper, NavFileEntity>
    implements NavFileService {

}





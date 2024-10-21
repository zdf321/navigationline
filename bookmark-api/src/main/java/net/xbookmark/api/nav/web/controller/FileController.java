package net.xbookmark.api.nav.web.controller;

import cn.hutool.core.lang.UUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.xbookmark.common.response.R;
import net.xbookmark.common.util.Constants;
import net.xbookmark.common.util.UserContext;
import net.xbookmark.core.FileService;
import net.xbookmark.core.TeamSpaceService;
import net.xbookmark.dao.model.TeamSpaceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2021/01/18 20:59
 */
@RestController
@RequestMapping("/file")
@Api(tags = "文件上传相关API")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private TeamSpaceService teamSpaceService;

    @PostMapping("/uploadUserWebsiteLogo")
    @ApiOperation("上传网站logo")
    public R<String> uploadUserWebsiteLogo(
            @ApiParam(value = "文件", required = true) @RequestParam MultipartFile file) {
        final String uid = UserContext.getUid();
        return R.success(
                fileService.uploadFile(
                        file, String.format(Constants.FileKey.USER_WEBSITE_LOGO_PREFIX, uid)));
    }

    @PostMapping("/uploadSpaceLogo")
    @ApiOperation("上传空间logo")
    public R<String> uploadSpaceLogo(
            @ApiParam(value = "文件", required = true) @RequestParam MultipartFile file,
            @RequestParam String spaceId) {
        TeamSpaceEntity teamSpace = teamSpaceService.getById(spaceId);
        String teamId = teamSpace.getTeamId();

        return R.success(
                fileService.uploadFile(
                        file, String.format(Constants.FileKey.TEAM_SPACE_LOGO_PREFIX, teamId)));
    }

    @PostMapping("/uploadAvatar")
    @ApiOperation("上传头像")
    public R<String> uploadAvatar(
            @ApiParam(value = "文件", required = true) @RequestParam MultipartFile file) {
        String uid = UserContext.getUid();
        return R.success(
                fileService.uploadFile(
                        file,
                        String.format(Constants.FileKey.AVATAR_PREFIX, uid, UUID.fastUUID().toString(true))));
    }
}

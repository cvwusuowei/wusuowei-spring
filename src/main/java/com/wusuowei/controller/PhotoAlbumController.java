package com.wusuowei.controller;

import com.wusuowei.annotation.OptLog;
import com.wusuowei.model.dto.PhotoAlbumAdminDTO;
import com.wusuowei.model.dto.PhotoAlbumDTO;
import com.wusuowei.enums.FilePathEnum;
import com.wusuowei.model.vo.ResultVO;
import com.wusuowei.service.PhotoAlbumService;
import com.wusuowei.strategy.context.UploadStrategyContext;
import com.wusuowei.model.vo.ConditionVO;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.vo.PhotoAlbumVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;

import static com.wusuowei.constant.OptTypeConstant.*;

@Tag(name = "相册模块")
@RestController
public class PhotoAlbumController {

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private PhotoAlbumService photoAlbumService;

    @OptLog(optType = UPLOAD)
    @Operation(summary = "上传相册封面")
    @Parameter(name = "file", description = "相册封面", required = true)
    @PostMapping("/admin/photos/albums/upload")
    public ResultVO<String> savePhotoAlbumCover(@RequestParam MultipartFile file) {
        return ResultVO.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath()));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "保存或更新相册")
    @PostMapping("/admin/photos/albums")
    public ResultVO<?> saveOrUpdatePhotoAlbum(@Valid @RequestBody PhotoAlbumVO photoAlbumVO) {
        photoAlbumService.saveOrUpdatePhotoAlbum(photoAlbumVO);
        return ResultVO.ok();
    }

    @Operation(summary = "查看后台相册列表")
    @GetMapping("/admin/photos/albums")
    public ResultVO<PageResultDTO<PhotoAlbumAdminDTO>> listPhotoAlbumBacks(ConditionVO conditionVO) {
        return ResultVO.ok(photoAlbumService.listPhotoAlbumsAdmin(conditionVO));
    }

    @Operation(summary = "获取后台相册列表信息")
    @GetMapping("/admin/photos/albums/info")
    public ResultVO<List<PhotoAlbumDTO>> listPhotoAlbumBackInfos() {
        return ResultVO.ok(photoAlbumService.listPhotoAlbumInfosAdmin());
    }

    @Operation(summary = "根据id获取后台相册信息")
    @Parameter(name = "albumId", description = "相册id", required = true)
    @GetMapping("/admin/photos/albums/{albumId}/info")
    public ResultVO<PhotoAlbumAdminDTO> getPhotoAlbumBackById(@PathVariable("albumId") Integer albumId) {
        return ResultVO.ok(photoAlbumService.getPhotoAlbumByIdAdmin(albumId));
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "根据id删除相册")
    @Parameter(name = "albumId", description = "相册id", required = true)
    @DeleteMapping("/admin/photos/albums/{albumId}")
    public ResultVO<?> deletePhotoAlbumById(@PathVariable("albumId") Integer albumId) {
        photoAlbumService.deletePhotoAlbumById(albumId);
        return ResultVO.ok();
    }

    @Operation(summary = "获取相册列表")
    @GetMapping("/photos/albums")
    public ResultVO<List<PhotoAlbumDTO>> listPhotoAlbums() {
        return ResultVO.ok(photoAlbumService.listPhotoAlbums());
    }
}

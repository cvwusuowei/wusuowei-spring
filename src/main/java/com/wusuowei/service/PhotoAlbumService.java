package com.wusuowei.service;

import com.wusuowei.entity.PhotoAlbum;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.dto.PhotoAlbumAdminDTO;
import com.wusuowei.model.dto.PhotoAlbumDTO;
import com.wusuowei.model.vo.ConditionVO;
import com.wusuowei.model.vo.PhotoAlbumVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PhotoAlbumService extends IService<PhotoAlbum> {

    void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO);

    PageResultDTO<PhotoAlbumAdminDTO> listPhotoAlbumsAdmin(ConditionVO condition);

    List<PhotoAlbumDTO> listPhotoAlbumInfosAdmin();

    PhotoAlbumAdminDTO getPhotoAlbumByIdAdmin(Integer albumId);

    void deletePhotoAlbumById(Integer albumId);

    List<PhotoAlbumDTO> listPhotoAlbums();

}

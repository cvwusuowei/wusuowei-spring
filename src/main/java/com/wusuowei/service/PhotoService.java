package com.wusuowei.service;

import com.wusuowei.entity.Photo;
import com.wusuowei.model.dto.PageResultDTO;
import com.wusuowei.model.dto.PhotoAdminDTO;
import com.wusuowei.model.dto.PhotoDTO;
import com.wusuowei.model.vo.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PhotoService extends IService<Photo> {

    PageResultDTO<PhotoAdminDTO> listPhotos(ConditionVO conditionVO);

    void updatePhoto(PhotoInfoVO photoInfoVO);

    void savePhotos(PhotoVO photoVO);

    void updatePhotosAlbum(PhotoVO photoVO);

    void updatePhotoDelete(DeleteVO deleteVO);

    void deletePhotos(List<Integer> photoIds);

    PhotoDTO listPhotosByAlbumId(Integer albumId);

}

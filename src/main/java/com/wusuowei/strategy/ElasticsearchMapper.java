package com.wusuowei.strategy;

import com.wusuowei.model.dto.ArticleSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


/**
 * @author 无所为
 * elasticsearch
 */
public interface ElasticsearchMapper extends ElasticsearchRepository<ArticleSearchDTO, Integer> {

}

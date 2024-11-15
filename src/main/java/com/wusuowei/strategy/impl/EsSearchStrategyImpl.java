//package com.wusuowei.strategy.impl;
//
//import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
//import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.wusuowei.model.dto.ArticleSearchDTO;
//import com.wusuowei.strategy.SearchStrategy;
//import lombok.extern.log4j.Log4j2;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
//import org.springframework.data.elasticsearch.core.elasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.Query;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.wusuowei.constant.CommonConstant.*;
//import static com.wusuowei.enums.ArticleStatusEnum.PUBLIC;
//
//@Log4j2
//@Service("esSearchStrategyImpl")
//public class EsSearchStrategyImpl implements SearchStrategy {
//
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    @Override
//    public List<ArticleSearchDTO> searchArticle(String keywords) {
//        if (StringUtils.isBlank(keywords)) {
//            return new ArrayList<>();
//        }
//        return search(buildQuery(keywords));
//    }
//
//    private NativeQueryBuilder buildQuery(String keywords) {
//        NativeQueryBuilder nativeSearchQueryBuilder = new NativeQueryBuilder();
//        QueryBuilders.bool().
//                must(QueryBuilders.bool()
//                        .should(QueryBuilders.match()))
//                        .should(QueryBuilders.match("articleContent", keywords)))
//                .must(QueryBuilders.termQuery("isDelete", FALSE))
//                .must(QueryBuilders.termQuery("status", PUBLIC.getStatus()));
//        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
//        return nativeSearchQueryBuilder;
//    }
//
//    private List<ArticleSearchDTO> search(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
//        HighlightBuilder.Field titleField = new HighlightBuilder.Field("articleTitle");
//        titleField.preTags(PRE_TAG);
//        titleField.postTags(POST_TAG);
//        HighlightBuilder.Field contentField = new HighlightBuilder.Field("articleContent");
//        contentField.preTags(PRE_TAG);
//        contentField.postTags(POST_TAG);
//        contentField.fragmentSize(50);
//        nativeSearchQueryBuilder.withHighlightFields(titleField, contentField);
//        try {
//            SearchHits<ArticleSearchDTO> search = elasticsearchTemplate.search(nativeSearchQueryBuilder.build(), ArticleSearchDTO.class);
//            return search.getSearchHits().stream().map(hit -> {
//                ArticleSearchDTO article = hit.getContent();
//                List<String> titleHighLightList = hit.getHighlightFields().get("articleTitle");
//                if (CollectionUtils.isNotEmpty(titleHighLightList)) {
//                    article.setArticleTitle(titleHighLightList.get(0));
//                }
//                List<String> contentHighLightList = hit.getHighlightFields().get("articleContent");
//                if (CollectionUtils.isNotEmpty(contentHighLightList)) {
//                    article.setArticleContent(contentHighLightList.get(contentHighLightList.size() - 1));
//                }
//                return article;
//            }).collect(Collectors.toList());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return new ArrayList<>();
//    }
//
//}
//

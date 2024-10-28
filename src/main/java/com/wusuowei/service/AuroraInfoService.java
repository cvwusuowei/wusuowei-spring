package com.wusuowei.service;

import com.wusuowei.model.dto.AboutDTO;
import com.wusuowei.model.dto.AuroraAdminInfoDTO;
import com.wusuowei.model.dto.AuroraHomeInfoDTO;
import com.wusuowei.model.dto.WebsiteConfigDTO;
import com.wusuowei.model.vo.AboutVO;
import com.wusuowei.model.vo.WebsiteConfigVO;

public interface AuroraInfoService {

    void report();

    AuroraHomeInfoDTO getAuroraHomeInfo();

    AuroraAdminInfoDTO getAuroraAdminInfo();

    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);

    WebsiteConfigDTO getWebsiteConfig();

    void updateAbout(AboutVO aboutVO);

    AboutDTO getAbout();

}

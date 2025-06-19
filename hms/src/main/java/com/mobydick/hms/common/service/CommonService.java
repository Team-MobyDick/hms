package com.mobydick.hms.common.service;

import com.mobydick.hms.common.vo.CommonVO;

public interface CommonService {
    // 대시보드에 필요한 모든 데이터를 한번에 조회하는 메서드
    CommonVO getDashboardData() throws Exception;
}
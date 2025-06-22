package com.mobydick.hms.common.service;

import com.mobydick.hms.common.vo.CommonVO;

public interface CommonService {

    CommonVO getDashboardData(String emplId) throws Exception;

}
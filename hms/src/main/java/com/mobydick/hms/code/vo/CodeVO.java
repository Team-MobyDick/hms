package com.mobydick.hms.code.vo;

import lombok.Data;

import java.sql.Date;

// 코드 테이블 데이터 저장 VO
@Data
public class CodeVO {

    private String codeId;         // 코드 아이디
    private String codeName;       // 코드명
    private int flag;              // 구분값

    private Date createdDate;      // 작성일자
    private String createdId;      // 작성자 ID
    private Date updatedDate;      // 수정일자
    private String updatedId;      // 수정자 ID

}

-- 테스트 테이블 생성
create table test(

    id varchar2(100) primary key not null,
    pwd varchar2(100) not null

);

-- 테스트 테이블 코멘트
-- 테이블 주석
COMMENT ON TABLE test IS '테스트용 사용자 정보 테이블';

-- 컬럼 주석
COMMENT ON COLUMN test.id IS '사용자 아이디';
COMMENT ON COLUMN test.pwd IS '사용자 비밀번호';
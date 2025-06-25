package com.mobydick.hms.common.service;

import com.mobydick.hms.common.dao.CommonDAO;
import com.mobydick.hms.common.vo.CommonVO;
import com.mobydick.hms.notice.vo.NoticeVO;
import com.mobydick.hms.schedule.vo.ScheduleVO;
import com.mobydick.hms.employee.vo.EmployeeVO;
import com.mobydick.hms.work.vo.WorkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonDAO commonDAO;

    private volatile List<EmployeeVO> cachedEmployeesOfTheDay;
    private volatile LocalDate lastEmployeesOfTheDayCachedDate;

    private volatile EmployeeVO cachedCoffeeWinner;
    private volatile LocalDate lastCoffeeWinnerCachedDate;

    @Override
    public CommonVO getDashboardData(String emplId) throws Exception {
        // 대시보드 데이터를 담을 CommonVO 객체 생성
        CommonVO dashboardData = new CommonVO();

        commonDAO.resetCoffeeWinnerStatus();

        // 오늘의 사원 정보 조회 후 CommonVO에 설정
        List<EmployeeVO> employees = getEmployeesOfTheDayDailyFixed();
        dashboardData.setEmployeesOfTheDay(employees);

        LocalDate today = LocalDate.now();

        if (cachedCoffeeWinner == null || !today.equals(lastCoffeeWinnerCachedDate)) {
            synchronized (this) {
                if (cachedCoffeeWinner == null || !today.equals(lastCoffeeWinnerCachedDate)) {
                    System.out.println("====== 오늘의 커피 당첨자 선정 시작 (재시작/새날) ======");

                    try {

                        if (employees != null && !employees.isEmpty()) {

                            Random random = new Random();
                            // '오늘의 사원' 3명 중 1명 선택
                            int winnerIndex = random.nextInt(employees.size());
                            EmployeeVO newlySelectedWinner = employees.get(winnerIndex);

                            commonDAO.updateCoffeeWinner(newlySelectedWinner.getEmplId());

                            cachedCoffeeWinner = newlySelectedWinner;
                            lastCoffeeWinnerCachedDate = today;
                            newlySelectedWinner.setCoffeeWinnerYn("Y");

                            System.out.println("오늘의 커피 당첨자 ('오늘의 사원' 중): " + newlySelectedWinner.getEmplName());
                        } else {
                            System.out.println("'오늘의 사원'이 없어 커피 당첨자를 선정할 수 없습니다.");
                            cachedCoffeeWinner = null;
                        }
                    } catch (Exception e) {
                        System.err.println("오늘의 커피 당첨자 선정 중 오류 발생: " + e.getMessage());
                        e.printStackTrace();
                        cachedCoffeeWinner = null;
                    }
                    System.out.println("====== 오늘의 커피 당첨자 선정 종료 ======");
                }
            }
        }

        // 주간 스케줄 정보 조회 후 CommonVO에 설정
        List<ScheduleVO> schedules = commonDAO.selectWeeklySchedules(emplId);
        dashboardData.setWeeklySchedules(schedules);

        // 최신 공지사항 정보 조회 후 CommonVO에 설정
        List<NoticeVO> notices = commonDAO.selectLatestNotices();
        dashboardData.setLatestNotices(notices);

        // 오늘의 할 일 정보 조회 후 CommonVO에 설정
        List<WorkVO> todayWorks = commonDAO.selectTodayWorks(emplId);
        dashboardData.setTodayWorks(todayWorks);

        if (todayWorks != null && !todayWorks.isEmpty()) {
            System.out.println("WorkDImpo from WorkVO in Service: " + todayWorks.get(0).getWorkDImpo());
        }

        return dashboardData;
    }

    // 오늘의 사원 일별 고정 후 조회(하루 한번 DB에서 조회, 이후에는 캐시된 데이터 반환)
    private List<EmployeeVO> getEmployeesOfTheDayDailyFixed() {
        LocalDate today = LocalDate.now();

        if (cachedEmployeesOfTheDay != null && today.equals(lastEmployeesOfTheDayCachedDate)) {
            return cachedEmployeesOfTheDay;
        }

        synchronized (this) {
            if (cachedEmployeesOfTheDay != null && today.equals(lastEmployeesOfTheDayCachedDate)) {
                return cachedEmployeesOfTheDay;
            }

            List<EmployeeVO> newEmployees = commonDAO.selectRandomEmployees();

            cachedEmployeesOfTheDay = newEmployees;
            lastEmployeesOfTheDayCachedDate = today;

            return newEmployees;
        }
    }
}
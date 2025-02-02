package kr.allcll.seatfinder.subject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Subject {

    private static final String NON_MAJOR_DEPARTMENT_NAME = "대양휴머니티칼리지";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String offeringUniversity;      // 개설대학
    private String offeringDepartment;      // 개설학과전공
    private String subjectCode;             // 학수번호
    private String classCode;               // 분반
    private String subjectName;             // 교과목명
    private String subjectType;             // 이수구분
    private String electiveArea;            // 선택영역
    private String academicYearSemester;    // 학년 (학기)
    private String credit;                  // 학점
    private String theoryHours;             // 이론
    private String practiceHours;           // 실습
    private String classType;               // 수업 유형
    private String exchangeEligible;        // 학점교류 수강가능
    private String schedule;                // 요일 및 강의시간
    private String lectureRoom;             // 강의실
    private String professorName;           // 메인 교수명
    private String supervisingDepartment;   // 주관학과
    private String notesAndRestrictions;    // 수강대상및유의사항
    private String classCategory;           // 강좌유형
    private String onlineLecture;           // 사이버강좌
    private String lectureLanguage;         // 강의언어
    private String foreignerOnly;           // 외국인 전용
    private String domesticOnly;            // 내국인 전용
    private String deptCd; //개설학과코드

    public boolean isNonMajor() {
        return NON_MAJOR_DEPARTMENT_NAME.equals(offeringDepartment);
    }
}

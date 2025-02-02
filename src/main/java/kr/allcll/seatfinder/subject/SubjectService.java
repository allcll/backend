package kr.allcll.seatfinder.subject;

import java.util.List;
import kr.allcll.seatfinder.subject.dto.SubjectsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
//
//    @Transactional
//    public void save(SubjectsRequest subjectRequests) {
//        List<Subject> subjects = subjectRequests.toEntity();
//        subjectRepository.saveAll(subjects);
//    }

    public SubjectsResponse findSubjectsByCondition(
        Long subjectId,
        String subjectName,
        String subjectCode,
        String classCode,
        String professorName
    ) {
        Specification<Subject> condition = getCondition(subjectId, subjectName, subjectCode, classCode, professorName);
        List<Subject> subjects = subjectRepository.findAll(condition);
        return SubjectsResponse.from(subjects);
    }

    private Specification<Subject> getCondition(
        Long subjectId,
        String subjectName,
        String subjectCode,
        String classCode,
        String professorName
    ) {
        return Specification.where(
                SubjectSpecifications.hasSubjectId(subjectId))
            .and(SubjectSpecifications.hasSubjectName(subjectName))
            .and(SubjectSpecifications.hasSubjectCode(subjectCode))
            .and(SubjectSpecifications.hasClassCode(classCode))
            .and(SubjectSpecifications.hasProfessorName(professorName)
            );
    }
}

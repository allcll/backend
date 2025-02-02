package kr.allcll.seatfinder.subject;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.allcll.seatfinder.excel.SubjectSheetParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SubjectApi.class)
class SubjectApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubjectSheetParser subjectSheetParser;

    @MockitoBean
    private SubjectService subjectService;

    @DisplayName("과목을 조회한다.")
    @Test
    void getSubjectsApiTest() throws Exception {
        mockMvc.perform(get("/api/subjects")
                .param("subjectId", "1")
                .param("subjectName", "컴퓨터구조"))
            .andExpect(status().isOk());

        then(subjectService).should().findSubjectsByCondition(1L, "컴퓨터구조", null, null, null);
    }
//
//    @DisplayName("과목 엑셀 파일을 업로드한다.")
//    @Test
//    void uploadSubjectsApiTest() throws Exception {
//        MockMultipartFile mockFile = new MockMultipartFile(
//            "file",
//            "subjects.xlsx",
//            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
//            "dummy-content".getBytes()
//        );
//        SubjectsParsingResponse mockResponse = new SubjectsParsingResponse(List.of());
//        given(subjectSheetParser.parse(any(MultipartFile.class))).willReturn(mockResponse);
//
//        mockMvc.perform(multipart("/api/subject/upload").file(mockFile))
//            .andExpect(status().isOk())
//            .andExpect(content().string("업로드에 성공했습니다."));
//
//        then(subjectSheetParser).should().parse(any(MultipartFile.class));
//        then(subjectService).should().save(any(SubjectsRequest.class));
//    }
}

package kr.allcll.seatfinder.basket;

import kr.allcll.seatfinder.basket.dto.BasketsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasketApi {

    private final BasketService basketService;

    @GetMapping("/api/baskets")
    public ResponseEntity<BasketsResponse> getBasketsBySearch(
        @RequestParam(required = false) String departmentCode,
        @RequestParam(required = false) String professorName,
        @RequestParam(required = false) String subjectName
    ) {
        BasketsResponse response = basketService.findBasketsByCondition(
            departmentCode,
            professorName,
            subjectName
        );
        return ResponseEntity.ok(response);
    }
}

package kr.allcll.seatfinder.pin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.allcll.seatfinder.TimeEntity;
import kr.allcll.seatfinder.subject.Subject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PIN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pin extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    public Pin(String token, Subject subject) {
        this.token = token;
        this.subject = subject;
    }
}

package simlabapp.entity;

import jakarta.persistence.*;
import lombok.*;
import simlabapp.entity.enums.Gender;
import simlabapp.entity.enums.Major;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "assistants")
public class Assistant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, length = 15)
    private String phone;

    private String address;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 150)
    private String placeOfBirth;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Major major;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Gender gender;

    private String imageProfile;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

}

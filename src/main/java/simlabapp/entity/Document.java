package simlabapp.entity;

import jakarta.persistence.*;
import lombok.*;
import simlabapp.entity.enums.DocumentType;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String source;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private DocumentType documentType;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

}

package com.Website.Step2.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Setter
public class Sub {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	@NotBlank(message = "Community name is required")
	private String name;
	@NotBlank(message = "Description is required")
	private String description;
	@OneToMany(fetch = LAZY)
	private List<Post> posts;
	private Instant createdDate;
	@ManyToOne(fetch = LAZY)
	private User user;

}

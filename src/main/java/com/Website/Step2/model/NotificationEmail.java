package com.Website.Step2.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationEmail {
    private String subject;
    private String recipient;
    private String body;

}

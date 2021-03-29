package com.playmuscial.api.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private String id;

    private String password;

    private String url;

    private String name;

    private Long userNo;

    private String token;

}
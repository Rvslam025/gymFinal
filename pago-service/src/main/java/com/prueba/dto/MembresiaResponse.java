package com.prueba.dto;

import lombok.Data;

@Data
public class MembresiaResponse {

    private Long id;
    private Long userId;
    private String tipoPlan;
    private Boolean activa;
}

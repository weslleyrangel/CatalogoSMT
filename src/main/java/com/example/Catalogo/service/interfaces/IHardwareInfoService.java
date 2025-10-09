package com.example.Catalogo.service.interfaces;

import com.example.Catalogo.dto.HardwareInfoDTO;

/**
 * Interface para o serviço que coleta informações de hardware local.
 */
public interface IHardwareInfoService {
    HardwareInfoDTO getLocalHardwareInfo();
}


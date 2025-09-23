package com.example.Catalogo.service;

import com.example.Catalogo.dto.HardwareInfoDTO;
import com.example.Catalogo.service.interfaces.IHardwareInfoService;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

@Service
public class HardwareInfoService implements IHardwareInfoService {

    @Override
    public HardwareInfoDTO getLocalHardwareInfo() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

        HardwareInfoDTO dto = new HardwareInfoDTO();

        // Coleta as informações usando OSHI
        dto.setOs(os.toString());
        dto.setFabricante(getFabricante(hal));
        dto.setModelo(getModelo(hal));
        dto.setProcessador(getProcessador(hal));
        dto.setRam(getMemoriaRAM(hal));

        return dto;
    }

    private String getFabricante(HardwareAbstractionLayer hal) {
        ComputerSystem system = hal.getComputerSystem();
        return system.getManufacturer();
    }

    private String getModelo(HardwareAbstractionLayer hal) {
        ComputerSystem system = hal.getComputerSystem();
        return system.getModel();
    }

    private String getProcessador(HardwareAbstractionLayer hal) {
        CentralProcessor processor = hal.getProcessor();
        return processor.getProcessorIdentifier().getName();
    }

    private String getMemoriaRAM(HardwareAbstractionLayer hal) {
        GlobalMemory memory = hal.getMemory();
        // Converte bytes para Gigabytes e formata
        double gbTotal = (double) memory.getTotal() / (1024 * 1024 * 1024);
        return String.format("%.0f GB", Math.ceil(gbTotal));
    }
}

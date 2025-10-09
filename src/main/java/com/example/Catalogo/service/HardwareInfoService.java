package com.example.Catalogo.service;

import com.example.Catalogo.dto.HardwareInfoDTO;
import com.example.Catalogo.service.interfaces.IHardwareInfoService;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class HardwareInfoService implements IHardwareInfoService {

    private final SystemInfo systemInfo;
    private final HardwareAbstractionLayer hardware;
    private final OperatingSystem os;

    public HardwareInfoService() {
        this.systemInfo = new SystemInfo();
        this.hardware = systemInfo.getHardware();
        this.os = systemInfo.getOperatingSystem();
    }

    @Override
    public HardwareInfoDTO getLocalHardwareInfo() {
        HardwareInfoDTO dto = new HardwareInfoDTO();

        dto.setOs(os.toString());
        dto.setFabricante(hardware.getComputerSystem().getManufacturer());
        dto.setModelo(hardware.getComputerSystem().getModel());
        dto.setProcessador(hardware.getProcessor().getProcessorIdentifier().getName());
        dto.setRam(formatarMemoriaRAM(hardware.getMemory()));

        dto.setTipoDispositivo(getTipoDispositivo(hardware));
        dto.setPlacaMae(getPlacaMaeInfo(hardware.getComputerSystem().getBaseboard()));
        dto.setBios(getBiosInfo(hardware.getComputerSystem().getFirmware()));
        dto.setMemoriaDetalhes(getDetalhesMemoria(hardware.getMemory()));
        dto.setArmazenamentoDetalhes(getDetalhesArmazenamento(hardware));

        return dto;
    }

    private String getTipoDispositivo(HardwareAbstractionLayer hal) {
        for (PowerSource ps : hal.getPowerSources()) {
            if (ps.getName().toLowerCase().contains("battery")) {
                return "Laptop";
            }
        }
        return "Desktop";
    }

    private String getPlacaMaeInfo(Baseboard board) {
        return String.format("%s - %s (Chipset: %s)",
                board.getManufacturer(),
                board.getModel(),
                board.getVersion());
    }
    
    private String getBiosInfo(Firmware firmware) {
        String releaseDate = "N/A";
        if (firmware.getReleaseDate() != null) {
            releaseDate = firmware.getReleaseDate().formatted(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return String.format("%s (Versão: %s, Data: %s)",
                firmware.getManufacturer(),
                firmware.getVersion(),
                releaseDate);
    }

    private String getDetalhesMemoria(GlobalMemory memory) {
        return memory.getPhysicalMemory().stream()
                .map(pm -> String.format("%s %s @ %d MHz",
                        formatarBytes(pm.getCapacity()),
                        pm.getManufacturer(),
                        pm.getClockSpeed() / 1000000))
                .collect(Collectors.joining(" | "));
    }
    
    private String getDetalhesArmazenamento(HardwareAbstractionLayer hal) {
        return hal.getDiskStores().stream()
                .map(disk -> String.format("%s (%s)",
                        disk.getModel(),
                        formatarBytes(disk.getSize())))
                .collect(Collectors.joining(" | "));
    }

    private String formatarMemoriaRAM(GlobalMemory memory) {
        double gbTotal = (double) memory.getTotal() / (1024 * 1024 * 1024);
        return String.format("%.0f GB", Math.ceil(gbTotal));
    }

    private String formatarBytes(long bytes) {
        if (bytes <= 0) return "0 B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        return String.format("%.1f %s", bytes / Math.pow(1024, digitGroups), units[digitGroups]);
    }
}
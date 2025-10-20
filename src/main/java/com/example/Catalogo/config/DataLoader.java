package com.example.Catalogo.config;

import com.example.Catalogo.service.interfaces.IDataInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Responsável apenas por executar a inicialização durante o boot da aplicação.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final IDataInitializationService dataInitializationService;

    @Autowired
    public DataLoader(IDataInitializationService dataInitializationService) {
        this.dataInitializationService = dataInitializationService;
    }

    @Override
    public void run(String... args) throws Exception {
        dataInitializationService.initializeData();
    }
}
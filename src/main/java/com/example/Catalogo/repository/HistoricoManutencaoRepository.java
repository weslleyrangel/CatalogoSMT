package com.example.Catalogo.repository;

import com.example.Catalogo.model.Computador;
import com.example.Catalogo.model.HistoricoManutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface HistoricoManutencaoRepository extends JpaRepository<HistoricoManutencao, Long> {

    /**
     * Encontra todos os registros de histórico para um computador, ordenados pela data de entrada mais recente.
     */
    List<HistoricoManutencao> findByComputadorOrderByDataEntradaDesc(Computador computador);


    List<HistoricoManutencao> findTop5ByOrderByDataEntradaDesc();

    /**
     * Encontra o registro de manutenção mais recente de um computador que ainda está "aberto" (sem data de saída).
     */
    Optional<HistoricoManutencao> findTopByComputadorAndDataSaidaIsNullOrderByDataEntradaDesc(Computador computador);
}
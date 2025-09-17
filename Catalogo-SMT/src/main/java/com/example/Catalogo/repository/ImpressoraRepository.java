/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Catalogo.repository;

import com.example.Catalogo.model.Impressora;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wesll
 *
 * Repository interface para operações CRUD da entidade Impressora. Extende
 * JpaRepository para operações básicas e define consultas customizadas.
 */
@Repository
public interface ImpressoraRepository extends JpaRepository<Impressora, String> {

    Optional<Impressora> findByPatrimonio(String patrimonio);

    boolean existsByPatrimonio(String patrimonio);

    List<Impressora> findByStatus(String status);

    List<Impressora> findByTipo(String tipo);

    List<Impressora> findByLocalizacao(String localizacao);

    /**
     * Busca impressoras com filtros múltiplos usando JPQL.
     *
     * @param patrimonio Patrimônio (pode ser nulo)
     * @param tipo Tipo (pode ser nulo)
     * @param modelo Modelo (pode ser nulo)
     * @param localizacao Localização (pode ser nulo)
     * @param status Status (pode ser nulo)
     * @return Lista de impressoras que atendem aos critérios
     */
    @Query("SELECT i FROM Impressora i WHERE "
            + "(:patrimonio IS NULL OR LOWER(i.patrimonio) LIKE LOWER(CONCAT('%', :patrimonio, '%'))) AND "
            + "(:tipo IS NULL OR i.tipo = :tipo) AND "
            + "(:modelo IS NULL OR LOWER(i.modelo) LIKE LOWER(CONCAT('%', :modelo, '%'))) AND "
            + "(:localizacao IS NULL OR LOWER(i.localizacao) LIKE LOWER(CONCAT('%', :localizacao, '%'))) AND "
            + "(:status IS NULL OR i.status = :status)")
    List<Impressora> findWithFilters(@Param("patrimonio") String patrimonio,
            @Param("tipo") String tipo,
            @Param("modelo") String modelo,
            @Param("localizacao") String localizacao,
            @Param("status") String status);

    long countByStatus(String status);

    long countByTipo(String tipo);
}

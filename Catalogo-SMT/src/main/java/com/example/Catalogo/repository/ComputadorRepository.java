/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Catalogo.repository;

import com.example.Catalogo.model.Computador;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wesll 
 * Repository interface para operações CRUD da entidade
 * Computador. Extende JpaRepository para operações básicas e define consultas
 * customizadas.
 *
 */
@Repository
public interface ComputadorRepository extends JpaRepository<Computador, String> {

    /**
     * Busca um computador por patrimônio.
     *
     * @param patrimonio Número do patrimônio
     * @return Optional contendo o computador se encontrado
     */
    Optional<Computador> findByPatrimonio(String patrimonio);

    /**
     * Verifica se existe um computador com o patrimônio informado.
     *
     * @param patrimonio Número do patrimônio
     * @return true se existe, false caso contrário
     */
    boolean existsByPatrimonio(String patrimonio);

    /**
     * Busca computadores por status.
     *
     * @param status Status do computador (ex: "Ativo", "Inativo", "Manutenção")
     * @return Lista de computadores com o status informado
     */
    List<Computador> findByStatus(String status);

    /**
     * Busca computadores por setor.
     *
     * @param setor Setor do computador
     * @return Lista de computadores do setor informado
     */
    List<Computador> findBySetor(String setor);

    /**
     * Busca computadores por usuário.
     *
     * @param usuario Nome do usuário
     * @return Lista de computadores do usuário informado
     */
    List<Computador> findByUsuario(String usuario);

    /**
     * Busca computadores que estão em manutenção.
     *
     * @return Lista de computadores em manutenção
     */
    @Query("SELECT c FROM Computador c WHERE c.status = 'Manutenção'")
    List<Computador> findEmManutencao();

    /**
     * Busca computadores com filtros múltiplos usando JPQL.
     *
     * @param patrimonio Patrimônio (pode ser nulo)
     * @param usuario Usuário (pode ser nulo)
     * @param setor Setor (pode ser nulo)
     * @param status Status (pode ser nulo)
     * @return Lista de computadores que atendem aos critérios
     */
    @Query("SELECT c FROM Computador c WHERE "
            + "(:patrimonio IS NULL OR LOWER(c.patrimonio) LIKE LOWER(CONCAT('%', :patrimonio, '%'))) AND "
            + "(:usuario IS NULL OR LOWER(c.usuario) LIKE LOWER(CONCAT('%', :usuario, '%'))) AND "
            + "(:setor IS NULL OR c.setor = :setor) AND "
            + "(:status IS NULL OR c.status = :status)")
    List<Computador> findWithFilters(@Param("patrimonio") String patrimonio,
            @Param("usuario") String usuario,
            @Param("setor") String setor,
            @Param("status") String status);

    /**
     * Conta o número total de computadores por status.
     *
     * @param status Status para contar
     * @return Quantidade de computadores com o status
     */
    long countByStatus(String status);
}

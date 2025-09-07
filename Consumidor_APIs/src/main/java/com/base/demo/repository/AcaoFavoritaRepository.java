package com.base.demo.repository;

import com.base.demo.model.AcaoFavorita;
import org.springframework.data.jpa.repository.JpaRepository;

// ...
import java.util.List;

public interface AcaoFavoritaRepository extends JpaRepository<AcaoFavorita, Long> {
    
    List<AcaoFavorita> findByPrecoAlvoCompraIsNotNullOrPrecoAlvoVendaIsNotNull();
}
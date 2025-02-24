package com.example.marketplacemain.marketplacemain.products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.products.entitites.Descuento;

public interface DescuentoRepository extends CrudRepository<Descuento, Long> {

    @Query("select d from Descuento d left join fetch d.vendedor where d.vendedor.id=?1 and d.fechaFinalDescuento  >= CURDATE()")
    List<Descuento> findByIdVendedor(Long id);


    @Query("select d from Descuento d left join fetch d.vendedor where d.vendedor.id=?1 ")
    List<Descuento> findByIdVendedorAll(Long id);

    // @Query("select c from Client c left join fetch c.invoices where c.id=?1")
    // Optional<Client> findOneWithInvoices(Long id);

    // @Query("select c from Client c left join fetch c.invoices left join fetch c.addresses left join fetch c.clientDetails where c.id=?1")
    // Optional<Client> findOne(Long id);
}

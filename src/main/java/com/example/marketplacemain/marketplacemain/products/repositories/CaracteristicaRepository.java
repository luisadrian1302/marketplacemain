package com.example.marketplacemain.marketplacemain.products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.products.DTO.ValorPropiedadDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Caracteristicas;



public interface CaracteristicaRepository extends CrudRepository<Caracteristicas, Long> {

    // ValorPropiedadDTO

    @Query("SELECT new com.example.marketplacemain.marketplacemain.products.DTO.ValorPropiedadDTO(a.tipoPropiedad, v.valor, a.nombre) " +
       "FROM Caracteristicas c " + 
       "INNER JOIN c.atributo a " + 
       "INNER JOIN c.valor v " + 
       "INNER JOIN c.subProducto s " + 
       "WHERE s.producto.id = ?1 "+ 
       "group by a.tipoPropiedad, a.nombre, v.valor ")
    List<ValorPropiedadDTO> findAtributoValor(Long id);

    // @Query(value = "SELECT * FROM empleado", nativeQuery = true)
    @Query("SELECT new com.example.marketplacemain.marketplacemain.products.DTO.ValorPropiedadDTO(a.tipoPropiedad, v.valor, a.nombre) " +
    "FROM Caracteristicas c " + 
    "INNER JOIN c.atributo a " + 
    "INNER JOIN c.valor v " + 
    "INNER JOIN c.subProducto s " + 
    "WHERE s.producto.id = ?1 and s.id <> ?2  "+ 
    "group by a.tipoPropiedad, a.nombre, v.valor ")
    List<ValorPropiedadDTO> findAtributoValorByIDSubproducto(Long id, Long idsubcategoria);


    @Query("SELECT new com.example.marketplacemain.marketplacemain.products.DTO.ValorPropiedadDTO(a.tipoPropiedad, v.valor, a.nombre) " +
    "FROM Caracteristicas c " + 
    "INNER JOIN c.atributo a " + 
    "INNER JOIN c.valor v " + 
    "INNER JOIN c.subProducto s " + 
    "WHERE s.producto.id = ?1 and s.id = ?2  "+ 
    "group by a.tipoPropiedad, a.nombre, v.valor ")
    List<ValorPropiedadDTO> findValuesBySubproduct(Long id, Long idsubcategoria);

   

 
//     select a.tipo_propiedad, v.valor from atributo a 
// inner join caracteristicas c on c.id_atributo = a.id 
// inner join valor v on v.id = c.id_valor 
// inner join subproducto s on s.id = c.id_subproducto
// inner join producto p on p.id = s.id_producto where p.id =10 ;
    
}

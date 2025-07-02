package com.example.marketplacemain.marketplacemain.products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.products.DTO.ValorPropiedadDTO;
import com.example.marketplacemain.marketplacemain.products.DTO.ValorPropiedadPublicDTO;
import com.example.marketplacemain.marketplacemain.products.entitites.Atributo;
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

    @Query("SELECT new com.example.marketplacemain.marketplacemain.products.DTO.ValorPropiedadPublicDTO(a.tipoPropiedad, v.valor, a.nombre, a.id) " +
       "FROM Caracteristicas c " + 
       "INNER JOIN c.atributo a " + 
       "INNER JOIN c.valor v " + 
       "INNER JOIN c.subProducto s " + 
       "WHERE s.producto.id = ?1 "+ 
       "group by a.tipoPropiedad, a.nombre, v.valor, a.id")
    List<ValorPropiedadPublicDTO> findAtributoValorPublic(Long id);

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

   @Query("SELECT COUNT(c.id) FROM Caracteristicas c WHERE c.atributo.id = ?1" )
   long countByAtributo_Id(Long id);


   @Query( value = "SELECT s.id as id\r\n" + //
            "FROM subproducto s\r\n" + //
            "INNER JOIN caracteristicas c1 ON c1.id_subproducto = s.id\r\n" + //
            "INNER JOIN atributo a1 ON c1.id_atributo = a1.id\r\n" + //
            "INNER JOIN valor v1 ON c1.id_valor = v1.id\r\n" + //
        
            "INNER JOIN caracteristicas c2 ON c2.id_subproducto = s.id\r\n" + //
            "INNER JOIN atributo a2 ON c2.id_atributo = a2.id\r\n" + //
            "INNER JOIN valor v2 ON c2.id_valor = v2.id\r\n" + //
     
            "WHERE s.id_producto = ?1 and (a1.nombre = ?2 AND v1.valor = ?3 and a1.tipo_propiedad =  ?4 ) \r\n" + //
            "AND (a2.nombre = ?5 AND v2.valor = ?6 and a2.tipo_propiedad = ?7 );", nativeQuery = true)
   List<Object[]> getIDSubproductTwoChanel(Long idProducto, String nombre1, String valor1, String tipo1, String nombre2, String valor2,  String tipo2);



   @Query( value = "select s.id from subproducto s\r\n" + //
            "INNER JOIN Caracteristicas c on c.id_subproducto = s.id\r\n" + //
            "INNER JOIN atributo a ON c.id_atributo = a.id\r\n" + //
            "INNER JOIN valor v ON c.id_valor = v.id\r\n" + //
            "where (a.nombre = ?2 and v.valor = ?3 AND a.tipo_propiedad = ?4) and s.id_producto = ?1 ;", nativeQuery = true)
   List<Object[]> getIDSubproductoneChanel(Long idProducto, String nombre1, String valor1, String tipo1);


   @Query( value = "SELECT s.id \r\n" + //
            "FROM subproducto s\r\n" + //

            "INNER JOIN caracteristicas c1 ON c1.id_subproducto = s.id\r\n" + //
            "INNER JOIN atributo a1 ON c1.id_atributo = a1.id\r\n" + //
            "INNER JOIN valor v1 ON c1.id_valor = v1.id\r\n" + //
            "INNER JOIN caracteristicas c2 ON c2.id_subproducto = s.id\r\n" + //
            "INNER JOIN atributo a2 ON c2.id_atributo = a2.id\r\n" + //
            "INNER JOIN valor v2 ON c2.id_valor = v2.id\r\n" + //
         
            "INNER JOIN caracteristicas c3 on c3.id_subproducto = s.id\r\n" + //
            "INNER JOIN atributo a3 on a3.id = c3.id_atributo\r\n" + //
            "INNER JOIN valor v3 ON c3.id_valor = v3.id\r\n" + //
           
            "WHERE s.id_producto = ?1 and (a1.nombre = ?2 AND v1.valor = ?3 and a1.tipo_propiedad = ?4 ) \r\n" + //
            "AND (a2.nombre = ?5 AND v2.valor = ?6 and a2.tipo_propiedad = ?7)\r\n" + //
            "and (a3.nombre = ?8 AND v3.valor = ?9 and a3.tipo_propiedad = ?10 ) ;\r\n"
            , nativeQuery = true)
   List<Object[]> getIDSubproductThreeChanel(Long idProducto, String nombre1, String valor1, String tipo1, String nombre2, String valor2,  String tipo2,
   String nombre3, String valor3,  String tipo3);

   



    
}

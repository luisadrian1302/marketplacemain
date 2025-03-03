package com.example.marketplacemain.marketplacemain.products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.products.entitites.SubProducto;

import jakarta.transaction.Transactional;

public interface SubProductoRepository extends CrudRepository<SubProducto, Long>{

    // funcones de empleados

    @Query("select s from SubProducto s left join fetch s.producto where s.producto.id=?1")
    List<SubProducto> findByIdProducto(Long id);

    @Query("select s from SubProducto s left join fetch s.producto  where s.producto.vendedor.id =?1 and s.statusValidacion = 0")
    List<SubProducto> findByIdUserActive(Long id);

    @Query("select s from SubProducto s left join fetch s.producto  where s.producto.vendedor.id =?1 and s.descuento is null")
    List<SubProducto> findByUserSinDescuento(Long id);

    @Query("select s from SubProducto s left join fetch s.producto  where s.producto.vendedor.id =?1 and s.descuento.id = ?2")
    List<SubProducto> findByUserCOnDescuentoEspecifico(Long id, Long iddescuento);


    @Query(value = "select s.id, p.titular, p.descripcion_general, s.precio, s.stock, s.status, s.tamaño_alto, s.tamaño_ancho, s.status_validacion \n" + //
                "from subproducto s\n" + //
                "inner join  producto p on p.id = s.id_producto\n" + //
                "where s.status IN (1, 2, 3, 4) and p.id_vendedor = ?1;", nativeQuery = true)

    List<Object[]>  findByIdUser(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE subproducto SET status = ?2 WHERE id = ?1", nativeQuery = true)
    void  updateStatusSubproducto(Long id, Integer status);

    @Transactional
    @Modifying
    @Query(value = "UPDATE subproducto\n" + //
                "SET id_descuento = NULL\n" + //
                "WHERE id_descuento IS NOT NULL AND NOT EXISTS (\n" + //
                "  SELECT 1 FROM descuento WHERE descuento.id = subproducto.id_descuento AND descuento.fecha_final_descuento >=  GETDATE()\n" + //
                ") and status <> 0;", nativeQuery = true)
    void  verificarEstadoDescuento();


    @Transactional
    @Modifying
    @Query(value = "UPDATE subproducto\n" + //
                "SET id_descuento = NULL\n" + //
                "WHERE id_descuento IS NOT NULL AND NOT EXISTS (\n" + //
                "  SELECT 1 FROM descuento WHERE descuento.id = subproducto.id_descuento AND descuento.fecha_final_descuento >= GETDATE()\n" + //
                ") and status <> 0 and id=?1;", nativeQuery = true) 
    void  verificarEstadoDescuento(Long id);
 
    

    // funciones de vendedores
    
    // @Query("select s from SubProducto s left join fetch s.producto  where s.producto.estadoAprobacion =?1 and s.statusValidacion = 0")
    // List<SubProducto> findByproductsDisApprovated(Long status);
   
}

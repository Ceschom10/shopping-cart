package backend.shoppingcart.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.shoppingcart.model.Order;

@Repository
public interface IOrderDao extends JpaRepository<Order, Integer>{
	@Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.orderDetails")
    List<Order> findAllWithOrderDetails();
	
	@Query("SELECT o FROM Order o JOIN FETCH o.orderDetails WHERE o.id = :orderId")
    Optional<Order> findByIdWithOrderDetails(@Param("orderId") int orderId);
}

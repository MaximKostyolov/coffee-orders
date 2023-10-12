package eventsStore.repository;

import eventsStore.model.Event;
import eventsStore.model.OrderEventView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query(value = "select * from events e " +
            "where (e.order_id = ?1) " +
            "and (e.status = 'REGISTERED')", nativeQuery = true)
    Event findRegisteredEvent(int id);

    @Query(value = "select e.order_id, e.employee_id, e.status, e.timestamp " +
            "from events e " +
            "where (e.order_id = ?1) " +
            "order by e.timestamp DESC", nativeQuery = true)
    List<OrderEventView> findEventList(int id);

    @Query(value = "select * from events e " +
            "where (e.order_id = ?1) " +
            "order by e.timestamp DESC " +
            "limit 1", nativeQuery = true)
    Event findLastEvent(int id);

}
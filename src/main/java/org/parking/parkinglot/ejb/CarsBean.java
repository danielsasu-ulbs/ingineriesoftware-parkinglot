package org.parking.parkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.parking.parkinglot.common.CarDto;
import org.parking.parkinglot.entities.Car;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    public List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> dtos = new ArrayList<CarDto>();
        for (Car car : cars) {
            CarDto carDto = new CarDto(car.getId(), car.getLicensePlate(), car.getParkingSpot(), car.getOwner().getUsername());
            dtos.add(carDto);
        }

        return dtos;
    }
}

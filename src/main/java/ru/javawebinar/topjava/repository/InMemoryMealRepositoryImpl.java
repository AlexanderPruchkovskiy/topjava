package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.MealServlet;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log=LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository=new ConcurrentHashMap<>();
    AtomicInteger counter=new AtomicInteger();
    public InMemoryMealRepositoryImpl(){
    for (Meal elem:MealsUtil.MEALS) {
       this.save(elem);
    }
}
    @Override
    public Meal save(Meal meal) {
        if (meal.isNew())
            meal.setId(counter.incrementAndGet());
        repository.put(meal.getId(),meal);
        return meal;
    }

    @Override
    public void delete(int id) {


        repository.remove(id);
        log.debug("delete meal in MealRepositoryImpl");
    }

    @Override
    public Meal get(Integer id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {

        return repository.values();
    }
}

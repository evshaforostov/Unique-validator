package ru.zcts.travel.directory.location.validator;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import ru.zcts.travel.directory.location.entity.QLocation;
import ru.zcts.travel.directory.location.entity.model.LocationModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.*;

/**
 * Created by evshaforostov on 16.11.2015.
 */
@Stateless
public class UniqueValidator implements ConstraintValidator<Unique, LocationModel>  {

    @Inject
    EntityManager entityManager;


    @Override
    public void initialize(Unique constraintAnnotation) {}



    @Override
    public boolean isValid(LocationModel value, ConstraintValidatorContext context) {
        Long id = value.getId();
        QLocation location = QLocation.location;
        JPAQuery qquery = new JPAQuery(entityManager);
        qquery.from(location);
        if (id != null) {
            qquery.where((location.id.ne(value.getId())));
        }
        String param = null;
        BooleanExpression exp = null;
        if (value.getIata() != null) {
            exp = location.iata.eq(value.getIata());
            param = "iata";
        }
        if (value.getIcao() != null) {
            if (exp == null)
                exp = location.icao.eq(value.getIcao());
            else
                exp = exp.or(location.icao.eq(value.getIcao()));
            param = "icao";
        }
        if (value.getIso() != null) {
            if (exp == null)
                exp = location.iso.eq(value.getIso());
            else
                exp = exp.or(location.iso.eq(value.getIso()));
            param = "iso";
        }
        if (value.getITransfer() != null) {
            if (exp == null)
                exp = location.iTransfer.eq(value.getITransfer());
            else
                exp = exp.or(location.iTransfer.eq(value.getITransfer()));
            param = "itransfer";
        }
        if (value.getUfs() != null) {
            if (exp == null)
                exp = location.ufs.eq(value.getUfs());
            else
                exp = exp.or(location.ufs.eq(value.getUfs()));
            param = "ufs";
        }
        qquery.where(exp);
        List results = qquery.createQuery().getResultList();

        if (results.size() > 0) {
            context.buildConstraintViolationWithTemplate("Unique constraint violation")
                    .addPropertyNode(param)
                    .addConstraintViolation();
            context.disableDefaultConstraintViolation();

            return false;
        }
        return true;
    }
}

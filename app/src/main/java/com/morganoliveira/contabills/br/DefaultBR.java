package com.morganoliveira.contabills.br;

import com.activeandroid.Model;

import java.util.List;

import com.morganoliveira.contabills.dao.DefaultDAO;
import com.morganoliveira.contabills.exceptions.DeleteException;
import com.morganoliveira.contabills.exceptions.SaveException;
import com.morganoliveira.contabills.exceptions.SelectException;
import com.morganoliveira.contabills.model.Conta;

/**
 * Created by Diogo on 01/03/2018.
 */

public abstract class DefaultBR <T extends Model, D extends DefaultDAO<T>> {

    private D dao;

    public DefaultBR(D dao) {
        this.dao = dao;
    }



    public void save(T t) throws SaveException {
        try {
            validateSave(t);
            dao.save(t);
        } catch (Exception e){
            throw new SaveException(e.getMessage(), e);
        }
    }

    public void delete(T t) throws DeleteException {
        try{
            validateDelete(t);
            dao.delete(t);
        } catch (Exception e){
            throw new DeleteException(e.getMessage(), e);
        }
    }

    public void deleteById(Long id) throws DeleteException {
        try{
            dao.deleteById(id);
        } catch (Exception e){
            throw new DeleteException(e.getMessage(), e);
        }
    }

    public void deleteAll() throws DeleteException {
        try{
            dao.deleteAll();
        } catch (Exception e){
            throw new DeleteException(e.getMessage(), e);
        }
    }

    public T selectById(Long id) throws SelectException {
        try {
            return dao.selectById(id);
        } catch (Exception e){
            throw new SelectException(e.getMessage(), e);
        }
    }

    public T selectLast() throws SelectException {
        try{
            return dao.selectLast();
        } catch (Exception e){
            throw new SelectException(e.getMessage(), e);
        }
    }

    public List<T> selectAll() throws SelectException {
        try{
            return dao.selectAll();
        } catch (Exception e){
            throw new SelectException(e.getMessage(), e);
        }
    }



    public boolean isNegativeValue(int value){
        return (value < 0);
    }

    public boolean isEmptyString(String value){
        return (value == null || value.isEmpty());
    }

    protected D getDAO(){
        return dao;
    }

    protected abstract void validateSave(T t) throws SaveException;

    protected abstract void validateDelete(T t) throws DeleteException;


}

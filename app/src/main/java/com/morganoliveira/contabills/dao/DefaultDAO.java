package com.morganoliveira.contabills.dao;

import android.database.Cursor;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.morganoliveira.contabills.model.Conta;
import com.morganoliveira.contabills.model.Usuario;

import java.util.List;

/**
 * Created by Diogo on 06/01/2016.
 */
public abstract class DefaultDAO<T extends Model> {

    private Class<T> tClass;

    public DefaultDAO(Class<T> tClass){

        this.tClass = tClass;
    }

    /**
     * Método para salvar um objeto T no banco de dados local.
     * @param t
     */
    public void save(T t){
        t.save();
    }

    /**
     * Método para deletar um objeto T no banco de dados local.
     * @param t
     */
    public void delete(T t){
        t.delete();
    }

    /**
     * Método para deletar um objeto T no banco de dados local por meio do Id.
     */
    public void deleteById(Long id){
        new Delete().from(tClass).where("id = "+id).execute();
    }

    /**
     * Deleta uma lista de objetos de uma determinada classe na base local de acordo com uma condiÃ§Ã£o.
     *
     * @param clause
     */
    protected void delete(String clause) {
        new Delete().from(tClass).where(clause).execute();
    }

    /**
     * Deleta uma lista de objetos de uma determinada classe na base local.
     *
     */
    public void deleteAll() {
        new Delete().from(tClass).execute();
    }

    /**
     * Consulta uma um objeto de uma determinada classe na base local de acordo com o Id.
     *
     * @return
     */
    public T selectById(Long id) {
        return new Select().from(tClass).where("mId = "+id).executeSingle();
    }

    /**
     * Consulta uma um objeto de uma determinada classe na base local de acordo com uma condiÃ§Ã£o.
     *
     * @param clause
     * @return
     */
    public T selectSingle(String clause) {
        return new Select().from(tClass).where(clause).executeSingle();
    }

    /**
     * Consulta o último objeto inserido relativo a uma determinada classe na base local.
     *
     * @return
     */
    public  T selectLast() {
        return new Select().from(tClass).orderBy("id desc").limit(1).executeSingle();
    }

    /**
     * Consulta uma lista de objetos de uma determinada classe na base local de acordo com uma condiÃ§Ã£o.
     *
     *
     */
    public List<T> select() {
        return new Select().from(tClass).execute();
    }

    /**
     * Consulta uma lista de objetos de uma determinada classe na base local de acordo com uma condiÃ§Ã£o e uma ordenaÃ§Ã£o.
     *
     *
     */
    public List<T> select2() {
        return new Select().from(tClass).execute();
    }

    /**
     * Consulta uma lista de objetos da mesma classe na base local.
     *
     * @return
     */
    public List<T> selectAll() {
        return new Select().from(tClass).execute();
    }

    /**
     * Método para consultar se um determinado objeto existe.
     *
     * @param clause
     * @return
     */


    /**
     * Método para consultar um valor booleano em uma determinada tabela.
     *
     * @param table
     * @param column
     * @param clause
     * @return
     */
    protected boolean selectBoolean(String table, String column, String clause) {
        Cursor cursor = ActiveAndroid.getDatabase().query(table, new String[]{column}, clause, null, null, null, null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(0) == 1;
        } else {
            return false;
        }
    }
}